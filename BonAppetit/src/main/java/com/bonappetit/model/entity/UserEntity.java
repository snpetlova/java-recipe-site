package com.bonappetit.model.entity;


import jakarta.persistence.*;


import java.util.List;
import java.util.Set;

@Entity
@Table(name="users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column (unique = true, nullable = false)
    private String email;

    @OneToMany(mappedBy = "addedBy", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<RecipeEntity> addedRecipeEntities;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<RecipeEntity> favouriteRecipeEntity;

    public UserEntity() {
    }

    public UserEntity(String username, String password, List<Object> of) {

    }

    public Long getId() {
        return id;
    }

    public UserEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserEntity setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public Set<RecipeEntity> getAddedRecipeEntities() {
        return addedRecipeEntities;
    }

    public UserEntity setAddedRecipeEntities(Set<RecipeEntity> addedRecipeEntities) {
        this.addedRecipeEntities = addedRecipeEntities;
        return this;
    }

    public Set<RecipeEntity> getFavouriteRecipeEntity() {
        return favouriteRecipeEntity;
    }

    public UserEntity setFavouriteRecipeEntity(Set<RecipeEntity> favouriteRecipeEntity) {
        this.favouriteRecipeEntity = favouriteRecipeEntity;
        return this;
    }
}
