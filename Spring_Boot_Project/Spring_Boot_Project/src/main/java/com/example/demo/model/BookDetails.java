package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Entity
public class BookDetails extends AbstractEntity{
    @NotBlank(message = "Book must have an author!")
    @Size(min = 3, max = 50,message = "Author must be between 3 and 50 characters!")
    private String author;

    @NotNull
    private LocalDate publishedDate;

    @NotNull
    private Float price;

    @NotNull
    private int stock;

    public BookDetails(String author, LocalDate publishedDate, Float price, int stock) {
        this.author = author;
        this.publishedDate = publishedDate;
        this.price = price;
        this.stock = stock;
    }
    public BookDetails(){
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
