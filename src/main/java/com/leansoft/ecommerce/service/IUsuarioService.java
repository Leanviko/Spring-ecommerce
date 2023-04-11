package com.leansoft.ecommerce.service;

import com.leansoft.ecommerce.model.Usuario;

import java.util.Optional;

public interface IUsuarioService {
    Usuario save(Usuario usuario);
    Usuario findById(Integer id);
    Optional<Usuario> findByEmail(String email);

}
