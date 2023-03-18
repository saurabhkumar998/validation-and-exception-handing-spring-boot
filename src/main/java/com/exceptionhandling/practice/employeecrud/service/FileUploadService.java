package com.exceptionhandling.practice.employeecrud.service;

import com.exceptionhandling.practice.employeecrud.entity.Document;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {

    Document saveDocument(MultipartFile file) throws Exception;

    Document getDocument(String fileId) throws Exception;
}
