package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Book extends AbstractEntity{

    @NotBlank(message = "Book must have an title!")
    @Size(min = 3, max = 50,message = "Title must be between 3 and 50 characters!")
    private String title;

    @OneToOne(cascade = CascadeType.ALL)
    @Valid
    @NotNull
    private BookDetails bookDetails;

    public Book( String title) {
        this.title = title;
    }
    public Book(){}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BookDetails getBookDetails() {
        return bookDetails;
    }

    public void setBookDetails(BookDetails bookDetails) {
        this.bookDetails = bookDetails;
    }
}

