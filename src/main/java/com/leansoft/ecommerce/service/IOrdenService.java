package com.leansoft.ecommerce.service;

import com.leansoft.ecommerce.model.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface IOrdenService {
    Orden save(Orden orden);
}
