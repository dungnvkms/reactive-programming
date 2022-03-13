package com.example.reactiveprogramming.demo.helper;

import java.util.Arrays;
import java.util.List;

public class EmployeeUtils {

    public static List<Employee> initEmployees() {
        return Arrays.asList(
            Employee.builder().name("A Nguyen").age(5).job("dev").build(),
            Employee.builder().name("B Nguyen").age(10).job("dev").build(),
            Employee.builder().name("C Nguyen").age(15).job("dev").build(),
            Employee.builder().name("D Nguyen").age(20).job("tester").build(),
            Employee.builder().name("E Nguyen").age(25).job("dev").build(),
            Employee.builder().name("F Nguyen").age(25).job("tester").build(),
            Employee.builder().name("G Nguyen").age(35).job("dev").build(),
            Employee.builder().name("H Nguyen").age(40).job("dev").build(),
            Employee.builder().name("I Nguyen").age(45).job("dev").build(),
            Employee.builder().name("K Nguyen").age(50).job("designer").build()
        );

    }

    public static <T> void printEmployees(List<T> items) {
        items.forEach(item -> System.out.println(item.toString()));
    }
}
