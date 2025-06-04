package com.lib.mgmt;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
@Data
public class Patron {
	private static long lastId=6500;
	private long patronId;
	private String name;
	private String address;
	private String adharId;
	private List<Book> books;
	public Patron( String name, String address, String adharId) {
		super();
		this.patronId = ++lastId;
		this.name = name;
		this.address = address;
		this.adharId = adharId;
		this.books = new ArrayList<>();
	}
	public void getBooksBorrowed() {
		System.out.println(name+" :");
		books.stream().forEach(b->{System.out.println(b.getTitle());});
	}
}
