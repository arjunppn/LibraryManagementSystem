package com.lib.mgmt;

import java.io.IOException;
import java.util.Scanner;

public class Main {

	@SuppressWarnings("finally")
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		boolean exit=false;
		String menu="""
					Library Management Service
				1. Add patron
				2. Add book
				3. Lend book
				4. retrieve book
				5. remove patron
				6. remove book
				7. get all books
				8. get all patrons
				9. get books given to user
				10.get all users a book is given to
				11.get all borrowers
				12.read all patrons and books from file
				13.exit
						""";
		while(!exit) {
			System.out.println(menu);
			int n;
			try {
			n=sc.nextInt();sc.nextLine();
			}
			catch(Exception e) {
				e.printStackTrace();
				sc.nextLine();
				continue;
				}
			switch (n) {
			case 1: {
				String s="""
						Enter Patron details:

						Name, Address and Adhar Id
						in order.
						""";
				System.out.println(s);
				String name=sc.nextLine();
				String address=sc.nextLine();
				String adhar=sc.nextLine();
				InventoryService.addPatron(name, address, adhar);
				System.out.println("Patron added successfully");
				break;
			}
			case 2: {
				String s="""
						Enter Book details:

						Title, Author, published year,
						ISBN and quantity
						in order.
						""";
				System.out.println(s);
				String title=sc.nextLine();
				String author=sc.nextLine();
				int pubYear=sc.nextInt();sc.nextLine();
				long iSBN=sc.nextLong();sc.nextLine();
				int qty=sc.nextInt();sc.nextLine();
				InventoryService.addBook(title, author,iSBN, pubYear, qty);
				System.out.println("Book added successfully");
				break;
			}
			case 3:{
				String s="""
						Enter Book title and patron name:
						""";
				System.out.println(s);
				String title=sc.nextLine();
				String name=sc.nextLine();
				InventoryService.assignBook(title, name);
				break;
			}
			case 4:{
				String s="""
						Enter Book title and patron name:
						""";
				System.out.println(s);
				String title=sc.nextLine();
				String name=sc.nextLine();
				InventoryService.retrieveBook(title, name);
				break;
			}
			case 5: {
				String s="""
						Enter patron name
						""";
				System.out.println(s);
				String name=sc.nextLine();
				InventoryService.removePatron(name);
				System.out.println("Patron removed successfully");
				break;
			}
			case 6: {
				String s="""
						Enter book title
						""";
				System.out.println(s);
				String title=sc.nextLine();
				InventoryService.removeBook(title);
				System.out.println("Book removed successfully");
				break;
			}
			case 7:{
				InventoryService.getAllBooks();
				break;
			}
			case 8:{
				InventoryService.getAllPatrons();
				break;
			}
			case 9:{
				System.out.println("Enter patron name:");
				String name=sc.nextLine();
				InventoryService.getAllBooksForPatron(name);
				break;
			}
			case 10:{
				System.out.println("Enter book title:");
				String title=sc.nextLine();
				InventoryService.getAllPatronsForBook(title);
			}
			case 11:{
				InventoryService.getAllBooksLent();
			}
			case 12:{
				try {
					InventoryService.parseFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("File path not valid");
				}
				finally { break;}
				
			}
			case 13:{
				exit=true;
				break;
			}
			default:
				throw new IllegalArgumentException("Unexpected value: " + n);
			}
		}
		sc.close();
	}
}
