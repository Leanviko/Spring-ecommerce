package com.leansoft.ecommerce.service;

import com.leansoft.ecommerce.model.Usuario;

public interface IUsuarioService {
    Usuario findById(Integer id);
}
