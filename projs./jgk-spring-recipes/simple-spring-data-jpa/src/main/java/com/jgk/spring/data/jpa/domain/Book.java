package com.jgk.spring.data.jpa.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@SuppressWarnings("serial")
@javax.persistence.Entity
@Table(name="BOOK")
public class Book implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name="BOOK_ID")
	private Long id;
	private String title;
	private String author;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public static Book createBook(String title, String author) {
		Book book = new Book();
		book.setTitle(title);
		book.setAuthor(author);
		return book;
	}
	
}
