package com.CQRS.service;

import com.CQRS.dto.ProdEvent;
import com.CQRS.entity.Product;
import com.CQRS.repo.ProdRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QueryService {
    @Autowired
    private ProdRepo repo;

    public List<Product> getAllProd(){
        return repo.findAll();
    }
    @KafkaListener(topics ="productItem-topic", groupId = "productItem-group")
    public void processEvent(ProdEvent prodEvent){
        if("CreateProdItem".equals(prodEvent.getEventType()) )
            repo.save(prodEvent.getProd());
        else if ("UpdateProdItem".equals(prodEvent.getEventType())) {
            Product oldProd = repo.findById(prodEvent.getProd().getId()).get();
            oldProd.setName(prodEvent.getProd().getName());
            oldProd.setPrice(prodEvent.getProd().getPrice());
            repo.save(oldProd);
        }
    }
}
