package com.bonappetit.model.entity;

import com.bonappetit.model.enums.CategoryEnum;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "categories")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryEnum name;

    @Column
    private String description;

    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
    private Set<RecipeEntity> recipes;

    public CategoryEntity() {
    }

    public Long getId() {
        return id;
    }

    public CategoryEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public CategoryEnum getName() {
        return name;
    }

    public CategoryEntity setName(CategoryEnum name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CategoryEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public Set<RecipeEntity> getRecipes() {
        return recipes;
    }

    public CategoryEntity setRecipes(Set<RecipeEntity> recipes) {
        this.recipes = recipes;
        return this;
    }
}
