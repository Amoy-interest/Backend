package com.example.demo.Entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "sensitive_words")
public class SensitiveWord {
    @Id
    private String keyword;
}
