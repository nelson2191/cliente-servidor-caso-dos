/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.servidorweb.models;

/**
 *
 * @author nelsonrivas
 */
public class Usuario extends persona {

    private String usuario;  // Atributo para almacenar el nombre de usuario.
    private String password; // Atributo para almacenar la contraseña

    public Usuario(String usuario, String password, int id, String nombre, String apellido, String email) {
        super(id, nombre, apellido, email);
        this.usuario = usuario;
        this.password = password;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
}
