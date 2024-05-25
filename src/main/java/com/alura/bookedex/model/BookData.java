package com.alura.bookedex.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookData(
    @JsonAlias("title") String title,
    @JsonAlias("authors") List<AuthorData> author,
    @JsonAlias("languages") List<String> availableLanguages,
    @JsonAlias("download_count") Double downloads
){
}
