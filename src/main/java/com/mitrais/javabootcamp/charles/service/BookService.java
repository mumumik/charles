package com.mitrais.javabootcamp.charles.service;

import java.util.List;

import com.mitrais.javabootcamp.charles.entity.Book;
import com.mitrais.javabootcamp.charles.entity.Status;

public interface BookService {

	public List<Book> findAll();
	
	public Book findById(int theId);
	
	public List<Book> findByTitle(String theTitle);
	
	public List<Book> findByStatus(Status theStatus);
	
	public void save(Book theBook);
	
	public void deleteById(int theId);
	
}
