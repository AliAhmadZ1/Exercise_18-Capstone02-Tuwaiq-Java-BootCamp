package com.example.capstone02_bookfriend.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
//    @NotEmpty(message = "ISBN cannot be empty")
    @Size(min = 10,message = "ISBN should be 10 digit or 13")
    @Column(columnDefinition = "varchar(13) not null")
    private String isbn;
    @NotEmpty(message = "title cannot be empty")
    @Size(min = 4,message = "should be at least 4 characters")
    @Column(columnDefinition = "varchar(20) not null")
    private String title;
    @NotEmpty(message = "bind cannot be empty")
    @Pattern(regexp = "^(eBook|paperBack)$",message = "bind should be ('eBook' or 'paperBack')")
    @Column(columnDefinition = "varchar(9) not null")
    @Check(constraints = "bind='eBook' or bind ='paperBack'")
    private String bind;
    @NotNull(message = "price shouldn't be null")
    @PositiveOrZero(message = "price cannot be negative")
    @Column(columnDefinition = "double not null")
    @Check(constraints = "price>=0.0")
    private Double price = 0.0;
    @Column(columnDefinition = "int not null")
    private Integer stock=0;
    @Column(columnDefinition = "date not null")
    private LocalDate publish_date;
    @NotNull(message = "publisher cannot be null")
    @Column(columnDefinition = "int not null")
    private Integer publisher_id;
    @NotNull(message = "category cannot be null")
    @Column(columnDefinition = "int not null")
    private Integer category_id;

}
