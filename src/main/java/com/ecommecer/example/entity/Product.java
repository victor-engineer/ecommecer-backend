package com.ecommecer.example.entity;

import jakarta.persistence.*;

@Entity
//especifiquei o nome da tabela para vê se corrige o erro dizendo: "table product does not existsudo usermod -aG docker $USER"
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String description;
    private Double price;


    public Product(Long id, String nome, double price) {
        this.id = id;
        this.nome = nome;
        this.price = price;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
