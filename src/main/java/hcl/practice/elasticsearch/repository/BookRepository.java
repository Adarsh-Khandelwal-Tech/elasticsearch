package hcl.practice.elasticsearch.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import hcl.practice.elasticsearch.model.Book;

import java.util.List;

public interface BookRepository extends ElasticsearchRepository<Book, String> {

    List<Book> findByTitle(String title);

}