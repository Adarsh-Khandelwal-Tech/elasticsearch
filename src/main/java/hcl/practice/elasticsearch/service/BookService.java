package hcl.practice.elasticsearch.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import hcl.practice.elasticsearch.model.Book;

import java.util.List;

public interface BookService {

    Book save(Book book);

    void delete(Book book);

    Book findOne(String id);

    Iterable<Book> findAll();

    List<Book> findByTitle(String title);

}