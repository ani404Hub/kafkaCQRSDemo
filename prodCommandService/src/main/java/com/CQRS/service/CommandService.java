package com.CQRS.service;

import com.CQRS.entity.Product;
import com.CQRS.dto.ProdEvent;
import com.CQRS.repo.ProdRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommandService<prod> {
    @Autowired
    private ProdRepo repo;

    /*@Autowired  --Dont work as we call "Product prod"
    private Product prod;*/
    @Autowired
    private KafkaTemplate<String, Object> tmplt;

    public Product createProd(Product prod){
        Product product = repo.save(prod);
        ProdEvent event = new ProdEvent("CreateProdItem", product);
        tmplt.send("productItem-topic", event);
        return prod;
    }

    public Product updateProd(long id, Product prod){
        Product oldProd = repo.findById(id).get();
        if(!prod.getName().isBlank())                       //isBlank is better than isEmpty as it also check whitespaces
            oldProd.setName(prod.getName());
        if(!prod.getDescription().isBlank())
            oldProd.setDescription(prod.getDescription());
        if(prod.getPrice() != oldProd.getPrice())
            oldProd.setPrice(prod.getPrice());
        Product updateProdItem = repo.save(oldProd);
        ProdEvent event = new ProdEvent("UpdateProdItem", updateProdItem);
        tmplt.send("productItem-topic", event);
        return updateProdItem;
    }

    public String deleteProdById(long id){
        Optional<Product> oldProd = repo.findById(id);
        if(oldProd.isPresent()){
            ProdEvent deleteEvent = new ProdEvent("DeleteProdItem", oldProd.get());
            tmplt.send("productItem-topic", deleteEvent);
            repo.deleteById(oldProd.get().getId());
            return "Product deleted Successfully ";
        }
        else
            return "No Product found to delete ";
    }
}
