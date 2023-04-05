package com.leansoft.ecommerce.controller;

import com.leansoft.ecommerce.model.Producto;
import com.leansoft.ecommerce.service.IProductoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private IProductoService productoService;

    private final Logger LOG = LoggerFactory.getLogger(HomeController.class);

    @GetMapping("")
    public String home(Model model){

        model.addAttribute("productos", productoService.getAll());

        return "usuario/home";
    }

    @GetMapping("productohome/{id}")
    public String productohome(@PathVariable Integer id, Model model){
        LOG.info("Id del producto enviado como parametro {}",id);
        Producto producto = new Producto();
        Optional<Producto> optionalProducto = productoService.getById(id);
        producto = optionalProducto.get();
        model.addAttribute("productoDetalle",producto);
        return "usuario/productohome";
    }

}
