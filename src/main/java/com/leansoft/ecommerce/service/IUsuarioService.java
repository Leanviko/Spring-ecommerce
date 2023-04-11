package com.leansoft.ecommerce.service;

import com.leansoft.ecommerce.model.Usuario;

public interface IUsuarioService {
    Usuario save(Usuario usuario);
    Usuario findById(Integer id);

}
