package com.example.reactiveprogramming.demo;

import com.example.reactiveprogramming.demo.helper.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import static com.example.reactiveprogramming.demo.helper.EmployeeUtils.initEmployees;

public class Demo0FunctionalStyle {
    public static List<Employee> getKmsDevelopers(List<Employee> employees) {
        List<Employee> result = new ArrayList<>();
        for (Employee employee: employees) {
            if ("dev".equals(employee.getJob())) {
                result.add(employee);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        List<Employee> devEmployees = initEmployees()
                .stream()
                .filter(employee -> "dev".equals(employee.getJob()))
                .collect(Collectors.toList());


        new Runnable() {
            @Override
            public void run() {

            }
        };

        new Callable<String>() {
            @Override
            public String call() throws Exception {
                return null;
            }
        };

    }
}
