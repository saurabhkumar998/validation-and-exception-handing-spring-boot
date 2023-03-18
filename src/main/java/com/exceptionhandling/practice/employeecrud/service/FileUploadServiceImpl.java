package com.exceptionhandling.practice.employeecrud.service;

import com.exceptionhandling.practice.employeecrud.entity.Document;
import com.exceptionhandling.practice.employeecrud.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Autowired
    private DocumentRepository documentRepository;
    @Override
    public Document saveDocument(MultipartFile file) throws Exception {
        String fileName = file.getOriginalFilename();

        try {
            if(fileName.contains("..")) {
                throw new Exception("Filename contains invalid path sequence " + fileName);
            }

            Document document = new Document(fileName, file.getContentType(), file.getBytes());
            return documentRepository.save(document);

        } catch (Exception e) {
            throw new Exception("Could not save File: " + fileName);
        }
    }

    @Override
    public Document getDocument(String fileId) throws Exception {
        return documentRepository.findById(fileId)
                .orElseThrow(() -> new Exception("File not found with Id: " + fileId));
    }
}
