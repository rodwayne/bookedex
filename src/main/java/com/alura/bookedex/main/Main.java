package com.alura.bookedex.main;

import java.util.Comparator;

import com.alura.bookedex.model.BookData;
import com.alura.bookedex.model.ResponseData;
import com.alura.bookedex.service.Api;
import com.alura.bookedex.service.ConvertResponse;

public class Main {

    private static final String BASE_URL = "https://gutendex.com/books/";
    private Api api = new Api();
    private ConvertResponse convertResponse = new ConvertResponse();

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
    }
}
