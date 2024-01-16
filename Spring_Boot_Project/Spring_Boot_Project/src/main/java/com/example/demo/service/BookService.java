package com.example.demo.service;

import com.example.demo.model.Book;
import com.example.demo.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public ArrayList<Book> findAll()
    {
        return (ArrayList<Book>) bookRepository.findAll();
    }

    public void save(Book book)
    {
        bookRepository.save(book);
    }

    public void deleteById(Integer id)
    {
        bookRepository.deleteById(id);
    }

    public Optional<Book> findById (Integer id)
    {
        return bookRepository.findById(id);
    }
}
