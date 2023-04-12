package com.leansoft.ecommerce.repository;

import com.leansoft.ecommerce.model.Orden;
import com.leansoft.ecommerce.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrdenRepository extends JpaRepository<Orden,Integer> {
    List<Orden> findByUsuario (Usuario usuario);
}
