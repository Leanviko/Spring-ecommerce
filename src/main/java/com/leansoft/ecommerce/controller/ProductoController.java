package com.leansoft.ecommerce.controller;

import com.leansoft.ecommerce.model.Producto;
import com.leansoft.ecommerce.model.Usuario;
import com.leansoft.ecommerce.service.IProductoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/productos")
public class ProductoController{

    @Autowired
    private IProductoService productoService;
    private final Logger LOGGER =  LoggerFactory.getLogger(ProductoController.class);
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
    public String save(Producto producto){
        LOGGER.info("Este es el objeto producto {}",producto);
        Usuario u = new Usuario(1,"","","","","","","",null,null);
        producto.setUsuario(u);
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
    public String update(Producto producto){
        productoService.update(producto);
        return "redirect:/productos";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id){
        productoService.delete(id);
        return "redirect:/productos";
    }

}
