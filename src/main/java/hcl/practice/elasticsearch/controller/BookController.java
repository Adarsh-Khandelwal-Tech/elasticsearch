package hcl.practice.elasticsearch.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hcl.practice.elasticsearch.model.Book;
import hcl.practice.elasticsearch.service.BookService;
import hcl.practice.elasticsearch.service.QueryDSLService;

@RestController
public class BookController {
	@Autowired
	private BookService bookService;
	
	@Autowired
	private QueryDSLService queryDSLService;
	
	@PostMapping(value="/books")
	public void postBooks(@RequestBody Book book) {
		bookService.save(book);
	}
	
	@GetMapping(value="/books")
	public List<Book> getBook(@RequestParam(name="title",required=true) String title) {
		return bookService.findByTitle(title);		
	}
	
	@GetMapping("/books/{title}/{author}")
	public List<SearchHits<Book>> serachByMultiField(@PathVariable String title, @PathVariable String author) {
		return queryDSLService.searchMultiField(title, author);
	}
	
	@GetMapping("/books/{text}")
	public List<SearchHits<Book>> serachByText(@PathVariable String text) {
		return queryDSLService.getBookSearchData(text);
	}
	
	@GetMapping("/books/search/{text}")
	public List<SearchHits<Book>> doMultimatchQuery(@PathVariable String text) {
		return queryDSLService.multiMatchQuery(text);
	}

}
