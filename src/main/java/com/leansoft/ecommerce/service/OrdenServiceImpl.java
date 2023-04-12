package com.leansoft.ecommerce.service;

import com.leansoft.ecommerce.model.Orden;
import com.leansoft.ecommerce.model.Usuario;
import com.leansoft.ecommerce.repository.IOrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrdenServiceImpl implements IOrdenService{

    @Autowired
    private IOrdenRepository ordenRepository;

    @Override
    public Orden save(Orden orden) {
        return ordenRepository.save(orden);
    }

    @Override
    public List<Orden> findAll() {
        return ordenRepository.findAll();
    }

    public String generarNumeroOrden(){
        int numero=0;
        String numeroConcatenado = "";
        List<Orden> ordenes = this.findAll();
        List<Integer> numeros = new ArrayList<>();

        ordenes.stream().forEach(o->numeros.add(Integer.parseInt(o.getNumero())));

        if (ordenes.isEmpty()){
            numero=1;
        }else{
            numero= numeros.stream().max(Integer::compare).get();//compara numeros a lo largo de la lista y devuelve el maximo
            numero++;
        }
        String numeroString = String.valueOf(numero);
        StringBuilder ceros = new StringBuilder();
        for (int i=0;i<=9-numeroString.length();i++){
            ceros.append("0");
        }
        numeroConcatenado = ceros+numeroString;

        return numeroConcatenado;
    }

    @Override
    public List<Orden> findByUsuario(Usuario usuario) {
        return ordenRepository.findByUsuario(usuario);
    }
}
