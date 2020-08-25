package com.example.amoy_interest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "blog_heat")
public class BlogHeat {
    @Id
    private int blog_Id;

    private int blog_heat;
}
