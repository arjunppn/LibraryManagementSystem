package com.lib.mgmt;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InventoryService {

	public static Map<Long,Book> books=new HashMap<>();
	public static Map<Long,Patron> patrons=new HashMap<>();

	public static void addBook(String title, String author, long iSBN, int pubYear, int quantity) {
		Book b=new Book(title,author,iSBN,pubYear,quantity);
		books.put(b.getBookId(),b);
	}
	public static void addPatron(String name, String address, String adharId) {
		Patron p=new Patron(name, address,adharId);
		patrons.put(p.getPatronId(),p);
	}
	public static void assignBook(long patronId,long bookId) {
		Patron p=patrons.get(patronId);
		if(null==p) {
			System.out.println("No patron with id "+patronId);return;
		}
		Book b=books.get(bookId); 	
		if(null==b) {
			System.out.println("No book with id "+bookId);return;
		}
		if(b.getAvailable()>0) {
			p.getBooks().add(b);
			b.giveBook(p);
			System.out.println(b.getTitle()+" Book lended to "+p.getName());
		}
		else{
			System.out.println("Book not available!");
		}
	}
	public static void assignBook(String title, String name) {

		Optional<Long> bookId=books.keySet().stream().filter(k->books.get(k).getTitle().equals(title)).findFirst();
		if(bookId.isEmpty()) {
			System.out.println("Book with title "+ title +" not available in library.");
			return;
		}
		Optional<Long> patronId=patrons.keySet().stream().filter(k->patrons.get(k).getName().equals(name)).findFirst();
		if(patronId.isEmpty()) {
			System.out.println(name+" is not a patron");
			return;
		}
		assignBook(patronId.get(), bookId.get());
	}
	public static void retrieveBook(String title, String name) {

		Optional<Long> bookId=books.keySet().stream().filter(k->books.get(k).getTitle().equals(title)).findFirst();
		if(bookId.isEmpty()) {
			System.out.println("Book with title "+ title +" not available in library.");
			return;
		}
		Optional<Long> patronId=patrons.keySet().stream().filter(k->patrons.get(k).getName().equals(name)).findFirst();
		if(patronId.isEmpty()) {
			System.out.println(name+" is not a patron");
			return;
		}
		retrieveBook(patronId.get(), bookId.get());
	}
	private static void retrieveBook(long patronId, long bookId) {

		Patron p=patrons.get(patronId);
		if(null==p) {
			System.out.println("No patron with id "+patronId);return;
		}
		Book b=books.get(bookId); 	
		if(null==b) {
			System.out.println("No book with id "+bookId);return;
		}
		p.getBooks().remove(b);
		b.takeBook(p);
		System.out.println(b.getTitle()+" Book retrieved from "+p.getName());

	}
	public static void removePatron(String name) {
		Optional<Long> patronId=patrons.keySet().stream().filter(k->patrons.get(k).getName().equals(name)).findFirst();
		if(patronId.isEmpty()) {
			System.out.println("No patron with given name exists.");
			return;
		}
		patrons.remove(patronId.get());
	}
	public static void removeBook(String title) {
		Optional<Long> bookId=books.keySet().stream().filter(k->books.get(k).getTitle().equals(title)).findFirst();
		if(bookId.isEmpty()) {
			System.out.println("No book with given title exists.");
			return;
		}
		books.remove(bookId.get());
	}
	public static void getAllBooks() {
		books.entrySet().stream().map(e->e.getValue().getTitle()).forEach(System.out::println);
	}
	public static void getAllPatrons() {
		patrons.entrySet().stream().map(e->e.getValue().getName()).forEach(System.out::println);
	}
	public static void getAllBooksLent() {
		books.entrySet().stream().map(e->e.getValue()).forEach(Book::printBorrowers);
	}
	public static void getAllPatronsForBook(String title) {
		Optional<Book> bookOp=books.entrySet().stream().filter(e->e.getValue().getTitle().equals(title)).map(e->e.getValue()).findFirst();
		if(bookOp.isEmpty()) {
			System.out.println("No book with given title exists.");
			return;
		}
		bookOp.get().getBorrowers();
	}
	public static void getAllBooksForPatron(String name) {
		Optional<Patron> patronOp=patrons.entrySet().stream().filter(e->e.getValue().getName().equals(name)).map(e->e.getValue()).findFirst();
		if(patronOp.isEmpty()) {
			System.out.println("No patron with given name exists.");
			return;
		}
		patronOp.get().getBooksBorrowed();
	}
	public static void parseFile() throws IOException {
		Path path=Paths.get("resources/books.txt");
		List<String> lines=Files.readAllLines(path);
		lines.stream().forEach(
				s->{
					try {
						String[] a=s.split(":");
						addBook(a[0], a[1], Long.parseLong(a[2]), Integer.parseInt(a[3]), Integer.parseInt(a[4]));
					} catch (NumberFormatException e) {
						e.printStackTrace();
						System.out.println("error in parsing line :"+s);
					}
					catch (Exception e) {
						e.printStackTrace();
						System.out.println("error in parsing line :"+s);
					}
				});
		System.out.println("Books parsed");
		lines=Files.readAllLines(Paths.get("resources/patrons.txt"));
		try {
			lines.stream().forEach(s->{
				String[] a=s.split(":");
				addPatron(a[0],a[1],a[2]);
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Patrons parsed");
	}
}
