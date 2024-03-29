/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.unam.pixel.model;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * *Clase de modelo
 * @author PIXEL
 */
@Entity
public class Categoria implements Serializable {
    //private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    
    @NotNull
    private String nombre;
    
    private int precioMayor;

    private int precioMenor;

    @ManyToOne(cascade = CascadeType.ALL,fetch =FetchType.EAGER)
    private Local local;
    
    
    public Categoria(){
        nombre ="";
        precioMayor = 200;
        precioMenor = 10;
    }
    
    public int getPrecioMayor() {
        return precioMayor;
    }

    public void setPrecioMayor(int precioMayor) {
        System.out.println("categoria PM:"+precioMayor);
        this.precioMayor = precioMayor;
    }

    public int getPrecioMenor() {
        return precioMenor;
    }

    public void setPrecioMenor(int precioMenor) {
                System.out.println("categoria Pm:"+precioMenor);
        this.precioMenor = precioMenor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
        System.out.println("categoria:"+nombre);
    }

    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Categoria)) {
            return false;
        }
        Categoria other = (Categoria) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.unam.pixel.model.Categoria[ id=" + id + " ]";
    }

    public Local getLocal() {
        return local;
    }

    public void setLocal(Local local) {
        this.local = local;
    }
    
}
