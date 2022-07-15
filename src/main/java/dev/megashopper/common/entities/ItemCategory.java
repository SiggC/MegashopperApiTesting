package dev.megashopper.common.entities;

import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "categories")
public class ItemCategory {
    @Id
    @Column(name = "category_id", nullable = false, unique = true)
    private int categoryId;
    @Column(name = "category_name", nullable = false, unique = true)
    private String categoryName;

    public ItemCategory() {
        super();
    }

    public ItemCategory(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
