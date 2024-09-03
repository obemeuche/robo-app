package com.robobob.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
public class FAQEntity {
    @Id
    private Long id;
    private String question;
    private String answer;
}
