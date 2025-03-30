package com.imaohd;

import com.imaohd.controller.UserController;
import com.imaohd.model.EmployeeDAO;
import com.imaohd.model.EmployeeDAOImp;
import com.imaohd.view.EmployeeViewImp;

public class Main {
    public static void main(String[] args) {
        EmployeeDAO employeeDAO = new EmployeeDAOImp();
        EmployeeViewImp employeeView = new EmployeeViewImp();

        UserController userController = new UserController(employeeDAO, employeeView);
        //employeeDAO.create(new Employee("Mauro", Rol.CAJERO));
        //employeeDAO.getEmployeesWithRol(Rol.CAJERO.name()).forEach(System.out::println);

        //System.out.println(employeeDAO.findById(0));
        //employeeDAO.findAll().forEach(System.out::println);

        //employeeDAO.update(new Employee(1, "Mauro", Rol.GERENTE));
        //employeeDAO.findAll().forEach(System.out::println);

//        employeeDAO.findAll().forEach(System.out::println);
//        employeeDAO.updateRol(2, Rol.SUPERVISOR.name());
//        employeeDAO.findAll().forEach(System.out::println);

        // Iniciar la aplicación
        userController.run();
    }
}