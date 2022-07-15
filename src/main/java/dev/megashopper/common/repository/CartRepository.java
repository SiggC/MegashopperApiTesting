package dev.megashopper.common.repository;

import dev.megashopper.common.entities.Cart;
import dev.megashopper.common.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository  extends JpaRepository<Cart,String> {
    @Query(nativeQuery = true, value = "select (count(c) > 0) from carts c where c.itemId = :itemId")
    boolean existsByItemId(String itemId);

    @Query(nativeQuery = true, value = "select (count(c) > 0) from carts c where c.customer_id = :customerId")
    boolean existsByCustomerId(String customerId);

    @Query(nativeQuery = true, value = "SELECT * FROM Items WHERE customer_id = :customerId")
    List<Item> findItemsByCustomerId(String customerId);
}