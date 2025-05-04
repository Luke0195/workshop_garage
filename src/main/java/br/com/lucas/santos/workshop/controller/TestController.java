package br.com.lucas.santos.workshop.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test")
public class TestController {


    @GetMapping(value = "/test")
    public String hello(){
        return "Hello stranger";
    }
}


