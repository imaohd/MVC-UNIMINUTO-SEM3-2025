package com.imaohd.view;

import com.imaohd.model.Employee;
import com.imaohd.model.Rol;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeViewImpTest {

    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;

    private ByteArrayInputStream testIn;
    private ByteArrayOutputStream testOut;

    private void provideInput(String data) {
        testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    private void captureOutput() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    private String getOutput() {
        return testOut.toString();
    }

    @BeforeEach
    public void setUp() {
        captureOutput();
    }

    @AfterEach
    public void restoreSystemIO() {
        System.setIn(systemIn);
        System.setOut(systemOut);
    }

    @Test
    public void testCreateEmployee_validInput() {
        // Simula: nombre = Juan, rol = CAJERO
        provideInput("\nJuan\nCAJERO\n");

        EmployeeViewImp view = new EmployeeViewImp();
        Employee employee = view.createEmployee();

        assertNotNull(employee);
        assertEquals("Juan", employee.getName());
        assertEquals(Rol.CAJERO, employee.getRol());
    }

    @Test
    public void testCreateEmployee_invalidRol() {
        // Simula: nombre = Ana, rol = INVALIDO
        provideInput("\nAna\nINVALIDO\n");

        EmployeeViewImp view = new EmployeeViewImp();
        Employee employee = view.createEmployee();

        assertNull(employee);
        assertTrue(getOutput().contains("Cargo inválido"));
    }

    @Test
    public void testConfirmDelete_yesInput() {
        provideInput("\nS\n");

        EmployeeViewImp view = new EmployeeViewImp();
        boolean confirmed = view.confirmDelete();

        assertTrue(confirmed);
    }

    @Test
    public void testConfirmDelete_noInput() {
        provideInput("\nN\n");

        EmployeeViewImp view = new EmployeeViewImp();
        boolean confirmed = view.confirmDelete();

        assertFalse(confirmed);
    }
}