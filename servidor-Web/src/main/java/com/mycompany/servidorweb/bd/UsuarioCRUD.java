/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.servidorweb.bd;

import com.mycompany.servidorweb.models.Usuario;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
 *
 * @author nelsonrivas
 */
@Service
public class UsuarioCRUD {
    @Autowired
    private final ConexionMySQL conexionMySQL = new ConexionMySQL();
    
     // Método para registrar un nuevo usuario en la base de datos
    public void registrarUsuario(Usuario usuario) {
        // Llama al método crearTabla() para asegurarse de que la tabla de usuarios esté creada
        conexionMySQL.crearTablaUsuarios();
        
        // Consulta SQL para insertar un nuevo usuario en la tabla 'usuarios'
        String sql = "INSERT INTO usuarios (nombre, apellido, email, username, passwords) VALUES (?, ?, ?, ?, ?)";

        // Se utiliza un bloque try-with-resources para asegurar que la conexión y los recursos se cierren automáticamente
        try (Connection conexion = conexionMySQL.conectar();
             // Preparar la consulta SQL para ser ejecutada
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            // Se asignan los valores de los atributos del usuario a los parámetros de la consulta
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getApellido());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getUsuario());
            stmt.setString(5, hashClave(usuario.getPassword())); // Genera el hash de la clave
            // Ejecuta la consulta de inserción
            stmt.executeUpdate();
            System.out.println("Usuario registrado correctamente.");
        } catch (SQLException e) {
            // Captura y muestra cualquier excepción que ocurra durante la ejecución de la consulta
            System.out.println("Error al registrar el usuario: " + e.getMessage());
        }
    }
    
    public void modificarUsuario(Usuario usuario){
        //Llama al método crearTabla() para asegurarse de que la tabla esté creada
        String sql = "Update usuarios set nombre=?, set apellido=?, set email=?, set clave=?  WHERE usuario = ?";
        // Se utiliza un bloque try-with-resources para asegurar que la conexión y los recursos se cierren automáticamente
        try (Connection conexion = conexionMySQL.conectar();
             // Preparar la consulta SQL para ser ejecutada
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            // Se asignan los valores de los atributos del usuario a los parámetros de la consulta
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getApellido());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getUsuario());
            stmt.setString(5, hashClave(usuario.getApellido())); // Genera el hash de la clav
            // Ejecuta la consulta de inserción
            stmt.executeUpdate();
            System.out.println("Usuario modificado correctamente.");
        } catch (SQLException e) {
            // Captura y muestra cualquier excepción que ocurra durante la ejecución de la consulta
            System.out.println("Error al modificar usuario: " + e.getMessage());
        }
        
    }

    // Método para realizar el login de un usuario
    public boolean loginUsuario(String username, String password) {
        // Consulta SQL para obtener la clave almacenada del usuario por correo
        String sql = "SELECT password FROM usuarios WHERE username = ?";

        // Se utiliza un bloque try-with-resources para asegurar que la conexión y los recursos se cierren automáticamente
        try (Connection conexion = conexionMySQL.conectar();
             // Preparar la consulta SQL para ser ejecutada
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            // Asigna el valor del correo a la consulta
            stmt.setString(1, username);

            // Ejecuta la consulta y obtiene el resultado
            ResultSet rs = stmt.executeQuery();
            // Si se encuentra un registro con el correo proporcionado
            if (rs.next()) {
                // Obtiene la clave hash almacenada en la base de datos
                String claveHash = rs.getString("paswword");
                // Compara el hash de la clave proporcionada con la clave almacenada
                return hashClave(password).equals(claveHash); // Si los hashes coinciden, el login es exitoso
            }
        } catch (SQLException e) {
            // Captura y muestra cualquier excepción que ocurra durante la ejecución de la consulta
            System.out.println("Error al realizar login: " + e.getMessage());
        }
        // Si no se encuentra el usuario o los hashes no coinciden, el login falla
        return false;
    }

    // Método privado para generar el hash de una clave utilizando SHA-256
    private String hashClave(String password) {
        try {
            // Se utiliza el algoritmo SHA-256 para generar el hash
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            // Se genera el hash de la clave en bytes
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            // Convierte los bytes del hash en su representación hexadecimal
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                // Asegura que el valor hexadecimal tenga dos dígitos
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            // Retorna el hash de la clave como una cadena hexadecimal
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // Captura cualquier error relacionado con el algoritmo de hash
            throw new RuntimeException("Error al generar el hash de la clave", e);
        }
    }
    
    
    public void eliminarUsuario(String usuario){
        //Llama al método crearTabla() para asegurarse de que la tabla esté creada
        String sql = "delete from usuarios where  username = ?";
        // Se utiliza un bloque try-with-resources para asegurar que la conexión y los recursos se cierren automáticamente
        try (Connection conexion = conexionMySQL.conectar();
             // Preparar la consulta SQL para ser ejecutada
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            // Se asignan los valores de los atributos del usuario a los parámetros de la consulta
          
            stmt.setString(1, usuario);
            
            // Ejecuta la consulta de Eliminar
            stmt.executeUpdate();
            System.out.println("Usuario eliminado correctamente.");
        } catch (SQLException e) {
            // Captura y muestra cualquier excepción que ocurra durante la ejecución de la consulta
            System.out.println("Error al eliminar usuario: " + e.getMessage());
        }
        
    }
    
     // Método para listar todos los empleados desde la base de datos.
    public List<Usuario> listarUsuarios() {
        // Lista para almacenar los empleados recuperados desde la base de datos.
        List<Usuario> Usuarios = new ArrayList<>();
        
        // Sentencia SQL para seleccionar todos los empleados.
        String sql = "SELECT * FROM usuarios";

        // Uso de try-with-resources para gestionar la conexión, la sentencia y el ResultSet.
        try (Connection conexion = conexionMySQL.conectar();
             Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Recorre los resultados obtenidos en el ResultSet.
            while (rs.next()) {
                // Crea un nuevo objeto empleado con los datos de cada fila.
                Usuario user = new Usuario( 
                        rs.getString("username"), // Nombre del usuario
                         "",//Clave del usuario
                        rs.getInt("id"),//ID del usuario
                        rs.getString("nombre"), // Cedula del usuario
                        rs.getString("apellido"),// Apellido del usuario 
                        rs.getString("email") // email del usuario
                    
                        
                );
                // Agrega el empleado a la lista.
                Usuarios.add(user);
            }
        } catch (SQLException e) {
            // Captura y muestra el error si ocurre un problema durante la consulta.
            System.out.println("Error al listar Usuarios: " + e.getMessage());
        }
        // Retorna la lista de empleados.
        return Usuarios;
    }
    
    
}
