package com.CQRS.controller;

import com.CQRS.entity.Product;
import com.CQRS.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
public class QueryController {
    @Autowired
    private QueryService qService;

    @GetMapping
    public List<Product> getAllProdList(){
        return qService.getAllProd();
    }
}
