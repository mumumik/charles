package com.mitrais.javabootcamp.charles;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.mitrais.javabootcamp.charles.entity.Book;
import com.mitrais.javabootcamp.charles.entity.Shelf;
import com.mitrais.javabootcamp.charles.entity.Status;
import com.mitrais.javabootcamp.charles.repository.BookRepository;
import com.mitrais.javabootcamp.charles.repository.ShelfRepository;
import com.mitrais.javabootcamp.charles.service.ShelfServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class ShelfServiceTest {
	
	@InjectMocks
	private ShelfServiceImpl shelfService;
	@Mock
	private ShelfRepository shelfRepository;
	@Mock
	private BookRepository bookRepository;
	
	@Test
	public void findById_ReturnsOk() {
		
		//setup
		when(shelfRepository.findById(anyInt())).thenReturn(
				Optional.of(new Shelf(1, 10, 0, null))
		);
				
		
		//action
		Shelf actual = shelfService.findById(1);
		
		//assertion
		Shelf shelf = actual;
		assertEquals(1, shelf.getShelfId());
		verify(shelfRepository, times(1)).findById(anyInt());
		
	}
	
	@Test
	public void addBook_ReturnsOk() {
		
		//setup
		Book theBook = new Book(1, "978-979-2763-37-9","ROBIN HOOD",Status.not_shelved,"Paul Creswick");
		Shelf theShelf = new Shelf(1, 10, 0, null);
		when(shelfRepository.findById(anyInt())).thenReturn(Optional.of(theShelf));
		when(bookRepository.findById(anyInt())).thenReturn(Optional.of(theBook));
		when(shelfRepository.save(any(Shelf.class))).thenReturn(theShelf);
		
		//action
		Shelf actual = shelfService.addBook(1, 1);
		
		//assertion
		List<Book> expBook = new ArrayList<>();
		expBook.add(new Book(1, "978-979-2763-37-9","ROBIN HOOD",Status.shelved,"Paul Creswick"));
		Shelf expShelf = new Shelf(1, 10, 1, expBook);
		assertEquals(expShelf.getCurrentCapacity(), actual.getCurrentCapacity());
		assertEquals(expShelf.getBooks().toString(),actual.getBooks().toString());
		
	}
	
	@Test
	public void removeBook_ReturnsOk() {
		
		//setup
		Book theBook = new Book(1, "978-979-2763-37-9","ROBIN HOOD",Status.shelved,"Paul Creswick");
		List<Book> bookList = new ArrayList<>();
		bookList.add(theBook);
		Shelf theShelf = new Shelf(1, 10, 1, bookList);
		when(shelfRepository.findById(anyInt())).thenReturn(Optional.of(theShelf));
		when(bookRepository.findById(anyInt())).thenReturn(Optional.of(theBook));
		when(shelfRepository.save(any(Shelf.class))).thenReturn(theShelf);
		
		//action
		Shelf actual = shelfService.removeBook(1, 1);
		
		//assertion
		List<Book> expBook = new ArrayList<>();		
		Shelf expShelf = new Shelf(1, 10, 0, expBook);
		assertEquals(expShelf.getCurrentCapacity(), actual.getCurrentCapacity());
		assertEquals(expShelf.getBooks().toString(),actual.getBooks().toString());
		
	}

}
