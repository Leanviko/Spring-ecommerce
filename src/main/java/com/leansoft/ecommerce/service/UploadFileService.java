package com.leansoft.ecommerce.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class UploadFileService {

    private String folder = "images//";


    public String saveImage(MultipartFile file) throws IOException {
        if (!file.isEmpty()){
            byte [] bytes = file.getBytes();//Crea un archivo del tipo byte
            Path path = Paths.get(folder+file.getOriginalFilename());//crea la direccion de almacenamiente
            Files.write(path,bytes);//guarda o sobreescribe el archivo el la direccion especificada
            return file.getOriginalFilename();
        }
        return "default.jpg";
    }

    public void deleteImage(String nombre){
        //String ruta = "images//";
        File file = new File(folder+nombre);
        file.delete();
    }
}
