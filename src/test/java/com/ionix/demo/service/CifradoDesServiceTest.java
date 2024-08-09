package com.ionix.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;    

@SpringBootTest
public class CifradoDesServiceTest {

    @Autowired
    private CifradoDesService cifradoDesService;

        
    @Test
    public void cifrarDES() {
        String parametro = "1-9";
        String cifradoCorrect = "FyaSTkGi8So=";

        String result = cifradoDesService.cifrarDES(parametro).get().toString();

        // Log the result for debugging
        System.out.println("Expected: " + cifradoCorrect);
        System.out.println("Actual: " + result);

        assertEquals(cifradoCorrect,cifradoDesService.cifrarDES(parametro).get());
        //assertEquals(cifrado, parametro);
    }
}
    