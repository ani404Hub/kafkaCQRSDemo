package com.CQRS.controller;

import com.CQRS.dto.ProdEvent;
import com.CQRS.entity.Product;
import com.CQRS.service.CommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class CommandController {
    @Autowired
    private CommandService comservice;

    @PostMapping
    public Product createProd(@RequestBody Product prod){
        return comservice.createProd(prod);
    }
    @PutMapping("/{id}")
    public Product updateProd(@PathVariable long id,@RequestBody Product prod){
        return comservice.updateProd(id, prod);
    }
    @DeleteMapping("/{id}")
    public String deleteProd(@PathVariable long id){
        return comservice.deleteProdById(id);
    }
}
