package com.example.capstone02_bookfriend.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Joins {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "state cannot be empty")
    @Pattern(regexp = "^(joined|withdrawn)$",message = "state should be ('joined' or 'withdrawn')")
    @Column(columnDefinition = "varchar(10) not null")
    @Check(constraints = "state='joined' or state='withdrawn'")
    private String state; // joined / withdrawn
    @NotNull(message = "group id cannot be null")
    @Column(columnDefinition = "int not null")
    private Integer group_id;
    @NotNull(message = "user id cannot be null")
    @Column(columnDefinition = "int not null")
    private Integer user_id;
}
