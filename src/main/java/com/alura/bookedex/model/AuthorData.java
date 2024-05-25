package com.alura.bookedex.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AuthorData(
    @JsonAlias("name") String name,
    @JsonAlias("birth_year") String birthDate
) {
}
