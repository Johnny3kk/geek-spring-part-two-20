package ru.geekbrains.repr;

import ru.geekbrains.model.Brand;

import java.io.Serializable;

public class BrandRepr implements Serializable {

    private Long id;

    private String name;

    public BrandRepr() {
    }

    public BrandRepr(Brand brand) {
        this.id = brand.getId();
        this.name = brand.getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
