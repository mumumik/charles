package com.mitrais.javabootcamp.charles.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mitrais.javabootcamp.charles.entity.Book;
import com.mitrais.javabootcamp.charles.entity.Status;

public interface BookRepository extends JpaRepository<Book, Integer> {

	public List<Book> findAllByOrderByStatusAsc();
	
	public List<Book> findByTitleIgnoreCaseContaining(String theTitle);
	
	public List<Book> findByStatus(Status theStatus);
	
}
