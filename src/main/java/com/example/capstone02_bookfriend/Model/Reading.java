package com.example.capstone02_bookfriend.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Reading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "state cannot be empty")
    @Pattern(regexp = "^(done|start|reviewed)$",message = "state should be (done | still | reviewed)")
    @Column(columnDefinition = "varchar(8) not null")
    @Check(constraints = "state='done' or state='still' or state='reviewed'")
    private String state; // done | still | interviewed
    @NotNull(message = "book id cannot be null")
    @Column(columnDefinition = "int not null")
    private Integer book_id;
    @NotNull(message = "user id cannot be null")
    @Column(columnDefinition = "int not null")
    private Integer user_id;
    @Column(columnDefinition = "date")
    private LocalDate readingStart;
    @Column(columnDefinition = "date")
    private LocalDate readingFinish;

}
