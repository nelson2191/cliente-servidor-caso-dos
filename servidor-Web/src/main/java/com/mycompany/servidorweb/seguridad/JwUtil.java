/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.servidorweb.seguridad;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;

/**
 *
 * @author nelsonrivas
 */
public class JwUtil {
     private static final String SECRET_KEY = "MySecretKeyForJWTGeneration1234567890"; 

    // Se crea una clave (Key) a partir de la SECRET_KEY utilizando el algoritmo HMAC-SHA-256.
    private static final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes()); // Convierte la clave secreta a un objeto Key.

    // Método estático para generar un token JWT con el nombre de usuario proporcionado.
    // Este token tiene un tiempo de expiración de 1 hora.
    public static String generateToken(String username) {
        // Usamos la biblioteca JJWT para construir el token.
        return Jwts.builder() // Inicia la construcción del token JWT.
                .setSubject(username) // Establece el "subject" del token, en este caso el nombre de usuario.
                .setIssuedAt(new Date()) // Establece la fecha de emisión del token.
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // Establece la fecha de expiración (1 hora a partir de la emisión).
                .signWith(key, SignatureAlgorithm.HS256) // Firma el token usando la clave secreta y el algoritmo HS256 (HMAC con SHA-256).
                .compact(); // Construye el token JWT como un string compactado.
    }

    // Método estático para validar si un token JWT es válido.
    // Intenta parsear el token con la clave secreta y devuelve true si el token es válido.
    // Si el token está mal formado o ha sido manipulado, lanzará una excepción y devolverá false.
    public static boolean validateToken(String token) {
        try {
            // Intentamos parsear el token. Si es válido, se puede continuar.
            Jwts.parserBuilder() // Crea un nuevo parser para procesar el token.
                    .setSigningKey(key) // Establece la clave secreta que se usó para firmar el token.
                    .build() // Construye el parser.
                    .parseClaimsJws(token); // Intenta parsear el token.
            return true; // Si no se lanza ninguna excepción, el token es válido.
        } catch (JwtException e) { // Si ocurre un error (token inválido, expirado, etc.), se captura la excepción.
            return false; // Devuelve false, indicando que el token no es válido.
        }
    }

    // Método estático para obtener el nombre de usuario (subject) desde un token JWT.
    // Extrae la información del "subject" del token, que en este caso es el nombre de usuario.
    public static String getUsernameFromToken(String token) {
        // Usamos el parser para procesar el token y extraer el cuerpo de los claims.
        return Jwts.parserBuilder() // Crea un nuevo parser para procesar el token.
                .setSigningKey(key) // Establece la clave secreta que se usó para firmar el token.
                .build() // Construye el parser.
                .parseClaimsJws(token) // Intenta parsear el token.
                .getBody() // Obtiene el cuerpo (claims) del token.
                .getSubject(); // Extrae el "subject", que en este caso es el nombre de usuario.
    }
}
