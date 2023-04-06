package com.leansoft.ecommerce.controller;

import com.leansoft.ecommerce.model.DetalleOrden;
import com.leansoft.ecommerce.model.Orden;
import com.leansoft.ecommerce.model.Producto;
import com.leansoft.ecommerce.service.IProductoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private IProductoService productoService;

    private final Logger LOG = LoggerFactory.getLogger(HomeController.class);
    //para almacenar los detalles de la orden
    List<DetalleOrden> detalleOrdenList = new ArrayList<>();
    Orden orden = new Orden();//datos de la orden
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

    @PostMapping("/cart")
    public String addCart(@RequestParam Integer id, @RequestParam Integer cantidad){
        DetalleOrden detalleOrden = new DetalleOrden();
        Producto producto = new Producto();
        double sumaTotal = 0;

        Optional<Producto> optionalProducto = productoService.getById(id);
        LOG.info("Producto ingresado: {}",optionalProducto.get());
        LOG.info("Cantidad: {}", cantidad);

        return "usuario/carrito";
    }

}
