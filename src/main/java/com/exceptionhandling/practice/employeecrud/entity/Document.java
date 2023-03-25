package com.exceptionhandling.practice.employeecrud.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
@NoArgsConstructor
@Table(name = "T_EMPLOYEE_DOCUMENT")
public class Document {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid", strategy = "uuid2")
    private String documentId;
    private String documentName;
    private String documentType;
    @Lob
    private byte[] data;

    public Document(String documentName, String documentType, byte[] data) {
        this.documentName = documentName;
        this.documentType = documentType;
        this.data = data;
    }
}
