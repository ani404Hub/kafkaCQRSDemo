package com.CQRS.service;

import com.CQRS.entity.Product;
import com.CQRS.dto.ProdEvent;
import com.CQRS.repo.ProdRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CommandService<prod> {
    @Autowired
    private ProdRepo repo;

    /*@Autowired  --Dont work as we call "Product prod"
    private Product prod;*/
    @Autowired
    private KafkaTemplate<String, Object> tmplt;

    public Product createProd(ProdEvent prodEvent){
        Product prod = repo.save(prodEvent.getProd());
        ProdEvent event = new ProdEvent("CreateProdItem", prod);
        tmplt.send("productItem-topic", event);
        return prod;
    }

    public Product updateProd(long id, ProdEvent prodEvent){
        Product oldProd = repo.findById(id).get();
        Product prod = prodEvent.getProd();
        oldProd.setName(prod.getName());
        oldProd.setDescription(prod.getDescription());
        oldProd.setPrice(prod.getPrice());
        Product updateProdItem = repo.save(oldProd);
        ProdEvent event = new ProdEvent("UpdateProdItem", updateProdItem);
        tmplt.send("productItem-topic", event);
        return updateProdItem;
    }

    public void deleteProdById(long id){
        repo.deleteById(id);
    }
}
