package com.upiiz.superheroes.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.Link;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomResponse<T> {
    private int status;
    private String message;
    private T data;
    private List<Link> links;
}
