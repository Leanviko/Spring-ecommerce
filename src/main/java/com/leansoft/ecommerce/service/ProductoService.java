package com.leansoft.ecommerce.service;

import com.leansoft.ecommerce.model.Producto;
import com.leansoft.ecommerce.repository.IProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService implements IProductoService{

    @Autowired
    private IProductoRepository productoRepository;

    @Override
    public void save(Producto producto) {
        productoRepository.save(producto);
    }

    @Override
    public Optional<Producto> getById(Integer id) {

        return productoRepository.findById(id);
    }

    @Override
    public List<Producto> getAll() {
        return productoRepository.findAll();
    }

    @Override
    public void update(Producto producto) {
        this.save(producto);
    }

    @Override
    public void delete(Integer id) {
        productoRepository.deleteById(id);
    }
}
