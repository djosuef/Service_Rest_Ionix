package com.ionix.demo.controller;

import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ionix.demo.service.CifradoDesService;
import com.ionix.demo.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RestController;

@RestController
@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Cifrado", description = "Servicio que cifra un parámetro y retorna los datos del usuario desde un servicio externo")
public class CifradoDesController {
    @Autowired
    private CifradoDesService cifradoDesService;

    @Autowired
    private UserService userService;


    @PostMapping("/cifrar/{parametro}")
    public ResponseEntity<?> cifrar(@PathVariable("parametro") String parametro) throws JSONException {

        Optional<?> cadenaCifrada = cifradoDesService.cifrarDES(parametro);
        if (cadenaCifrada.isPresent()) {
            JSONObject dataUser = userService.findDataUsers(cadenaCifrada.get().toString());
            //System.out.println("DATA USER: " + dataUser);
            return ResponseEntity.ok(dataUser.toString());
        } else {
            return ResponseEntity.badRequest().body("Error al cifrar");
        }


          
    }
}
