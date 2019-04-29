package com.mitrais.javabootcamp.charles.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="book")
public class Book {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="isbn")
	private String isbn;
	
	@Column(name="title")
	private String title;
	
	@Column(name="author")
	private String author;
	
	@Enumerated(EnumType.STRING)
	private Status status;
	
	public Book() {
		
	}

	public Book(String isbn, String title, String author, Status status) {
		this.isbn = isbn;
		this.title = title;
		this.author = author;
		this.status = status;
	}

	public Book(int id, String isbn, String title, Status status, String author) {
		this.id = id;
		this.isbn = isbn;
		this.title = title;
		this.author = author;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", isbn=" + isbn + ", title=" + title + ", author=" + author + ", status=" + status
				+ "]";
	}
	
	
	
}
