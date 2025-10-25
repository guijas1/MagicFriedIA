package com.java10.MagicFridgeAI.controller;

import com.java10.MagicFridgeAI.model.FoodItem;
import com.java10.MagicFridgeAI.service.FoodItemService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/food")
public class FoodItemController {

    @Autowired
    private FoodItemService service;


    @PostMapping("/criar")
    public ResponseEntity<FoodItem> criar(@RequestBody FoodItem foodItem){
        FoodItem save = service.save(foodItem);
        return ResponseEntity.ok(foodItem);
    }
    @GetMapping
    public ResponseEntity<List<FoodItem>> achaTodos(){

        return ResponseEntity.ok(service.getAll());

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<FoodItem> atualizar(
            @RequestBody FoodItem foodItem,
            @PathVariable Long id) {

        try{
            FoodItem atualizado = service.update(id, foodItem);
            return ResponseEntity.ok(atualizado);
        }catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/saveList")
    public ResponseEntity<List<FoodItem>> criarVarios(@RequestBody List<FoodItem> itens) {
        List<FoodItem> salvos = service.saveAll(itens);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvos);
    }

    @GetMapping("{id}")
    public ResponseEntity<FoodItem> getById(@PathVariable Long id){
        FoodItem achado = service.getById(id);
        return ResponseEntity.ok(achado);
    }



}
