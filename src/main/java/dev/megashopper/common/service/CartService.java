package dev.megashopper.common.service;

import dev.megashopper.common.dtos.*;
import dev.megashopper.common.entities.Cart;
import dev.megashopper.common.entities.Item;
import dev.megashopper.common.entities.User;
import dev.megashopper.common.repository.CartRepository;
import dev.megashopper.common.repository.ItemRepository;
import dev.megashopper.common.utils.exceptions.ItemNotFoundException;
import dev.megashopper.common.utils.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }


    public List<CartResponse> fetchAllItems() {
        return cartRepository.findAll()
                .stream()
                .map(CartResponse::new)
                .collect(Collectors.toList());
    }
    public ResourceCreationResponse addItem(String itemId, String userId) {
        Item item = itemRepository.findById(itemId).get();
        Cart cart = cartRepository.findById(userId).get();

        cart.addItems(item);
        cartRepository.save(cart);
        return new ResourceCreationResponse(String.valueOf(cart.getCartId()));
    }
    public void removeItem(ItemRequestPayload itemRequestPayload, UserRequestPayload userRequestPayload) {
        Item item = itemRequestPayload.extractResource();
        User user = userRequestPayload.extractResource();

        Cart cart = cartRepository.findById(user.getCustomerId()).get();
        if (!cartRepository.existsByItemId(item.getItemId()))
            throw new ItemNotFoundException("Cart does not contain this item");
        cart.removeItem(item);
        cartRepository.save(cart);
    }
}
