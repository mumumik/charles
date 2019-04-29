package com.mitrais.javabootcamp.charles.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mitrais.javabootcamp.charles.entity.Book;
import com.mitrais.javabootcamp.charles.entity.Shelf;
import com.mitrais.javabootcamp.charles.entity.Status;
import com.mitrais.javabootcamp.charles.repository.BookRepository;
import com.mitrais.javabootcamp.charles.repository.ShelfRepository;

@Service
public class ShelfServiceImpl implements ShelfService {

	@Autowired
	ShelfRepository shelfRepository;
	
	@Autowired
	BookRepository bookRepository;
	
	@Override
	public List<Shelf> findAll() {
		
		List<Shelf> shelfs = shelfRepository.findAll();
		return shelfs;
		
	}

	@Override
	public Shelf findById(int theId) {
		
		Optional<Shelf> result = shelfRepository.findById(theId);
		
		Shelf theShelf = null;
		
		if(result.isPresent()) {
			theShelf = result.get();
		}else {
			throw new RuntimeException("Did not find shelf id - " + theId);
		}
		
		return theShelf;
	}

	@Override
	public void save(Shelf theShelf) {
		
		shelfRepository.save(theShelf);

	}

	@Override
	public void deleteById(int theId) {
		
		shelfRepository.deleteById(theId);

	}

	@Override
	public Shelf addBook(int shelfId, int bookId) {
		
		Optional<Shelf> result = shelfRepository.findById(shelfId);
		
		Shelf theShelf = null;
		
		if(result.isPresent()) {
			theShelf = result.get();
			Book theBook = null;
			Optional<Book> book = bookRepository.findById(bookId);
			if(book.isPresent()) {
				
				int shelfCurrentCapacity;
				int shelfMaximumCapacity;
				
				theBook = book.get();
				
				shelfCurrentCapacity = theShelf.getCurrentCapacity();
				shelfMaximumCapacity = theShelf.getMaxCapacity();
				shelfCurrentCapacity++;
				
				if (shelfCurrentCapacity > shelfMaximumCapacity) {
					throw new RuntimeException("Shelf Curent Capacity cannot exceed the Maximum Capacity");
				}
				
				theShelf.setCurrentCapacity(shelfCurrentCapacity);
				theBook.setStatus(Status.shelved);
				theShelf.addBook(theBook);
				
				shelfRepository.save(theShelf);
				
			}else {
				throw new RuntimeException("Did not find book id - " + bookId);
			}
		} else {
			throw new RuntimeException("Did not find shelf id - " + shelfId);
		}
		
		return theShelf;
		
	}

	@Override
	public Shelf removeBook(int shelfId, int bookId) {

		Optional<Shelf> result = shelfRepository.findById(shelfId);
		
		Shelf theShelf = null;
		
		if(result.isPresent()) {
			theShelf = result.get();
			Book theBook = null;
			Optional<Book> book = bookRepository.findById(bookId);
			if(book.isPresent()) {
				
				int shelfCurrentCapacity;
				
				theBook = book.get();
				
				shelfCurrentCapacity = theShelf.getCurrentCapacity();
				shelfCurrentCapacity--;
				
				theShelf.setCurrentCapacity(shelfCurrentCapacity);
				theBook.setStatus(Status.not_shelved);
				theShelf.removeBook(theBook);
				
				shelfRepository.save(theShelf);
				
			}else {
				throw new RuntimeException("Did not find book id - " + bookId);
			}
		} else {
			throw new RuntimeException("Did not find shelf id - " + shelfId);
		}
		
		return theShelf;
		
	}

}
