package com.mitrais.javabootcamp.charles;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
//import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.mitrais.javabootcamp.charles.entity.Book;
import com.mitrais.javabootcamp.charles.entity.Status;
import com.mitrais.javabootcamp.charles.repository.BookRepository;
import com.mitrais.javabootcamp.charles.service.BookServiceImpl;


@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

	@InjectMocks
	private BookServiceImpl bookService;
	@Mock
	private BookRepository bookRepository;
	
	@Test
	public void findById_ReturnsOk() {
		
		//setup
		when(bookRepository.findById(anyInt())).thenReturn(
				Optional.of(new Book(1, "978-979-2763-37-9","ROBIN HOOD",Status.not_shelved,"Paul Creswick"))
		);
				
		
		//action
		Book actual = bookService.findById(1);
		
		//assertion
		Book book = actual;
		assertEquals("ROBIN HOOD", book.getTitle());
		verify(bookRepository, times(1)).findById(anyInt());
		
	}
	
	@Test( expected = RuntimeException.class)
	public void findById_ReturnsRuntimeException(){
		
		//setup
		when(bookRepository.findById(anyInt())).thenReturn(
				Optional.empty()
		);
		
		//action & assertion
		bookService.findById(1);
		
	}
	
	@Test
	public void findAll_ReturnsOk() {
		
		//setup
		List<Book> bookList = new ArrayList<>();
		bookList.add(new Book(3, "978-979-2763-37-9","ROBIN HOOD 3",Status.not_shelved,"Paul Creswick"));
		bookList.add(new Book(1, "978-979-2763-37-5","ROBIN HOOD",Status.shelved,"Paul Creswick"));
		bookList.add(new Book(2, "978-979-2763-37-4","ROBIN HOOD 2",Status.shelved,"Paul Creswick"));
		when(bookRepository.findAllByOrderByStatusAsc()).thenReturn(bookList);
		
		//action
		List<Book> actual = bookService.findAll();
		
		//assertion
		List<Book> expected = new ArrayList<>();
		expected.add(new Book(3, "978-979-2763-37-9","ROBIN HOOD 3",Status.not_shelved,"Paul Creswick"));
		expected.add(new Book(1, "978-979-2763-37-5","ROBIN HOOD",Status.shelved,"Paul Creswick"));
		expected.add(new Book(2, "978-979-2763-37-4","ROBIN HOOD 2",Status.shelved,"Paul Creswick"));
		assertEquals(expected.get(0).toString(), actual.get(0).toString());
		assertEquals(expected.get(1).toString(), actual.get(1).toString());
		assertNotEquals(expected.get(2).toString(), actual.get(1).toString());
		
	}
	
	@Test
	public void findByTitle_ReturnsBooks() {
		
		//setup
		List<Book> bookList = new ArrayList<>();
		bookList.add(new Book(1, "978-979-2763-37-9","ROBIN HOOD",Status.not_shelved,"Paul Creswick"));
		bookList.add(new Book(2, "978-979-2763-37-5","ROBIN HOOD 2",Status.not_shelved,"Paul Creswick"));
		bookList.add(new Book(3, "978-979-2763-37-4","ROBIN HOOD 3",Status.not_shelved,"Paul Creswick"));
		when(bookRepository.findByTitleIgnoreCaseContaining(anyString())).thenReturn(bookList);
		
		//action
		List<Book> actual = bookService.findByTitle("robin");		
		
		//assertion
		List<Book> expected = new ArrayList<>();
		expected.add(new Book(1, "978-979-2763-37-9","ROBIN HOOD",Status.not_shelved,"Paul Creswick"));
		expected.add(new Book(2, "978-979-2763-37-5","ROBIN HOOD 2",Status.not_shelved,"Paul Creswick"));
		expected.add(new Book(3, "978-979-2763-37-4","ROBIN HOOD 3",Status.not_shelved,"Paul Creswick"));
		//assertTrue(actual.containsAll(expected));
		assertEquals(expected.get(0).toString(), actual.get(0).toString());
		assertEquals(expected.get(1).toString(), actual.get(1).toString());
		assertNotEquals(expected.get(2).toString(), actual.get(1).toString());
		
	}
	
	@Test
	public void findByStatus_ReturnsOk() {
		
		//setup
		List<Book> bookList = new ArrayList<>();
		bookList.add(new Book(1, "978-979-2763-37-9","ROBIN HOOD",Status.not_shelved,"Paul Creswick"));		
		when(bookRepository.findByStatus(any(Status.class))).thenReturn(bookList);
		
		//action
		List<Book> actual = bookService.findByStatus(Status.not_shelved);
		
		//assertion
		List<Book> expected = new ArrayList<>();
		expected.add(new Book(1, "978-979-2763-37-9","ROBIN HOOD",Status.not_shelved,"Paul Creswick"));
		assertEquals(expected.get(0).getIsbn(),actual.get(0).getIsbn());
		
	}
	

	
}
