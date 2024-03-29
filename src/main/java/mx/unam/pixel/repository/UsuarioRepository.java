/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.unam.pixel.repository;

import java.util.List;
import mx.unam.pixel.model.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 *Consultas para operar con los usuarios
 * @author PIXEL
 */
public interface UsuarioRepository extends CrudRepository<Usuario, Integer>{
     @Query("SELECT l FROM Usuario l")
     @Override
    List<Usuario> findAll();
    
    @Query("SELECT u FROM Usuario u WHERE u.nombre LIKE CONCAT(?,'%') ")
    List<Usuario> findByNombre(String nombre);
    
        @Query("SELECT u FROM Usuario u WHERE u.nombreUsuario LIKE ? ")
    List<Usuario> findByNombreUsuario(String nombreUsuario);
    
        @Query("SELECT u FROM Usuario u WHERE u.correo LIKE ? ")
    List<Usuario> findByCorreo(String correo);
    
    @Query("SELECT u FROM Usuario u WHERE u.nombre LIKE ? AND u.contrasena LIKE ? ")
    List<Usuario> findByNombreContrasena(String nombre,String contrasena);
    
    @Query("UPDATE Usuario u SET u.administrador = ?2 WHERE u.nombreUsuario LIKE ?1")
    void rolAdministrador(String usuario, boolean rol);
    
        
}
