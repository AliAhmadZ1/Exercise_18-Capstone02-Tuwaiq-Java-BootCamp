package com.example.capstone02_bookfriend.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//import org.hibernate.annotations.Check;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Groups {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "name cannot be empty")
    @Column(columnDefinition = "varchar(15)")
    private String name;
    @NotNull(message = "number of users cannot be null")
    @PositiveOrZero(message = "cannot be negative")
    @Column(columnDefinition = "int not null")
//    @Check(constraints = "number_of_users>=0")
    private Integer number_of_users = 0;
    @NotNull(message = "max capacity cannot be null")
    @PositiveOrZero(message = "cannot be negative")
    @Column(columnDefinition = "int not null")
//    @Check(constraints = "max_capacity>=0")
    private Integer max_capacity = 0;
    @NotNull(message = "book id cannot be null")
    @Column(columnDefinition = "int not null")
    private Integer book_id;
    @NotNull(message = "user id cannot be null")
    @Column(columnDefinition = "int not null")
    private Integer user_id;
}
