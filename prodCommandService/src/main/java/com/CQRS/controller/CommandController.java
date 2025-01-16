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
    public Product createProd(@RequestBody ProdEvent prodEvent){
        return comservice.createProd(prodEvent);
    }
    @PutMapping("/{id}")
    public Product updateProd(@PathVariable long id,@RequestBody ProdEvent prodEvent){
        return comservice.updateProd(id, prodEvent);
    }
    @DeleteMapping("/{id}")
    public void deleteProd(@PathVariable long id){
        comservice.deleteProdById(id);
    }
}
