package com.example.capstone02_bookfriend.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "name cannot be empty")
    @Column(columnDefinition = "varchar(20)")
    private String name;
    @NotNull(message = "number of groups cannot be null")
    @PositiveOrZero(message = "cannot be negative")
    @Column(columnDefinition = "int not null")
    @Check(constraints = "number_of_groups>=0")
    private Integer number_of_groups=0;
    @NotNull(message = "category id cannot be null")
    @Column(columnDefinition = "int not null")
    private Integer category_id;
    @NotNull(message = "group id cannot be null")
    @Column(columnDefinition = "int not null")
    private Integer group_id;
}
