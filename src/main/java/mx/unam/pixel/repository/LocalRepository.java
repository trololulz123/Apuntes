/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.unam.pixel.repository;

import java.util.List;
import mx.unam.pixel.model.Comentario;
import mx.unam.pixel.model.Local;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Este local ve muchas consultas entre ellas las de busqueda avanzada por facultad, por
 * ubicacion y los comentarios de un local
 * @author PIXEL
 */
public interface LocalRepository extends CrudRepository<Local, Integer>{
     @Query("SELECT l FROM Local l")
     @Override
    List<Local> findAll();
    
    @Query("SELECT l FROM Local l WHERE l.bano = TRUE AND l.aprobado = TRUE")
    List<Local> findByBano();
    
    @Query("SELECT l FROM Local l WHERE l.bano = ? ")
    List<Local> findByBanoAdmin(Boolean bano);
    
    @Query("SELECT loc FROM Local loc WHERE loc.nombre LIKE CONCAT('%',?,'%') AND loc.aprobado = TRUE")
    List<Local> findByNombre(String nombre);

    @Query("SELECT loc FROM Local loc WHERE loc.nombre LIKE CONCAT('%',?,'%')")
    List<Local> findByNombreAdmin(String nombre);
    
    /**
     *
     * @param rangoInferior
     * @param rangoSuperior
     * @return
     */
    @Query("SELECT l FROM Local l WHERE l.rangoInferior >= ?1 AND l.rangoSuperior<= ?2 AND l.aprobado = TRUE")
    List<Local> findByRangoInferior(Integer rangoInferior,Integer rangoSuperior);

    @Query("SELECT l FROM Local l WHERE l.rangoInferior > ? AND l.rangoSuperior< ?")
    List<Local> findByRangoInferiorAdmin(Integer rangoInferior,Integer rangoSuperior);
    
    @Query("SELECT loc FROM Local loc WHERE loc.wifi = true AND loc.aprobado = TRUE")
    List<Local> findByWifi();

    @Query("SELECT loc FROM Local loc WHERE loc.facultad IN (SELECT f FROM Facultad f WHERE f.estacionamiento != 0 ) AND loc.aprobado = TRUE")
    List<Local> findByEstacionamiento();

    @Query("SELECT loc FROM Local loc WHERE loc.wifi = true")
    List<Local> findByWifiAdmin();

    @Query("SELECT loc FROM Local loc WHERE loc.facultad IN (SELECT f FROM Facultad f WHERE f.estacionamiento = 1)")
    List<Local> findByEstacionamientoAdmin();

    @Query("SELECT loc FROM Local loc WHERE loc.aprobado = ? ")
    List<Local> findByAprobado(Boolean b);
    

    @Query("SELECT loc FROM Local loc WHERE loc.comerOLlevar = 1 or loc.comerOLlevar = 3 AND loc.aprobado = TRUE")
    List<Local> findByComer();
    
    @Query("SELECT loc FROM Local loc WHERE loc.comerOLlevar = 1 or loc.comerOLlevar = 3 ")
    List<Local> findByComerAdmin();
    
    @Query("SELECT loc FROM Local loc WHERE loc.comerOLlevar = 2 or loc.comerOLlevar = 3 AND loc.aprobado = TRUE")
    List<Local> findByLlevar();    
    
    @Query("SELECT loc FROM Local loc WHERE loc.comerOLlevar = 2 or loc.comerOLlevar = 3")
    List<Local> findByLlevarAdmin();
    
    @Query("SELECT l FROM Local l JOIN FETCH l.facultad f WHERE f.nombreFac LIKE CONCAT('%',?,'%') AND l.aprobado = TRUE")
    List<Local> findByFacultad(String facultad);
    
    @Query("SELECT l FROM Local l JOIN FETCH l.facultad f WHERE f.nombreFac LIKE CONCAT('%',?,'%')")
    List<Local> findByFacultadAdmin(String facultad);
    
    @Query("SELECT l FROM Local l JOIN FETCH l.recomendacion r WHERE r.nombre LIKE CONCAT('%',?,'%') AND l.aprobado = TRUE")
    List<Local> findByRecomendacion(String recomendacion);
    
    @Query("SELECT l FROM Local l JOIN FETCH l.recomendacion r WHERE r.nombre LIKE CONCAT('%',?,'%') ")
    List<Local> findByRecomendacionAdmin(String recomendacion);
    
    
    @Query("SELECT l FROM Local l WHERE l.recomendacion.nombre LIKE CONCAT('%',?,'%')")
    List<Local> findByRecomendacion1(String recomendacion);
        
