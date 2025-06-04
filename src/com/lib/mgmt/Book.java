package com.lib.mgmt;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Book {
	private static long lastID=4500;
	private long bookId;
	private String title;
	private String author;
	private long ISBN;
	private int pubYear;
	private int quantity;
	private int available;
	public List<Patron> borrowers;
	public Book(String title, String author, long iSBN, int pubYear, int quantity) {
		super();
		this.title = title;
		this.author = author;
		ISBN = iSBN;
		this.pubYear = pubYear;
		this.quantity = quantity;
		available=quantity;
		borrowers=new ArrayList<>();
		bookId=++lastID;
	}
	public void giveBook(Patron p) {
		borrowers.add(p);
		available--;
	}
	public void takeBook(Patron p) {
		borrowers.remove(p);
		available--;
	}
	public void printBorrowers() {
		System.out.println(title+" :");
		borrowers.stream().forEach(b->{System.out.println(b.getName());});
	}
}
