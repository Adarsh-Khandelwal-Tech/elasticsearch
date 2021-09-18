package hcl.practice.elasticsearch.service;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import hcl.practice.elasticsearch.model.Book;

@Service
public class QueryDSLService {
	private static final String BOOK_INDEX="book_index";
	@Autowired
	private ElasticsearchRestTemplate template;

	public List<SearchHits<Book>> searchMultiField(String title, String author) {
		QueryBuilder query = QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("title", title))
				.must(QueryBuilders.matchQuery("author", author));
		NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder().withQuery(query).build();
		List<NativeSearchQuery> list=new ArrayList<>();
		list.add(nativeSearchQuery);
		List<SearchHits<Book>> books=template.multiSearch(list, Book.class, IndexCoordinates.of(BOOK_INDEX));
		//List<Book> books = template.queryForList(nativeSearchQuery, Book.class,IndexCoordinates.of(BOOK_INDEX));
		return books;
	}
	
	public List<SearchHits<Book>> getBookSearchData(String input) {
		String search = ".*" + input + ".*";
		NativeSearchQuery nativeSearchQuery=new NativeSearchQueryBuilder()
				.withFilter(QueryBuilders.regexpQuery("title", search)).build();
		List<NativeSearchQuery> list=new ArrayList<>();
		list.add(nativeSearchQuery);
		List<SearchHits<Book>> books=template.multiSearch(list, Book.class, IndexCoordinates.of(BOOK_INDEX));
		return books;
	}
	
	public List<SearchHits<Book>> multiMatchQuery(String text) {
		NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(QueryBuilders.multiMatchQuery(text)
				.field("title").field("author").type(MultiMatchQueryBuilder.Type.BEST_FIELDS)).build();
		List<NativeSearchQuery> list=new ArrayList<>();
		list.add(searchQuery);
		List<SearchHits<Book>> books=template.multiSearch(list, Book.class, IndexCoordinates.of(BOOK_INDEX));
		return books;
	}
}
