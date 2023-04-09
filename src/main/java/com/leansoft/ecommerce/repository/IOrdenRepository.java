package com.leansoft.ecommerce.repository;

import com.leansoft.ecommerce.model.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrdenRepository extends JpaRepository<Orden,Integer> {
}
