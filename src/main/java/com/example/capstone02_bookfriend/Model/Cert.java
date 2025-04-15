package com.example.capstone02_bookfriend.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "book id cannot be null")
    @Column(columnDefinition = "int not null")
    private Integer book_id;
    @NotNull(message = "user id cannot be null")
    @Column(columnDefinition = "int not null")
    private Integer user_id;
    @Column(columnDefinition = "int")
    private Integer amount=0;
}
