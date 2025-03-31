package com.imaohd;

import com.imaohd.controller.EmployeeController;
import com.imaohd.model.EmployeeDAOImp;
import com.imaohd.util.DatabaseConnection;
import com.imaohd.view.EmployeeViewImp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.assertj.core.api.Assertions.assertThat;

public class MainTest {

    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;

    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setup() throws SQLException {
        // Resetear la base de datos en memoria
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS employees;");
            stmt.execute("CREATE TABLE employees (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT NOT NULL," +
                    "rol TEXT NOT NULL);");
        }

        // Captura salida por consola
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    @Test
    void testMain_createAndListEmployee() {
        // Simular: opción 2 (crear), nombre=Juan, rol=CAJERO, luego opción 4 (listar), luego 0 (salir)
        String simulatedInput = String.join("\n",
                "2",        // Crear
                "Juan",     // Nombre
                "CAJERO",   // Rol
                "4",        // Ver todos
                "0"         // Salir
        );
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        EmployeeDAOImp dao = new EmployeeDAOImp();
        EmployeeViewImp view = new EmployeeViewImp();
        EmployeeController controller = new EmployeeController(dao, view);
        controller.run();

        String output = outContent.toString();
        assertThat(output).contains("Empleado creado con éxito");
        assertThat(output).contains("Juan");
        assertThat(output).contains("CAJERO");
    }
}