package com.exceptionhandling.practice.employeecrud.repository;

import com.exceptionhandling.practice.employeecrud.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<Document, String> {
}
