package com.alura.bookedex.main;

import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

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
            System.out.println("\nBook found");
            System.out.println(bookNameOptional.get());
        } else {
            System.out.println(bookName + " not found");
        }

        // Statistics
        DoubleSummaryStatistics est = response.booksResults().stream()
            .filter(book -> book.downloads() > 0)
            .collect(Collectors.summarizingDouble(BookData::downloads));
        System.out.println("\nAverage downloads: " + est.getAverage());
        System.out.println("Most downloads: " + est.getMax());
        System.out.println("Least downloads: " + est.getMin());
        System.out.println("Book data set size: " + est.getCount());

        // TODO: Search books by realeaseDate
        System.out.println("Search books by release year: ");
        var year = input.nextLine();
        var jsonYear = api.fetch(BASE_URL + "?author_year_start=" + year + "&author_year_end=" + year);
        var responseYear = convertResponse.fetch(jsonYear, ResponseData.class);
        System.out.println("Books dating from " + year);
        responseYear.booksResults().stream()
            .map(book -> book.title())
            .forEach(System.out::println);
    }
}
