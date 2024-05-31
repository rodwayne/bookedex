package com.alura.bookedex.main;

import java.util.Comparator;
import java.util.Optional;
import java.util.Scanner;

import com.alura.bookedex.model.BookData;
import com.alura.bookedex.model.ResponseData;
import com.alura.bookedex.service.Api;
import com.alura.bookedex.service.ConvertResponse;

public class Main {

    private static final String BASE_URL = "https://gutendex.com/books/";
    private Api api = new Api();
    private ConvertResponse convertResponse = new ConvertResponse();
    private Scanner input = new Scanner(System.in);

    public void menu() {
        var json = api.fetch(BASE_URL);
        // System.out.println(json);
        var response = convertResponse.fetch(json, ResponseData.class);
        // System.out.println(response);

        // Top 10 most downloaded titles
        System.out.println("Top 10 books");
        response.booksResults().stream()
        .sorted(Comparator.comparing(BookData::downloads).reversed())
        .limit(10)
        .map(book -> book.title().toUpperCase())
        .forEach(System.out::println);

        // Search book by name
        System.out.println("Enter a book to search: ");
        var bookName = input.nextLine();
        var jsonBookName = api.fetch(BASE_URL + "?search=" + bookName.replace(" ", "+"));
        var responseBookName = convertResponse.fetch(jsonBookName, ResponseData.class);
        Optional<BookData> bookNameOptional = responseBookName.booksResults().stream()
        .filter(book -> book.title().toUpperCase().contains(bookName.toUpperCase()))
        .findFirst();

        if (bookNameOptional.isPresent()) {
            System.out.println("Book found");
            System.out.println(bookNameOptional.get());
        } else {
            System.out.println(bookName + " not found");
        }
    }
}
