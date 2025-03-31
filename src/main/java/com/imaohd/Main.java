package com.imaohd;

import com.imaohd.controller.EmployeeController;
import com.imaohd.model.EmployeeDAO;
import com.imaohd.model.EmployeeDAOImp;
import com.imaohd.view.EmployeeViewImp;

public class Main {
    public static void main(String[] args) {
        EmployeeDAO employeeDAO = new EmployeeDAOImp();
        EmployeeViewImp employeeView = new EmployeeViewImp();

        EmployeeController employeeController = new EmployeeController(employeeDAO, employeeView);


        // Iniciar la aplicación
        employeeController.run();
    }
}