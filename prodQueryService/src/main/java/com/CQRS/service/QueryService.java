package com.CQRS.service;

import com.CQRS.dto.ProdEvent;
import com.CQRS.entity.Product;
import com.CQRS.repo.ProdRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QueryService {
    Logger log = LoggerFactory.getLogger(QueryService.class);
    @Autowired
    private ProdRepo repo;

    public List<Product> getAllProd(){
        return repo.findAll();
    }
    @KafkaListener(topics ="productItem-topic", groupId = "productItem-group")
    public void processEvent(ProdEvent prodEvent){
        try{
            log.info("Consumer consumed the message : " + prodEvent.getProd().getName());
            if("CreateProdItem".equals(prodEvent.getEventType()) )
                repo.save(prodEvent.getProd());
            else if ("UpdateProdItem".equals(prodEvent.getEventType())) {
                Product oldProd = repo.findById(prodEvent.getProd().getId()).get();
                oldProd.setName(prodEvent.getProd().getName());
                oldProd.setPrice(prodEvent.getProd().getPrice());
                repo.save(oldProd);
            } else if("DeleteProdItem".equals(prodEvent.getEventType())) {
                repo.deleteById(prodEvent.getProd().getId());
            }
        }catch(ObjectOptimisticLockingFailureException e){
            System.err.println("Concurrent Modification detected = " + e.getMostSpecificCause());
        }
    }
}
