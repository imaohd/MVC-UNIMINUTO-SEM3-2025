package com.imaohd.controller;

import com.imaohd.model.Employee;
import com.imaohd.model.EmployeeDAO;
import com.imaohd.view.EmployeeViewImp;

import java.util.List;

public class EmployeeController {

    private final EmployeeDAO employeeDAO;
    private final EmployeeViewImp employeeView;

    public EmployeeController(EmployeeDAO employeeDAO, EmployeeViewImp employeeView) {
        this.employeeDAO = employeeDAO;
        this.employeeView = employeeView;
    }

    public void run() {
        boolean exit = false;

        while (!exit) {
            int option = employeeView.showMenu();

            switch (option) {
                case 1:
                    showEmployeesByRol();
                    break;
                case 2:
                    createEmployee();
                    break;
                case 3:
                    showEmployeeByID();
                    break;
                case 4:
                    showAllEmployees();
                    break;
                case 5:
                    updateEmployee();
                    break;
                case 6:
                    updateEmployeeByRol();
                    break;
                case 7:
                    deleteEmployee();
                    break;
                case 0:
                    exit = true;
                    employeeView.showMessage("¡Gracias por usar el Sistema de Gestión de Empleados!");
                    employeeView.close();
                    break;
                default:
                    employeeView.showMessage("Opción no válida. Intente de nuevo.");
            }
        }
    }

    public void deleteEmployee() {
        int employeeId = employeeView.getEmployeeById("eliminar");
        if (employeeId < 0) {
            return;
        }
        Employee employee = employeeDAO.findById(employeeId);


        if (employee != null) {
            employeeView.showEmployee(employee);

            if (employeeView.confirmDelete()) {
                if(employeeDAO.delete(employeeId)) {
                    employeeView.showMessage("Empleado eliminado con éxito:");
                }
            }
        }

    }

    public void updateEmployeeByRol() {
        int employeeId = employeeView.getEmployeeById("actualizar");
        if (employeeId < 0) return;

        Employee employee = employeeDAO.findById(employeeId);
        if (employee == null) {
            employeeView.showMessage("Empleado no encontrado.");
            return;
        }

        employeeView.showEmployee(employee);

        String newRol = employeeView.showEmployeesByRol();
        if (newRol.isEmpty()) return;

        boolean updated = employeeDAO.updateRol(employeeId, newRol);
        if (!updated) {
            employeeView.showMessage("Error al actualizar el rol del empleado.");
            return;
        }

        Employee updatedEmployee = employeeDAO.findById(employeeId);
        employeeView.showMessage("Empleado actualizado con éxito:");
        employeeView.showEmployee(updatedEmployee);
    }

    public void updateEmployee() {
        int id = employeeView.getEmployeeById("actualizar");
        if (id < 0) {
            return;
        }
        Employee employee = employeeDAO.findById(id);
        if (employee != null) {
            Employee updatedEmployee = employeeView.updateEmployee(employee);
            if (updatedEmployee != null) {
                boolean result = employeeDAO.update(updatedEmployee);
                if (result) {
                    employeeView.showMessage("Empleado actualizado con éxito:");
                    employeeView.showEmployee(updatedEmployee);
                } else {
                    employeeView.showMessage("Error al actualizar el empleado.");
                }
            } else {
                employeeView.showMessage("Error al actualizar el empleado.");
            }
        } else {
            employeeView.showMessage("Empleado no encontrado.");
        }

    }

    public void showEmployeeByID() {
        int id = employeeView.getEmployeeById("buscar");
        if (id < 0) {
            return;
        }
        Employee employee = employeeDAO.findById(id);
        if (employee != null) {
            employeeView.showEmployee(employee);
        } else {
            employeeView.showMessage("Empleado no encontrado.");
        }
    }

    public void createEmployee() {
        Employee employee = employeeView.createEmployee();
        if (employee != null) {
            employeeDAO.create(employee);
            employeeView.showMessage("Empleado creado con éxito:");
            employeeView.showEmployee(employee);
        } else {
            employeeView.showMessage("Error al crear el empleado.");
        }
    }

    public void showAllEmployees() {
        List<Employee> employees = employeeDAO.findAll();
        if (employees.isEmpty()) {
            employeeView.showMessage("No hay empleados registrados.");
        } else {
            employeeView.showAllEmployees(employees);
        }
    }

    public void showEmployeesByRol() {
        String rol = employeeView.showEmployeesByRol();
        if (rol.isEmpty()) {
            return;
        }
        employeeView.showAllEmployees(employeeDAO.getEmployeesWithRol(rol));
    }

}
