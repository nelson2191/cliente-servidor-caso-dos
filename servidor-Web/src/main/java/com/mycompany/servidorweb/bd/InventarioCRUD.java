/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.servidorweb.bd;

import com.mycompany.servidorweb.models.Inventario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
id INT AUTO_INCREMENT PRIMARY KEY, -- Identificador único para cada empleado.
                    nombre VARCHAR(50) NOT NULL, -- Campo para el nombre del empleado, obligatorio.
                    cantidad INT NOT NULL, -- Campo para la cantidad,obligatorio
                    descripcion VARCHAR(100) NOT NULL, -- Campo para la descripcion, obligatorio.
                    usuario VARCHAR(50) NOT NULL -- Campo para el usuario, obligatorio.
 */
@Service
public class InventarioCRUD {
    
     // Instancia de la clase ConexionMySQL para manejar la conexión con la base de datos.
    @Autowired
    private final ConexionMySQL conexionMySQL = new ConexionMySQL();
    
     // Método para insertar un nuevo empleado en la base de datos.
    public void agregarInventario(Inventario inv) {
        // Sentencia SQL para insertar un nuevo empleado en la tabla empleados.
        String sql = "INSERT INTO inventario (nombre, cantidad, descripcion, usuario) VALUES (?, ?, ?, ?)";

        // Uso de try-with-resources para gestionar automáticamente los recursos (conexión y prepared statement).
        try (Connection conexion = conexionMySQL.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            // Asignación de valores a los parámetros de la consulta.
            ps.setString(1, inv.getNombre()); // Nombre 
            ps.setInt(2, inv.getCantidad()); // cantidad 
            ps.setString(3, inv.getDescripcion()); // descripcion 
            ps.setString(4, inv.getUsuario()); // usuario 

            // Ejecuta la consulta para insertar el registro en la base de datos.
            ps.executeUpdate();
            System.out.println("Producto agregado correctamente.");
        } catch (SQLException e) {
            // Captura y muestra el error si ocurre algún problema con la base de datos.
            System.out.println("Error al agregar producto: " + e.getMessage());
        }
    }

    // Método para listar todos los empleados desde la base de datos.
    public List<Inventario> listarInventario() {
        // Lista para almacenar los empleados recuperados desde la base de datos.
        List<Inventario> inventario = new ArrayList<>();
        
        // Sentencia SQL para seleccionar todos los empleados.
        String sql = "SELECT * FROM inventario";

        // Uso de try-with-resources para gestionar la conexión, la sentencia y el ResultSet.
        try (Connection conexion = conexionMySQL.conectar();
             Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Recorre los resultados obtenidos en el ResultSet.
            while (rs.next()) {
                // Crea un nuevo objeto empleado con los datos de cada fila.
                Inventario inv = new Inventario(
                        rs.getInt("id"), 
                        rs.getString("nombre"), 
                        rs.getInt("cantidad"), 
                        rs.getString("descripcion"), 
                        rs.getString("usuario") // 
                );
                // Agrega el producto a la lista.
                inventario.add(inv);
            }
        } catch (SQLException e) {
            // Captura y muestra el error si ocurre un problema durante la consulta.
            System.out.println("Error al listar producto: " + e.getMessage());
        }
        // Retorna la lista de empleados.
        return inventario;
    }

    // Método para obtener un empleado por su cédula.
    public Inventario obtenerPorID(int id) {
        // Sentencia SQL para seleccionar un producto por su ID
        String sql = "SELECT * FROM inventario WHERE id = ?";
        Inventario inventario = null; // Objeto que almacenará el resultado.

        // Uso de try-with-resources para gestionar la conexión y la sentencia.
        try (Connection conexion = conexionMySQL.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            // Asigna el valor de la cédula al parámetro de la consulta.
            stmt.setInt(1, id);

            // Ejecuta la consulta y obtiene los resultados.
            try (ResultSet rs = stmt.executeQuery()) {
                // Si se encuentra un registro, lo mapea a un objeto empleado.
                if (rs.next()) {
                    inventario = new Inventario(
                            rs.getInt("id"), 
                            rs.getString("nombre"), // Cedula del empleado.
                            rs.getInt("cantidad"), // Nombre del empleado.
                            rs.getString("descripcion"),
                            rs.getString("usuario")
                    );
                }
            }
        } catch (SQLException e) {
            // Captura y muestra el error si ocurre un problema con la base de datos.
            System.out.println("Error al obtener producto: " + e.getMessage());
        }

        // Retorna el empleado encontrado o null si no existe.
        return inventario;
    }

    // Método para actualizar los datos de un empleado.
    public void actualizarInventario(Inventario inv) {
        // Sentencia SQL para actualizar los datos de un empleado en la base de datos.
        String sql = "UPDATE inventario SET nombre = ?, cantidad = ?, descripcion = ?, usuario = ? WHERE id = ?";

        // Uso de try-with-resources para gestionar la conexión y el prepared statement.
        try (Connection conexion = conexionMySQL.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            // Asignación de valores a los parámetros de la consulta.
            ps.setString(1, inv.getNombre()); // 
            ps.setInt(2, inv.getCantidad()); // 
            ps.setString(3, inv.getDescripcion()); // 
            ps.setString(4, inv.getUsuario()); // 

            // Ejecuta la actualización en la base de datos.
            ps.executeUpdate();
            System.out.println("Producto actualizado correctamente.");
        } catch (SQLException e) {
            // Captura y muestra el error si ocurre un problema durante la actualización.
            System.out.println("Error al actualizar producto: " + e.getMessage());
        }
    }

    // Método para eliminar un empleado de la base de datos.
    public void eliminarInventario(int id) {
        // Sentencia SQL para eliminar un empleado por su cédula.
        String sql = "DELETE FROM inventario WHERE id = ?";

        // Uso de try-with-resources para gestionar la conexión y el prepared statement.
        try (Connection conexion = conexionMySQL.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            // Asigna la cédula del empleado a eliminar.
            ps.setInt(1, id);

            // Ejecuta la eliminación del registro en la base de datos.
            ps.executeUpdate();
            System.out.println("Producto eliminado correctamente.");
        } catch (SQLException e) {
            // Captura y muestra el error si ocurre un problema durante la eliminación.
            System.out.println("Error al eliminar producto: " + e.getMessage());
        }
    }
    
}
