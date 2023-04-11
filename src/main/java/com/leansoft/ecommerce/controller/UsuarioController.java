package com.leansoft.ecommerce.controller;

import com.leansoft.ecommerce.model.Usuario;
import com.leansoft.ecommerce.service.IUsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    private final Logger LOG = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private IUsuarioService usuarioService;

    // "/usuario/registro"
    @GetMapping("/registro")
    public String create(){
        return "usuario/registro";
    }

    @PostMapping("/save")
    public String save(Usuario usuario){
        LOG.info("Usuario registro: {}", usuario);
        usuario.setTipo("USER");
        usuarioService.save(usuario);

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(){
        return "/usuario/login";
    }

    @PostMapping("/acceder")
    public String acceder(Usuario  usuario, HttpSession session){
        LOG.info("Accesos: {}",usuario);
        Optional<Usuario> user = usuarioService.findByEmail(usuario.getEmail());


        if (user.isPresent()){
            LOG.info("Usuario obtenido de la BD: {}", user.get());
            session.setAttribute("idusuario", user.get().getId());
            if (user.get().getTipo().equals("ADMIN")){
                return "redirect:/administrador";
            }
        }else {
            LOG.info("El usuario no existe en la base de datos");
        }

        return "redirect:/";
    }
}
