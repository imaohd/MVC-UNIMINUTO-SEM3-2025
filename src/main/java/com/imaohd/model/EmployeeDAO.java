package com.imaohd.model;

import java.util.List;

public interface EmployeeDAO {

    List<Employee> getEmployeesWithRol(String rol);

    boolean create(Employee employee);

    Employee findById(int id);

    List<Employee> findAll();

    boolean update(Employee employee);

    boolean updateRol(int id, String rol);

    boolean delete(int id);
}
