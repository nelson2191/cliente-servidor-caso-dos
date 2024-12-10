/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.servidorweb.controller;

import com.mycompany.servidorweb.bd.ConexionMySQL;
import com.mycompany.servidorweb.bd.InventarioCRUD;
import com.mycompany.servidorweb.models.Inventario;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author nelsonrivas
 */
@Controller
@RequestMapping("/Inventario")
public class InventarioController {
     @Autowired
    private InventarioCRUD inventarioCRUD ;
    
    // Método que se ejecuta antes de cualquier acción del controlador para verificar el token JWT en las cookies.
    // Si el token no es válido o no está presente, se lanza una excepción para denegar el acceso.
    
    
    // Método para mostrar el formulario de creación de un nuevo empleado.
    @GetMapping("/registro")
    public String mostrarFormulario(Model model) {
        // Crea un nuevo objeto 'Inventario' vacío para ser utilizado en el formulario.
        model.addAttribute("inventario", new Inventario(0,"",0,"",""));
        return "registro";  // Devuelve la vista Thymeleaf 'registro.html' que contiene el formulario para agregar empleados.
    }
    
   
    @PostMapping("/registro")
    public String registrarInventario(@ModelAttribute Inventario inventario, Model model) {
        inventarioCRUD.agregarInventario(inventario); // Actualiza los datos del empleado en la base de datos.
        model.addAttribute("mensaje", "Producto actualizado correctamente."); // Agrega un mensaje de éxito al modelo.
        List<Inventario> Inventario = inventarioCRUD.listarInventario(); // Obtiene la lista actualizada de empleados.
        model.addAttribute("inventario", inventario); // Agrega la lista de empleados al modelo.
        return "listarInventario"; // Devuelve la vista Thymeleaf 'listarEmpleados.html' con la lista de empleados actualizada.
    }

    // Método para crear la tabla 'empleados' en la base de datos si no existe.
    @GetMapping("/crear-tabla")
    public String crearTabla(Model model) {
        ConexionMySQL conexionMySQL = new ConexionMySQL();  // Crea una instancia de ConexionMySQL para ejecutar la operación.
        conexionMySQL.crearTablaInventario();// Llama al método que crea la tabla de empleados.
        model.addAttribute("mensaje", "Tabla inventario creada o ya existía."); // Agrega un mensaje al modelo para mostrar en la vista.
        return "tablaCreada"; // Devuelve la vista Thymeleaf 'tablaCreada.html' con el mensaje de confirmación.
    }

    // Método para mostrar el formulario de modificación manual (sin cédula específica).
    @GetMapping("/modificar")
    public String formularioModificarManual(Model model) {
        model.addAttribute("empleado", new Inventario(0,"",0,"","")); // Crea un objeto vacío de 'empleado' para el formulario.
        return "formularioModifica"; // Devuelve la vista Thymeleaf 'formularioModifica.html' para editar un producto
    }

    // Método para agregar un nuevo empleado.
    @PostMapping("/agregar")
    public String agregarUsuario(@ModelAttribute Inventario inventario, Model model) {
        ConexionMySQL conexionMySQL = new ConexionMySQL();  // Crea una instancia de ConexionMySQL.
        conexionMySQL.crearTablaInventario();// Llama al método que crea la tabla de empleados si no existe.
        inventarioCRUD.agregarInventario(inventario);  // Agrega el nuevo empleado a la base de datos.
        model.addAttribute("mensaje", "Empleado agregado correctamente."); // Agrega un mensaje de éxito al modelo.
        List<Inventario> inventarios = inventarioCRUD.listarInventario(); // Obtiene la lista actualizada de empleados.
        model.addAttribute("inventario", inventario); // Agrega la lista de empleados al modelo.
        return "listarInventario"; // Devuelve la vista Thymeleaf 'listarEmpleados.html' para mostrar la lista de empleados.
    }

    // Método para listar todos los empleados.
    @GetMapping("/listar")
    public String mostrarFormularioLista(Model model) {
        List<Inventario> inventarios = inventarioCRUD.listarInventario(); // Obtiene la lista completa de empleados.
        model.addAttribute("inventario", inventarios); // Agrega la lista de empleados al modelo.
        
        return "listarInvertario"; // Devuelve la vista Thymeleaf 'listarEmpleados.html' para mostrar la lista.
    }

    // Método para actualizar los datos de un empleado.
    @PostMapping("/actualizar")
    public String actualizarInventario(@ModelAttribute Inventario inventario, Model model) {
        inventarioCRUD.actualizarInventario(inventario);// Actualiza los datos del inventario en la base de datos.
        model.addAttribute("mensaje", "Inventario actualizado correctamente."); // Agrega un mensaje de éxito al modelo.
        List<Inventario> inventarios = inventarioCRUD.listarInventario(); // Obtiene la lista actualizada de empleados.
        model.addAttribute("inventario", inventarios); // Agrega la lista de empleados al modelo.
        return "listarInventario"; // Devuelve la vista Thymeleaf 'listarEmpleados.html' con la lista de empleados actualizada.
    }
    
     @GetMapping("/eliminar/{id}")
    public String formularioEliminarxID(@PathVariable("id") int id, Model model) {
        inventarioCRUD.eliminarInventario(id);
        List<Inventario> inventario=inventarioCRUD.listarInventario();
        model.addAttribute("inventario",inventario);
        return "listarInventario"; // Devuelve la vista Thymeleaf 'formularioModifica.html' para editar un producto
    }


    // Método para eliminar un empleado usando su cédula.
    @DeleteMapping("/eliminar")
    public String eliminarInventario(@ModelAttribute Inventario inventario, Model model) {
        inventarioCRUD.eliminarInventario(inventario.getId()); // Elimina al empleado de la base de datos.
        model.addAttribute("mensaje", "Producto eliminado correctamente."); // Agrega un mensaje de éxito.
        return "ProductoEliminado"; // Devuelve la vista Thymeleaf 'empleadoEliminado.html' para confirmar la eliminación.
    }
}
