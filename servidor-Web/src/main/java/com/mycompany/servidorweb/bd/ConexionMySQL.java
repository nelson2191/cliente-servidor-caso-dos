/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.servidorweb.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author nelsonrivas
 */
public class ConexionMySQL {
     private static final String URL = "jdbc:mysql://localhost:3306/mi_base";
    
    // Usuario y contraseña de acceso a la base de datos MySQL.
    private static final String USER = "root"; 
    private static final String PASSWORD = "mi-contraseña"; 

    // Método para establecer una conexión con la base de datos.
    public Connection conectar() {
        Connection conexion = null;
        try {
            // Intenta establecer una conexión utilizando la URL, usuario y contraseña.
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión exitosa a MySQL");
        } catch (SQLException e) {
            // Captura y muestra un mensaje de error si ocurre una excepción de SQL.
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }
        // Retorna la conexión establecida (o null si falló).
        return conexion;
    }

    // Método para crear una tabla llamada "Inventario" en la base de datos, si no existe.
    public void crearTablaInventario() {
        // Consulta SQL para crear la tabla "Inventario" con sus respectivas columnas.
        String sql = """
                CREATE TABLE IF NOT EXISTS empleados (
                    id INT AUTO_INCREMENT PRIMARY KEY, -- Identificador único para cada empleado.
                    nombre VARCHAR(50) NOT NULL, -- Campo para el nombre del empleado, obligatorio.
                    cantidad INT NOT NULL, -- Campo para la cantidad,obligatorio
                    descripcion VARCHAR(100) NOT NULL, -- Campo para la descripcion, obligatorio.
                    usuario VARCHAR(50) NOT NULL -- Campo para el usuario, obligatorio.
                );
                """;

        // Uso de try-with-resources para manejar automáticamente el cierre de recursos.
        try (Connection conexion = conectar()) {
            if (conexion != null) {
                // Ejecuta la consulta SQL para crear la tabla.
                conexion.createStatement().execute(sql);
                System.out.println("Tabla Inventario creada o ya existía.");
            }
        } catch (SQLException e) {
            // Captura y muestra un mensaje de error si ocurre una excepción de SQL.
            System.out.println("Error al crear la tabla: " + e.getMessage());
        }
    }
    // Método para crear la tabla de usuarios en la base de datos
    public void crearTablaUsuarios() {
        // Consulta SQL para crear la tabla 'usuarios', si no existe
        String sql = """
                CREATE TABLE IF NOT EXISTS usuarios (
                    id INT AUTO_INCREMENT PRIMARY KEY, -- Identificador único para cada Usuario.
                    nombre VARCHAR(100) NOT NULL,
                    apellidos VARCHAR(100) NOT NULL,
                    email VARCHAR(255) NOT NULL,
                    username VARCHAR(100) NOT NULL,
                    password VARCHAR(255) NOT NULL,
    
                );
                """;

        // Se utiliza un bloque try-with-resources para asegurar que la conexión y los recursos se cierren automáticamente
        try (Connection conexion = conectar();
             Statement stmt = conexion.createStatement()) {
            // Ejecuta la consulta de creación de la tabla
            stmt.execute(sql);
            System.out.println("Tabla 'usuarios' creada correctamente.");
        } catch (SQLException e) {
            // Captura y muestra cualquier excepción que ocurra durante la ejecución de la consulta
            System.out.println("Error al crear la tabla: " + e.getMessage());
        }
    }
}
