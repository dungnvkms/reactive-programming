package com.example.reactiveprogramming.demo.helper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee {
    private String name;
    private int age;
    private String job;
    private List<String> email;
    private boolean isOfficial;

    @Override
    public String toString() {
        return "name=" + name +
                " || age=" + age +
                " || job=" + job +
                " || email=" + email;
    }
}
