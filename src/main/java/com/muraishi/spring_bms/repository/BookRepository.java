package com.muraishi.spring_bms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.muraishi.spring_bms.entity.Book;



@Repository
public interface BookRepository extends JpaRepository<Book, String>{

}
