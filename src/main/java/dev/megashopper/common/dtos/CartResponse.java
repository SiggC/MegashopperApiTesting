package dev.megashopper.common.dtos;

import dev.megashopper.common.entities.Cart;
import dev.megashopper.common.entities.Item;
import dev.megashopper.common.entities.User;
import dev.megashopper.common.service.UserService;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CartResponse {

    private List<Item> items;
    private String customerId;
    private UserService userService;


    public CartResponse(Cart cart) {
        this.customerId = cart.getCustomer().getCustomerId();
    }

    public void createCart(Cart cart) {
        this.items = cart.getItems();
        this.customerId = cart.getCustomer().getCustomerId();
    }
}