package com.mitrais.javabootcamp.charles;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.mitrais.javabootcamp.charles.entity.Book;
import com.mitrais.javabootcamp.charles.entity.Status;
import com.mitrais.javabootcamp.charles.rest.BookRestController;
import com.mitrais.javabootcamp.charles.service.BookServiceImpl;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@WebMvcTest(BookRestController.class)
public class BookRestControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private BookServiceImpl bookService;
	
	@Test
	public void get_AllBooksOrderByStatus_ReturnsOk() throws Exception{
		
		//setup
		List<Book> bookList = new ArrayList<>();
		bookList.add(new Book(3, "978-979-2763-37-9","ROBIN HOOD 3",Status.not_shelved,"Paul Creswick"));
		bookList.add(new Book(1, "978-979-2763-37-5","ROBIN HOOD",Status.shelved,"Paul Creswick"));
		bookList.add(new Book(2, "978-979-2763-37-4","ROBIN HOOD 2",Status.shelved,"Paul Creswick"));
		when(bookService.findAll()).thenReturn(bookList);
		
		//action & assertion
		mockMvc.perform(
				MockMvcRequestBuilders.get("/books/"))
				.andExpect(content().json("[{id:3,isbn:'978-979-2763-37-9',title:'ROBIN HOOD 3',author:'Paul Creswick',status:'not_shelved'},"
						+ "{id:1,isbn:'978-979-2763-37-5',title:'ROBIN HOOD',author:'Paul Creswick',status:'shelved'},"
						+ "{id:2,isbn:'978-979-2763-37-4',title:'ROBIN HOOD 2',author:'Paul Creswick',status:'shelved'}]"));
		
	}
	
	@Test
	public void get_booksByTitle_ReturnsOk() throws Exception{
		
		//setup
		List<Book> bookList = new ArrayList<>();
		bookList.add(new Book(1, "978-979-2763-37-9","ROBIN HOOD",Status.not_shelved,"Paul Creswick"));
		when(bookService.findByTitle(anyString())).thenReturn(bookList);
		
		//action & assertion
		mockMvc.perform(
				MockMvcRequestBuilders.get("/books/list-books-by-title?bookTitle=robin"))
				.andExpect(content().json("[{id:1,isbn:'978-979-2763-37-9',title:'ROBIN HOOD',author:'Paul Creswick',status:'not_shelved'}]"));
		
	}
	
	@Test
	public void get_booksByStatus_ReturnsOk() throws Exception{
		
		//setup
		List<Book> bookListNotShelved = new ArrayList<>();
		bookListNotShelved.add(new Book(1, "978-979-2763-37-9","ROBIN HOOD",Status.not_shelved,"Paul Creswick"));
		List<Book> bookListShelved = new ArrayList<>();
		bookListShelved.add(new Book(2, "978-979-2763-37-4","ROBIN HOOD 2",Status.shelved,"Paul Creswick"));
		when(bookService.findByStatus(Status.not_shelved)).thenReturn(bookListNotShelved);
		when(bookService.findByStatus(Status.shelved)).thenReturn(bookListShelved);
				
		//action & assertion
		mockMvc.perform(
				MockMvcRequestBuilders.get("/books/list-books-by-status?bookStatus=not_shelved"))
				.andExpect(content().json("[{id:1,isbn:'978-979-2763-37-9',title:'ROBIN HOOD',author:'Paul Creswick',status:'not_shelved'}]"));
		
		mockMvc.perform(
				MockMvcRequestBuilders.get("/books/list-books-by-status?bookStatus=shelved"))
				.andExpect(content().json("[{id:2,isbn:'978-979-2763-37-4',title:'ROBIN HOOD 2',author:'Paul Creswick',status:'shelved'}]"));
		
	}

}
