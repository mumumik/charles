package com.mitrais.javabootcamp.charles.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mitrais.javabootcamp.charles.entity.Book;
import com.mitrais.javabootcamp.charles.entity.Status;
import com.mitrais.javabootcamp.charles.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService {

	private BookRepository bookRepository;
	
	@Autowired
	public BookServiceImpl(BookRepository theBookRepository) {
		bookRepository = theBookRepository;
	}
	
	@Override
	public List<Book> findAll() {
		
		List<Book> books = bookRepository.findAllByOrderByStatusAsc();
		
		return books;
	}

	@Override
	public Book findById(int theId) {
		
		Optional<Book> result = bookRepository.findById(theId);
		
		Book theBook = null;
		
		if(result.isPresent()){
			theBook = result.get();
		}else {
			throw new RuntimeException("Book Id not found - " + theId);
		}
		
		return theBook;
	}

	@Override
	public void save(Book theBook) {
		
		bookRepository.save(theBook);

	}

	@Override
	public void deleteById(int theId) {
		
		bookRepository.deleteById(theId);

	}

	@Override
	public List<Book> findByTitle(String theTitle) {
		
		List<Book> books = bookRepository.findByTitleIgnoreCaseContaining(theTitle);		
		
		if(books == null) {
			throw new RuntimeException("Book Title not found - " + theTitle);
		} 
		
		return books;
		
	}

	@Override
	public List<Book> findByStatus(Status theStatus) {

		List<Book> books = bookRepository.findByStatus(theStatus);		
		
		if(books == null) {
			throw new RuntimeException("Book status not found - " + theStatus);
		} 
		
		return books;
		
	}

}
