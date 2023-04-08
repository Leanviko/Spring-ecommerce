package com.leansoft.ecommerce.controller;

import com.leansoft.ecommerce.model.DetalleOrden;
import com.leansoft.ecommerce.model.Orden;
import com.leansoft.ecommerce.model.Producto;
import com.leansoft.ecommerce.model.Usuario;
import com.leansoft.ecommerce.service.IProductoService;
import com.leansoft.ecommerce.service.IUsuarioService;
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

    @Autowired
    private IUsuarioService usuarioService;

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
    public String addCart(@RequestParam Integer id, @RequestParam Integer cantidad, Model model){
        DetalleOrden detalleOrden = new DetalleOrden();
        Producto producto = new Producto();
        double sumaTotal = 0;

        Optional<Producto> optionalProducto = productoService.getById(id);
        LOG.info("Producto ingresado: {}",optionalProducto.get());
        LOG.info("Cantidad: {}", cantidad);
        producto = optionalProducto.get();

        detalleOrden.setCantidad(cantidad);
        detalleOrden.setPrecio(producto.getPrecio());
        detalleOrden.setNombre(producto.getNombre());
        detalleOrden.setTotal(producto.getPrecio()*cantidad);
        detalleOrden.setProducto(producto);

        //validar que el producto no se añada 2 veces
        Integer idProducto = producto.getId();
        boolean ingresado = detalleOrdenList.stream().anyMatch(d ->d.getProducto().getId()==idProducto);

        if (!ingresado){
            detalleOrdenList.add(detalleOrden);
        }else{
            for (DetalleOrden dO: detalleOrdenList) {
                if (dO.getProducto().getId()==idProducto){
                    dO.setCantidad(dO.getCantidad()+cantidad);
                    dO.setTotal(dO.getCantidad()*dO.getProducto().getPrecio());
                }
            }
        }

        sumaTotal=detalleOrdenList.stream().mapToDouble(dt->dt.getTotal()).sum();
        orden.setTotal(sumaTotal);
        model.addAttribute("carrit",detalleOrdenList);
        model.addAttribute("orden",orden);


        return "usuario/carrito";
    }

    @GetMapping("/delete/cart/{id}")
    public String deleteProductoCart(@PathVariable Integer id, Model model){

        //lista nueva de productos
        List<DetalleOrden> ordenesNueva = new ArrayList<>();

        for(DetalleOrden dO : detalleOrdenList){
            if(dO.getProducto().getId() != id){
                ordenesNueva.add(dO);
            }
        }
        // remplazo la vieja lista con la nueva lista con los productos restantes
        detalleOrdenList = ordenesNueva;

        double sumaTotal = 0;
        sumaTotal=detalleOrdenList.stream().mapToDouble(dt->dt.getTotal()).sum();
        orden.setTotal(sumaTotal);
        model.addAttribute("carrit",detalleOrdenList);
        model.addAttribute("orden",orden);

        return "usuario/carrito";

    }

    @GetMapping("/getCarrito")
    public String verCarrito(Model model){
        model.addAttribute("carrit",detalleOrdenList);
        model.addAttribute("orden",orden);
        return "usuario/carrito";
    }
    @GetMapping("/order")
    public String order(Model model){
        Usuario usuario = usuarioService.findById(1);

        model.addAttribute("carrit",detalleOrdenList);
        model.addAttribute("orden",orden);
        model.addAttribute("usuario",usuario);
        return "usuario/resumenorden";
    }

}