    //@Query("SELECT l FROM Local l JOIN FETCH l.categorias c WHERE c.nombre LIKE CONCAT('%',?,'%') ")
    //List<Local> findByCategoria(String categoria);
    
    @Query("SELECT l FROM Local l JOIN FETCH l.categorias c WHERE c.nombre LIKE CONCAT('%',?,'%') AND l.aprobado = TRUE")
    List<Local> findByCategoria(String categoria);
    
    @Query("SELECT l FROM Local l JOIN FETCH l.categorias c WHERE c.nombre LIKE CONCAT('%',?,'%') ")
    List<Local> findByCategoriaAdmin(String categoria);
    
    
    /*@Query("SELECT l FROM Local l JOIN FETCH l.rutas r "+
           "WHERE r.nombreRuta LIKE CONCAT('%',?,'%')")
    List<Local> findByRutaPumaBus(String nombreRuta);
    */
    
    //Hay que limpiar estps metodos pues estan contnidos en la facultad
    
    @Query("SELECT l FROM Local l JOIN FETCH l.facultad f JOIN FETCH f.pumabus p "+
           "WHERE p.estacion LIKE CONCAT('%',?,'%') AND l.aprobado = TRUE")
    List<Local> findByPumabus(String estacion);
        @Query("SELECT l FROM Local l JOIN FETCH l.facultad f JOIN FETCH f.biciPuma p "+
           "WHERE p.nombre LIKE CONCAT('%',?,'%') AND l.aprobado = TRUE")
    List<Local> findByBiciPuma(String estacion);
        @Query("SELECT l FROM Local l JOIN FETCH l.facultad f JOIN FETCH f.metro p "+
           "WHERE p.nombre LIKE CONCAT('%',?,'%') AND l.aprobado = TRUE")
    List<Local> findByMetro(String estacion);
        @Query("SELECT l FROM Local l JOIN FETCH l.facultad f JOIN FETCH f.metroBus p "+
           "WHERE p.nombre LIKE CONCAT('%',?,'%') AND l.aprobado = TRUE")
    List<Local> findByMetroBus(String estacion);
    
    @Query("SELECT l FROM Local l JOIN FETCH l.facultad f JOIN FETCH f.pumabus p "+
           "WHERE p.estacion LIKE CONCAT('%',?,'%')")
    List<Local> findByPumabusAdmin(String estacion);
        @Query("SELECT l FROM Local l JOIN FETCH l.facultad f JOIN FETCH f.biciPuma p "+
           "WHERE p.nombre LIKE CONCAT('%',?,'%')")
    List<Local> findByBiciPumaAdmin(String estacion);
        @Query("SELECT l FROM Local l JOIN FETCH l.facultad f JOIN FETCH f.metro p "+
           "WHERE p.nombre LIKE CONCAT('%',?,'%')")
    List<Local> findByMetroAdmin(String estacion);
    
    @Query("SELECT l FROM Local l JOIN FETCH l.facultad f JOIN FETCH f.metroBus p WHERE p.nombre LIKE CONCAT('%',?,'%')")
    List<Local> findByMetroBusAdmin(String estacion);
    
//Hay que limpiar estps metodos pues estan contnidos en la facultad    

    
    @Query("SELECT l FROM Facultad l ")
    List<Local> findAllFacultades();

    
    @Query("SELECT l FROM Local l WHERE SQRT(POWER(l.latitud-?,2)+POWER(l.longitud-?,2))<0.01   AND l.aprobado = TRUE")
    List<Local> findByPunto(Double latitud,Double longitud);
    
    @Query("SELECT l FROM Local l WHERE SQRT(POWER(l.latitud-?,2)+POWER(l.longitud-?,2))<0.01")
    List<Local> findByPuntoAdmin(Double latitud,Double longitud);

    
     /*   @Query("UPDATE TABLE Local l SET l.calificacion = (SELECT AVG(c.calificacion)"
                + " FROM Comentario c WHERE c.local.id = ?) "
                + "WHERE l.id = ?")
    void actualizaCalificacion(Integer id);*/
    
    
    @Query("SELECT AVG(c.calificacion) FROM Comentario c WHERE c.local.nombre = ? ")
    public Double getPromedio(String nombre);
    
    @Query("SELECT l FROM Local l WHERE l.id = ? ")
    public List<Local> findById(Integer id);
    
    
    @Query("SELECT l.foto FROM Local l WHERE l.id = ? ")
    public byte[] findFoto(Integer id);
}
