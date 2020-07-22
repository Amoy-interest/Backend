package com.example.amoy_interest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@NoArgsConstructor
@Table(name = "sensitive_words")
public class SensitiveWord {
    @Id
    private String keyword;

    public SensitiveWord() {}
    public SensitiveWord(String keyword) {
        this.keyword = keyword;
    }
}
