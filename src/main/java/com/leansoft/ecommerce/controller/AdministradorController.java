package com.leansoft.ecommerce.controller;

import com.leansoft.ecommerce.model.Producto;
import com.leansoft.ecommerce.service.IProductoService;
import com.leansoft.ecommerce.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/administrador")
public class AdministradorController {
    @Autowired
    private IProductoService productoService;
    @GetMapping("")
    public String home(Model model){
        List<Producto> productos = productoService.getAll();
        model.addAttribute("ListProductos",productos);

        return "administrador/home";
    }
}
