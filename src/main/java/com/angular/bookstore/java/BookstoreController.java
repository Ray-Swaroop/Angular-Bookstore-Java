package com.angular.bookstore.java;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/book")
public class BookstoreController {
	private List<Bookstore> book = new ArrayList<Bookstore>();
	BookstoreController() {
		this.book = buildBook();
	}
	@RequestMapping(method = RequestMethod.GET)
	public List<Bookstore> getbook() {
		return this.book;
	}
	@RequestMapping(value = "/bookName", method = RequestMethod.GET)
	public List<String> getbookName() {
		List<String> bookList=new ArrayList<String>();
		for(Bookstore bookName:this.book){
			bookList.add(bookName.getBookName());
		}
		return bookList;
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Bookstore getBook(@PathVariable("id") Long id) {
		return this.book.stream().filter(book -> book.getBookId()== id).findFirst().orElse(null);
	}
	@RequestMapping(value = "/bookName/{name}", method = RequestMethod.GET)
	public List<String> getBookName(@PathVariable("name") String name) {
		List<String> bookList=new ArrayList<String>();
		for(Bookstore bookName:this.book){
			if(bookName.getBookName().contains(name)){
				bookList.add(bookName.getBookName());
			}
		}
		return bookList;
	}
	@RequestMapping(method = RequestMethod.POST)
	public List<Bookstore> saveBook(@RequestBody Bookstore book) {
		Long nextId = 0L;
		if (this.book.size() != 0) {
			Bookstore lastbook = this.book.stream().skip(this.book.size() - 1).findFirst().orElse(null);
			nextId = lastbook.getBookId() + 1;
		}
		book.setBookId(nextId);
		this.book.add(book);
		return this.book;
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public List<Bookstore> deleteBook(@PathVariable Long id) {
		for(Iterator<Bookstore> itr=this.book.iterator();itr.hasNext();)
		{
			Bookstore book = itr.next();
			Long inId = book.getBookId();
			if(inId == (id)){
				itr.remove();
			}
		}
		return this.book;
	}
	List<Bookstore> buildBook() {
		List<Bookstore> books = new ArrayList<>();
		Bookstore book1 = buildBook(1L, "Angular-Basics", 550L, "Jacob Markus");
		Bookstore book2 = buildBook(2L, "Angular-Advanced", 950L, "Stephen Markus");
		Bookstore book3 = buildBook(2L, "Angular-Base", 350L, "Stephen Markus");
		books.add(book1);
		books.add(book2);
		books.add(book3);
		return books;
	}
	
	Bookstore buildBook(long bookId, String bookName, long price, String author) {
		Bookstore book = new Bookstore(bookId, bookName, price, author);
		return book;
	}
}

