package com.jgk.spring.data.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jgk.spring.data.jpa.domain.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}
