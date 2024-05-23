package com.alura.bookedex;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alura.bookedex.main.Main;

@SpringBootApplication
public class BookedexApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BookedexApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Main main = new Main();
		main.menu();
	}

}
