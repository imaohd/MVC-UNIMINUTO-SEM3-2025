package com.imaohd.view;

import com.imaohd.model.Employee;
import com.imaohd.model.Rol;

import java.util.List;
import java.util.Scanner;

public class EmployeeViewImp implements EmployeeView {


    private final Scanner scanner;
    private final String[] menuOptions = {
            "Consultar empleados por cargo",
            "Crear empleado",
            "Buscar empleado por ID",
            "Ver todos los empleados",
            "Actualizar empleado",
            "Actualizar cargo de empleado",
            "Eliminar empleado"
    };

    public EmployeeViewImp() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public int showMenu() {
        int opcion = -1;

        while (true) {
            System.out.println("\n--- Menú de Empleados ---");
            for (int i = 0; i < menuOptions.length; i++) {
                System.out.println((i + 1) + ". " + menuOptions[i]);
            }
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                if (opcion >= 0 && opcion <= menuOptions.length) {
                    return opcion;
                } else {
                    System.out.println("Opción fuera de rango. Intente de nuevo.");
                }
            } else {
                System.out.println("Entrada inválida. Por favor, ingrese un número del 0 al " + menuOptions.length);
                scanner.next(); // Limpiar el buffer
            }
        }
    }

    @Override
    public String showEmployeesByRol() {
        scanner.nextLine(); // Consumir el salto de línea pendiente
        System.out.println("\nIntroduzca el cargo (" + Rol.cargosActivos() + ") del empleado: ");
        System.out.print("Cargo: ");
        String rol = scanner.nextLine().trim();
        if (Rol.isValidRol(rol.toUpperCase())) {
            return Rol.valueOf(rol.toUpperCase()).name();
        } else {
            System.out.println("Cargo inválido");
            return "";
        }
    }

    @Override
    public Employee createEmployee() {
        scanner.nextLine();

        System.out.println("\n--- Crear Empleado ---");

        System.out.print("Nombre: ");
        String name = scanner.nextLine().trim();

        System.out.print("Cargo (" + Rol.cargosActivos() + "): ");
        String rol = scanner.nextLine().trim();
        if (Rol.isValidRol(rol.toUpperCase())) {
            return new Employee(name, Rol.valueOf(rol.toUpperCase()));
        } else {
            System.out.println("Cargo inválido");
            return null;
        }
    }

    @Override
    public void showEmployee(Employee employee) {
        if (employee == null) {
            System.out.println("Empleado no encontrado.");
            return;
        }
        System.out.println("\n--- Detalles del Empleado ---");
        System.out.printf("ID: %d, Nombre: %s, Cargo: %s\n", employee.getId(), employee.getName(), employee.getRol());
    }

    @Override
    public void showAllEmployees(List<Employee> employees) {
        System.out.println("\n--- Lista de Empleados ---");
        if (employees.isEmpty()) {
            System.out.println("No hay empleados registrados.");
            return;
        }
        System.out.printf("%-5s %-15s %-15s\n", "ID", "NOMBRE", "CARGO");
        System.out.println("----------------------------------------------");
        for (Employee employee : employees) {
            System.out.printf("%-5d %-15s %-15s\n",
                    employee.getId(),
                    employee.getName(),
                    employee.getRol());
        }

    }

    @Override
    public int getEmployeeById(String action) {
        System.out.println("\nIntroduzca el ID del usuario para " + action + ": ");
        System.out.print("ID: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Entrada inválida. Por favor, ingrese un número.");
            scanner.next(); // Limpiar el buffer
            System.out.print("ID: ");
        }
        return scanner.nextInt();
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        scanner.nextLine();

        System.out.println("\n--- Actualizar Empleado ---");
        System.out.println("Datos actuales:");
        System.out.printf("ID: %d, Nombre: %s, Cargo: %s\n", employee.getId(), employee.getName(), employee.getRol());
        System.out.print("Nombre [" + employee.getName() + "]: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            name = employee.getName();
        }

        System.out.print("Cargo[" + employee.getRol() +"] cargos disponibles (" + Rol.cargosActivos() + "): ");
        String rol = scanner.nextLine().trim();
        if (rol.isEmpty()) {
            rol = employee.getRol().name();
        }
        if (Rol.isValidRol(rol.toUpperCase())) {
            employee.setName(name);
            employee.setRol(Rol.valueOf(rol.toUpperCase()));
            System.out.println("Empleado actualizado con éxito.");
        } else {
            System.out.println("Cargo inválido");
        }
        return employee;
    }

    @Override
    public boolean confirmDelete() {
        System.out.print("¿Está seguro de que desea eliminar este usuario? (S/N): ");
        scanner.nextLine(); // Consumir el salto de línea pendiente
        String response = scanner.nextLine().toUpperCase();
        return response.equals("S");
    }

    @Override
    public void showMessage(String message) {
        System.out.println("\n" + message);
    }

    @Override
    public void close() {
        scanner.close();
    }
}
