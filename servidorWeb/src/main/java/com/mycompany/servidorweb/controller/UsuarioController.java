/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.servidorweb.controller;

import com.mycompany.servidorweb.bd.UsuarioCRUD;
import com.mycompany.servidorweb.models.Usuario;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author nelsonrivas
 */
@Controller
@RequestMapping("/usuarios")
public class UsuarioController {
    
    @Autowired
    private UsuarioCRUD usuarioCRUD; // Se inyecta una instancia de UsuarioCRUD que maneja las operaciones CRUD para usuarios.

    // Método que maneja las solicitudes GET para mostrar el formulario de registro de un usuario.
    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        // Crea un objeto Usuario vacío para ser utilizado en el formulario de registro.
        model.addAttribute("usuario", new Usuario( "", "",0, "", "",""));
        return "registro";  // Devuelve la vista 'registro.html' donde se mostrará el formulario de registro.
    }

    // Método que maneja las solicitudes POST para registrar un nuevo usuario.
    @PostMapping("/registro")
    public String registrarUsuario(@RequestParam("usuario") String usuario,
            @RequestParam("password") String password,
            @RequestParam("nombre") String nombre,
            @RequestParam("apellido") String apellido,
            @RequestParam("email") String email,
            Model model) {
        
        Usuario usuario1 =new Usuario (usuario, password, 0, nombre, apellido, email);
        // Llama al método registrarUsuario de UsuarioCRUD para almacenar el nuevo usuario en la base de datos.
        usuarioCRUD.registrarUsuario(usuario1);
        model.addAttribute("mensaje", "Usuario registrado correctamente."); // Agrega un mensaje de éxito al modelo.
        return "login"; // Devuelve la vista 'registro.html' mostrando el mensaje de éxito.
    }

    // Método que maneja las solicitudes GET para mostrar el formulario de login (inicio de sesión).
    @GetMapping("/login")
    public String mostrarFormularioLogin(Model model) {
        return "login";  // Devuelve la vista 'login.html' que contiene el formulario de login.
    }

    // Método que maneja las solicitudes POST para iniciar sesión de un usuario.
    @PostMapping("/login")
    public String loginUsuario(@RequestParam String usuario, @RequestParam String password, Model model) {
        // Llama al método loginUsuario de UsuarioCRUD para verificar las credenciales del usuario.
        boolean loginExitoso = usuarioCRUD.loginUsuario(usuario, password);
        
        if (loginExitoso) {
            // Si el login es exitoso, genera un token JWT para el usuario usando su correo.

            model.addAttribute("mensaje", "Login exitoso."); // Agrega un mensaje de éxito al modelo.
            return "bienvenida"; // Devuelve la vista 'bienvenida.html' para mostrar la página de bienvenida.
        } else {
            // Si las credenciales son incorrectas, agrega un mensaje de error al modelo.
            model.addAttribute("mensaje", "Correo o clave incorrectos.");
            return "noacceso"; // Devuelve la vista 'noacceso.html' para mostrar el mensaje de error.
        }
    }
    
     // Método para actualizar los datos de un empleado.
    @PostMapping("/actualizar")
    public String actualizarUsuario(@RequestParam("usuario") String usuario,
            @RequestParam("password") String password,
            @RequestParam("nombre") String nombre,
            @RequestParam("apellido") String apellido,
            @RequestParam("email") String email,
            Model model){
        Usuario usuario1 =new Usuario (usuario, password, 0, nombre, apellido, email);
        usuarioCRUD.modificarUsuario(usuario1); // Actualiza los datos del usuario en la base de datos.
        model.addAttribute("mensaje", "Empleado actualizado correctamente."); // Agrega un mensaje de éxito al modelo.
        List<Usuario> usuarios = usuarioCRUD.listarUsuarios(); // Obtiene la lista actualizada de empleados.
        model.addAttribute("empleados", usuarios); // Agrega la lista de empleados al modelo.
        return "listarUsuarios"; // Devuelve la vista Thymeleaf 'listarUsuarios.html' con la lista de empleados actualizada.
    }
    
    // Método para eliminar un empleado utilizando su cédula.
    @GetMapping("/eliminar/{usuario}")
    public String formularioEliminarxUsuario(@PathVariable("usuario") String usuario, Model model) {
        usuarioCRUD.eliminarUsuario(usuario); // Elimina al empleado de la base de datos.
        List<Usuario> usuarios = usuarioCRUD.listarUsuarios(); // Obtiene la lista actualizada de todos los empleados.
        model.addAttribute("usuarios", usuario); // Agrega la lista de empleados al modelo.
        return "listarUsuarios"; // Devuelve la vista Thymeleaf 'listarEmpleados.html' con la lista actualizada de empleados.
    }
     @GetMapping("/listar")
    public String mostrarFormularioListar(Model model) {
        model.addAttribute("usuarios", new Usuario("", "",0, "", "","")); // Agrega un objeto vacío de 'empleado' para el formulario.
        List<Usuario> usuarios = usuarioCRUD.listarUsuarios(); // Obtiene la lista completa de empleados.
        model.addAttribute("usuarios", usuarios); // Agrega la lista de empleados al modelo.
        
        return "listarUsuarios"; // Devuelve la vista Thymeleaf 'registro.html' para mostrar la lista.
    }
}
