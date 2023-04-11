package com.leansoft.ecommerce.controller;

import com.leansoft.ecommerce.model.Producto;
import com.leansoft.ecommerce.model.Usuario;
import com.leansoft.ecommerce.service.IProductoService;
import com.leansoft.ecommerce.service.IUsuarioService;
import com.leansoft.ecommerce.service.UploadFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/productos")
public class ProductoController{

    private final Logger LOGGER =  LoggerFactory.getLogger(ProductoController.class);

    @Autowired
    private IProductoService productoService;
    @Autowired
    private IUsuarioService usuarioService;
    @Autowired
    private UploadFileService fileService;

    @GetMapping("")
    public String show(Model model){
        model.addAttribute("listaProductos",productoService.getAll());
        return "productos/show";
    }

    @GetMapping("/create")
    public String create(){
        return "productos/create";
    }

    @PostMapping("/save")
    public String save(Producto producto, @RequestParam("img")MultipartFile file, HttpSession session) throws IOException {
        LOGGER.info("Este es el objeto producto {}",producto);

        Usuario u = usuarioService.findById(Integer.parseInt(session.getAttribute("idusuario").toString()));
        producto.setUsuario(u);

        //imagen
        if (producto.getId()==null){ // cuando se crea un producto nuevo
            String enlaceProd = fileService.saveImage(file);
            producto.setImagen(enlaceProd);
        }

        productoService.save(producto);
        return "redirect:/productos";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model){
        Producto producto = new Producto();
        Optional<Producto> productoOptional = productoService.getById(id);
        producto = productoOptional.get();
        LOGGER.info("Producto busco: {}", producto);
        model.addAttribute("producto",producto);
        return "productos/edit";
    }

    @PostMapping("/update")
    public String update(Producto producto, @RequestParam("img")MultipartFile file) throws IOException {

        Producto p = new Producto();
        p = productoService.getById(producto.getId()).get();

        if (file.isEmpty()){//editamos el producto pero no subimos imagen
            producto.setImagen(p.getImagen());//Asignamos la imagen que ya tenia el Prod. si modificar
        }else{
            if (!p.getImagen().equals("default.jpg")){ //Si la imagen es diferente de default borramos la interior
                fileService.deleteImage(p.getImagen());
            }
            String enlaceProd = fileService.saveImage(file);
            producto.setImagen(enlaceProd);
        }
        producto.setUsuario(p.getUsuario());
        productoService.update(producto);
        return "redirect:/productos";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id){
        Producto p = new Producto();
        p=productoService.getById(id).get();
        if (!p.getImagen().equals("default.jpg")){
            fileService.deleteImage(p.getImagen());
        }

        productoService.delete(id);
        return "redirect:/productos";
    }


}
