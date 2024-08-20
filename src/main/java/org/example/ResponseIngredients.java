package org.example;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class ResponseIngredients {
    private boolean success;
    private List<Ingredient> data;
    private String message;
}
