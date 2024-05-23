package com.alura.bookedex.service;

public interface IConvertResponse {
    <T> T fetch(String json, Class<T> genericClass);
}
