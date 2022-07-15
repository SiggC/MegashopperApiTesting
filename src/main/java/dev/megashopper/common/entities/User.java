package dev.megashopper.common.entities;

import dev.megashopper.common.utils.Generation;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "customers")
public class User implements Comparable<User> {

    @Id
    @Column(name = "customer_id", nullable = false, unique = true)
    private String customerId;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false, unique = true, columnDefinition  = "VARCHAR CHECK (LENGTH(username) >= 8)")
    private String username;
    @Embedded
    @Column(nullable = false)
    private Password password;
    @JoinColumn(name = "cart_id", nullable = false)
    @OneToOne(mappedBy = "customer")
    private Cart cart;

    public User() {
        super();
    }

    public User(String firstName, String lastName, String email, String username) {
        this.customerId = UUID.randomUUID().toString();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
    }

    public User(String firstName, String lastName, String email, String username, Password password) {
        this(firstName, lastName, email, username);
        this.password = password;
    }
    public User(String firstName, String lastName, String email, String address, String username, Password password) {
        this(firstName, lastName, email, username, password);
        this.address = address;
    }
    public User(String customerId, String firstName, String lastName, String email, String address, String username, Password password) {
        this(firstName, lastName, email, address, username, password);
        this.customerId = customerId;
    }


    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Password getPassword() {
        return password;
    }

    public void setPassword(Password password) {
        this.password = password;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
    public int compareTo(User o) {
        if (this == o) return 0;
        if (getCustomerId() != null) {
            return getCustomerId().compareTo(o.getCustomerId());
        } else {
            return -1;
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "customerId='" + customerId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", username='" + username + '\'' +
                ", password=" + password +
                ", cart=" + cart +
                '}';
    }

    public void setLoggedIn(boolean b) {

    }
}

