package dev.megashopper.controllers;

import dev.megashopper.common.dtos.*;
import dev.megashopper.common.entities.Cart;
import dev.megashopper.common.entities.Item;
import dev.megashopper.common.entities.User;
import dev.megashopper.common.repository.ItemRepository;
import dev.megashopper.common.repository.UserRepository;
import dev.megashopper.common.service.CartService;
import dev.megashopper.common.service.ItemService;
import dev.megashopper.common.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    private final ItemService itemService;
    private final TokenService tokenService;
    private final UserService userService;
    @Autowired
    public CartController(CartService cartService, ItemService itemService, TokenService tokenService, UserService userService) {
        this.cartService = cartService;
        this.itemService = itemService;
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @GetMapping(produces = "application/json", value = "/cart/items")
    public List<CartResponse> getAllItems() {
        return cartService.fetchAllItems();
    }

    @PostMapping("add/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addItem(@PathVariable String itemId, @RequestHeader String token) {
        ItemResponsePayload i = itemService.findById(itemId);
        UserResponsePayload u = userService.findById(String.valueOf(tokenService.extractTokenDetails(token).getAuthCustomerId()));

        cartService.addItem(i.getItemId(), u.getCustomerId());
    }
    @PatchMapping("/remove/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void removeItem(@PathVariable String itemId, @RequestHeader String token) {

    }
}
