package com.leansoft.ecommerce.service;

import com.leansoft.ecommerce.model.Producto;

import java.util.List;
import java.util.Optional;

public interface IProductoService {
    public void save(Producto producto);
    public Optional<Producto> getById(Integer id);
    public List<Producto> getAll();
    public void update(Producto producto);
    public void delete(Integer id);
}
