package dev.megashopper.common.service;

import dev.megashopper.common.dtos.ItemRequestPayload;
import dev.megashopper.common.dtos.ResourceCreationResponse;
import dev.megashopper.common.entities.Item;
import dev.megashopper.common.dtos.ItemResponsePayload;
import dev.megashopper.common.repository.ItemRepository;
import dev.megashopper.common.utils.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
@Transactional
public class ItemService {
    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }


    public List<ItemResponsePayload> fetchAllItems() {
        return itemRepository.findAll()
                .stream()
                .map(ItemResponsePayload::new)
                .collect(Collectors.toList());
    }
    public ItemResponsePayload findById(String id) {
        return itemRepository.findById(id)
                .map(ItemResponsePayload::new)
                .orElseThrow(ResourceNotFoundException::new);
    }


    public ResourceCreationResponse createItem(ItemRequestPayload itemRequest) {
        Item newItem = itemRequest.extractResource();
        itemRepository.save(newItem);

        return new ResourceCreationResponse(newItem.getItemId());
    }

    public void updateItem(ItemRequestPayload updatedItemRequest) {
        Item updatedItem = updatedItemRequest.extractResource();
        Item itemForUpdate = itemRepository.findById(updatedItem.getItemId()).orElseThrow(ResourceNotFoundException::new);
    }

    public void deleteitemById(String id) {
        itemRepository.deleteById(id);
    }
}
