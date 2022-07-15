package dev.megashopper.common.entities;

import dev.megashopper.common.repository.CartRepository;
import dev.megashopper.common.repository.ItemRepository;
import dev.megashopper.common.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "carts")
public class Cart implements Serializable {

    @Id
    @Column(name = "cart_id", nullable = false)
    private int cartId;

    @ManyToMany
    @JoinTable(
        name = "cart_items",
        joinColumns = @JoinColumn(name = "cart_id"),
        inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<Item> items;

    @OneToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;

    public Cart() {
        super();
        this.items = new ArrayList<>();
    }

    public Cart(int cartId) {
        this();
        this.cartId = cartId;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public void addItems(Item... items) {
        if (this.items == null)
            this.items = new ArrayList<>();
        this.items.addAll(Arrays.asList(items));
    }

    public void removeItem(Item... items) {
        this.items.removeAll(Arrays.asList(items));
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cartId=" + cartId +
                ", items=" + items +
                ", customerId=" + customer.getCustomerId() +
                '}';
    }

}
