package com.muraishi.spring_bms.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;


@Entity
@Table(name="bookinfo")
public class Book {
	
	//ISBN
	@Id
	@Column(length = 20)
	@NotEmpty(message = "Please enter ISBN")
	private String isbn;
	
	public String getIsbn() {
		return isbn;
	}
	
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	
	//title
	@Column(length = 100, nullable = true)
	@NotEmpty(message = "Please enter TITLE")
	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	//price
	@Column(length = 11, nullable = true)
	@NotEmpty(message = "Please enter PRICE")
	private String price;

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
}
