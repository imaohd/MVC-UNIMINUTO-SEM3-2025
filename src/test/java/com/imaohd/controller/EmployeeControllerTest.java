package com.imaohd.controller;

import com.imaohd.model.Employee;
import com.imaohd.model.EmployeeDAO;
import com.imaohd.model.Rol;
import com.imaohd.view.EmployeeViewImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

public class EmployeeControllerTest {

    private EmployeeDAO employeeDAO;
    private EmployeeViewImp employeeView;
    private EmployeeController employeeController;

    @BeforeEach
    public void setUp() {
        employeeDAO = mock(EmployeeDAO.class);
        employeeView = mock(EmployeeViewImp.class);
        employeeController = new EmployeeController(employeeDAO, employeeView);
    }

    @Test
    public void testCreateEmployee_success() {
        Employee employee = new Employee(1, "Juan", Rol.CAJERO);

        when(employeeView.createEmployee()).thenReturn(employee);

        employeeController.createEmployee();

        verify(employeeDAO).create(employee);
        verify(employeeView).showMessage("Empleado creado con éxito:");
        verify(employeeView).showEmployee(employee);
    }

    @Test
    public void testDeleteEmployee_success() {
        Employee employee = new Employee(2, "Ana", Rol.GERENTE);

        when(employeeView.getEmployeeById("eliminar")).thenReturn(2);
        when(employeeDAO.findById(2)).thenReturn(employee);
        when(employeeView.confirmDelete()).thenReturn(true);
        when(employeeDAO.delete(2)).thenReturn(true);

        employeeController.deleteEmployee();

        verify(employeeView).showEmployee(employee);
        verify(employeeDAO).delete(2);
        verify(employeeView).showMessage("Empleado eliminado con éxito:");
    }

    @Test
    public void testShowAllEmployees_withEmployees() {
        List<Employee> employees = List.of(new Employee(3, "Carlos", Rol.SUPERVISOR));
        when(employeeDAO.findAll()).thenReturn(employees);

        employeeController.showAllEmployees();

        verify(employeeView).showAllEmployees(employees);
    }

    @Test
    public void testShowEmployeeById_notFound() {
        when(employeeView.getEmployeeById("buscar")).thenReturn(999);
        when(employeeDAO.findById(999)).thenReturn(null);

        employeeController.showEmployeeByID();

        verify(employeeView).showMessage("Empleado no encontrado.");
    }

    @Test
    public void testUpdateEmployeeByRol_success() {
        Employee originalEmployee = new Employee(4, "María", Rol.CAJERO);
        String newRolString = Rol.SUPERVISOR.name();

        when(employeeView.getEmployeeById("actualizar")).thenReturn(4);
        when(employeeDAO.findById(4)).thenReturn(originalEmployee);
        when(employeeView.showEmployeesByRol()).thenReturn(newRolString);
        when(employeeDAO.updateRol(4, newRolString)).thenReturn(true);
        when(employeeDAO.findById(4)).thenReturn(new Employee(4, "María", Rol.SUPERVISOR));

        employeeController.updateEmployeeByRol();

        verify(employeeView).showMessage("Empleado actualizado con éxito:");
        verify(employeeView, times(2)).showEmployee(any(Employee.class));
    }

    @Test
    public void testShowEmployeesByRol_withResults() {
        String rolString = Rol.GERENTE.name();
        List<Employee> employees = List.of(new Employee(5, "Laura", Rol.GERENTE));

        when(employeeView.showEmployeesByRol()).thenReturn(rolString);
        when(employeeDAO.getEmployeesWithRol(rolString)).thenReturn(employees);

        employeeController.showEmployeesByRol();

        verify(employeeView).showAllEmployees(employees);
    }
}