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
@NoArgsConstructor
@Entity
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "type cannot be empty")
    @Pattern(regexp = "^(normal|vip)$",message = "type should be ('normal' or 'vip')")
    @Column(columnDefinition = "varchar(6) not null")
    @Check(constraints = "type='normal' or type='vip'")
    private String type; // normal | vip
    @NotNull(message = "user id cannot be null")
    @Column(columnDefinition = "int not null")
    private Integer user_id;
}
