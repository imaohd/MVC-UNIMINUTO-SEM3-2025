package com.imaohd.view;

import com.imaohd.model.Employee;

import java.util.List;

public interface EmployeeView {

    int showMenu();
    String showEmployeesByRol();
    Employee createEmployee();
    void showEmployee(Employee employee);
    void showAllEmployees(List<Employee> employees);
    int getEmployeeById(String action);
    Employee updateEmployee(Employee employee);
    boolean confirmDelete();
    void showMessage(String message);
    void close();
}
