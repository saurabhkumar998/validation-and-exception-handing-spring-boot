package com.exceptionhandling.practice.employeecrud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadResponseDto {

    private String fileName;
    private String downloadUrl;
    private String fileType;
    private Long fileSize;
}
