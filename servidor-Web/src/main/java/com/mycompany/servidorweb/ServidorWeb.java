/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.servidorweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author Mario
 */
// Indica que esta es una clase principal de una aplicación Spring Boot
// y que debe escanear los paquetes especificados para detectar componentes, controladores y servicios.
@SpringBootApplication()
public class ServidorWeb {

    // Método principal (main) que sirve como punto de entrada para la ejecución del programa.
    public static void main(String[] args) {
        // Llama al método estático run de la clase SpringApplication.
        // Este método inicializa el contexto de Spring, configura la aplicación y la ejecuta.
        SpringApplication.run(ServidorWeb.class, args);
    }
}
