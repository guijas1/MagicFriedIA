package com.java10.MagicFridgeAI.service;

import com.java10.MagicFridgeAI.model.FoodItem;
import com.java10.MagicFridgeAI.repository.FoodItemRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodItemService {


    @Autowired
    private FoodItemRepository fdR;

    public FoodItem save(FoodItem foodItem){

        return fdR.save(foodItem);
    }

    public List<FoodItem> getAll(){
        return fdR.findAll();
    }

    public FoodItem getById(Long id){
        return fdR.findById(id).orElseThrow();
    }

    public void delete(Long id){
        fdR.deleteById(id);
    }

    public FoodItem update(Long id, FoodItem updateItem){
        FoodItem existing = fdR.findById(id).orElseThrow(() -> new EntityNotFoundException("Item não encontrado com o id" + id));

        existing.setNome(updateItem.getNome());
        existing.setCategoria(updateItem.getCategoria());
        existing.setQtd(updateItem.getQtd());
        existing.setValidade(updateItem.getValidade());

        return fdR.save(existing);

    }

    public List<FoodItem> saveAll(List<FoodItem> items){
        return fdR.saveAll(items);
    }
}
