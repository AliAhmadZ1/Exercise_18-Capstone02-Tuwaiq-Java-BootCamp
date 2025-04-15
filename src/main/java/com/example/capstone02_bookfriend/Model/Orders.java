package com.example.capstone02_bookfriend.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "total price cannot be null")
    @PositiveOrZero(message = "cannot be negative")
    @Column(columnDefinition = "double not null")
    @Check(constraints = "total_price>=0.0")
    private Double total_price = 0.0;
    @NotEmpty(message = "state cannot be empty")
    @Pattern(regexp = "^(returned|completed|in progress)$",message = "state should be (returned | completed | in progress)")
    @Column(columnDefinition = "varchar(12) not null")
    @Check(constraints = "state='returned' or state='completed' or state='in progress'")
    private String state; // returned | completed | in progress
    @NotNull(message = "book id cannot be null")
    @Column(columnDefinition = "int not null")
    private Integer book_id;
    @NotNull(message = "user id cannot be null")
    @Column(columnDefinition = "int not null")
    private Integer user_id;

}
