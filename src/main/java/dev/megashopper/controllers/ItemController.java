package dev.megashopper.controllers;

import dev.megashopper.common.dtos.ItemRequestPayload;
import dev.megashopper.common.dtos.ItemResponsePayload;
import dev.megashopper.common.dtos.ResourceCreationResponse;
import dev.megashopper.common.entities.Item;
import dev.megashopper.common.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping(produces = "application/json")
    public List<ItemResponsePayload> getAllItems() {
        return itemService.fetchAllItems();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResourceCreationResponse createItem(@RequestBody ItemRequestPayload newItem) {
        return itemService.createItem(newItem);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(consumes = "application/json")
    public void updateItemInfo(@RequestBody ItemRequestPayload updatedItemInfo) {
        itemService.updateItem(updatedItemInfo);
    }

    @DeleteMapping()
    public void deleteItem(String id) {
        itemService.deleteitemById(id);
    }
}
