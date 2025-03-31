package com.imaohd.model;

import com.imaohd.util.DatabaseConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class EmployeeDAOImpTest {

    private EmployeeDAOImp dao;

    @BeforeEach
    void setUp() throws SQLException {
        dao = new EmployeeDAOImp();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute("DROP TABLE IF EXISTS employees;");
            stmt.execute("CREATE TABLE employees (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT NOT NULL," +
                    "rol TEXT NOT NULL);");
        }
    }

    @Test
    void testCreate_success() {
        Employee employee = new Employee("Juan", Rol.CAJERO);
        boolean created = dao.create(employee);

        assertThat(created).isTrue();
        assertThat(employee.getId()).isGreaterThan(0);
    }

    @Test
    void testFindById_exists() {
        Employee employee = new Employee("Ana", Rol.SUPERVISOR);
        dao.create(employee);

        Employee found = dao.findById(employee.getId());

        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("Ana");
        assertThat(found.getRol()).isEqualTo(Rol.SUPERVISOR);
    }

    @Test
    void testFindById_notFound() {
        Employee found = dao.findById(999);
        assertThat(found).isNull();
    }

    @Test
    void testFindAll_emptyInitially() {
        List<Employee> employees = dao.findAll();
        assertThat(employees).isEmpty();
    }

    @Test
    void testFindAll_withData() {
        dao.create(new Employee("Pedro", Rol.GERENTE));
        dao.create(new Employee("Laura", Rol.SUPERVISOR));

        List<Employee> employees = dao.findAll();
        assertThat(employees).hasSize(2);
    }

    @Test
    void testUpdate_success() {
        Employee employee = new Employee("Carlos", Rol.CAJERO);
        dao.create(employee);

        employee.setName("Carlos A.");
        employee.setRol(Rol.GERENTE);

        boolean updated = dao.update(employee);
        assertThat(updated).isTrue();

        Employee updatedEmployee = dao.findById(employee.getId());
        assertThat(updatedEmployee.getName()).isEqualTo("Carlos A.");
        assertThat(updatedEmployee.getRol()).isEqualTo(Rol.GERENTE);
    }

    @Test
    void testUpdateRol_success() {
        Employee employee = new Employee("María", Rol.CAJERO);
        dao.create(employee);

        boolean updated = dao.updateRol(employee.getId(), Rol.SUPERVISOR.name());
        assertThat(updated).isTrue();

        Employee updatedEmployee = dao.findById(employee.getId());
        assertThat(updatedEmployee.getRol()).isEqualTo(Rol.SUPERVISOR);
    }

    @Test
    void testDelete_success() {
        Employee employee = new Employee("Sebastián", Rol.GERENTE);
        dao.create(employee);

        boolean deleted = dao.delete(employee.getId());
        assertThat(deleted).isTrue();

        assertThat(dao.findById(employee.getId())).isNull();
    }

    @Test
    void testGetEmployeesWithRol() {
        dao.create(new Employee("Luis", Rol.CAJERO));
        dao.create(new Employee("Ana", Rol.CAJERO));
        dao.create(new Employee("Patricia", Rol.GERENTE));

        List<Employee> cajeros = dao.getEmployeesWithRol(Rol.CAJERO.name());

        assertThat(cajeros).hasSize(2);
        assertThat(cajeros).allMatch(e -> e.getRol() == Rol.CAJERO);
    }

    @Test
    void testGetEmployeesWithRol_invalid() {
        List<Employee> unknown = dao.getEmployeesWithRol("INVALIDO");
        assertThat(unknown).isEmpty();
    }
}