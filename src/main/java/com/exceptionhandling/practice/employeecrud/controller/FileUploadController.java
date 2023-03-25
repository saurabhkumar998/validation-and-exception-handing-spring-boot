package com.exceptionhandling.practice.employeecrud.controller;

import com.exceptionhandling.practice.employeecrud.dto.FileUploadResponseDto;
import com.exceptionhandling.practice.employeecrud.entity.Document;
import com.exceptionhandling.practice.employeecrud.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/api/fileUpload/")
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;
    @Value("${file.upload-dir}")
    String FILE_DIRECTORY;

    @PostMapping("/uploadFileToDisk")
    public ResponseEntity<String> fileUploadToDisk(@RequestParam("File") MultipartFile file) throws IOException {
        File myFile = new File(FILE_DIRECTORY + file.getOriginalFilename());
        myFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(myFile);
        fos.write(file.getBytes());
        fos.close();

        return new ResponseEntity<>("The file was uploaded successfully.", HttpStatus.OK);
    }


    @PostMapping("/uploadFileToDb")
    public FileUploadResponseDto fileUploadToDb(@RequestParam("File") MultipartFile file) throws Exception {
        Document document = null;
        String downloadUrl = "";

        document = fileUploadService.saveDocument(file);

        downloadUrl = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/fileUpload/download/")
                .path(document.getDocumentId())
                .toUriString();

        return new FileUploadResponseDto(document.getDocumentName(), downloadUrl, file.getContentType(), file.getSize());
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("fileId") String fileId) throws Exception {
        Document document = null;

        document = fileUploadService.getDocument(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(document.getDocumentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "document; filename=\"" + document.getDocumentName()
                +"\"")
                .body(new ByteArrayResource(document.getData()));
    }
}
