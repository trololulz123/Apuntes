/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.unam.pixel.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import mx.unam.pixel.model.BiciPuma;

import mx.unam.pixel.model.Categoria;
import mx.unam.pixel.model.Comentario;
import mx.unam.pixel.model.Facultad;
import mx.unam.pixel.model.Local;
import mx.unam.pixel.model.Metro;
import mx.unam.pixel.model.Metrobus;
import mx.unam.pixel.model.Pumabus;
import mx.unam.pixel.repository.FacultadRepository;
import mx.unam.pixel.service.LocalService;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.map.Circle;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.util.comparator.ComparableComparator;


import java.io.Serializable;
import javax.annotation.PostConstruct;
import mx.unam.pixel.model.Usuario;
/*import javax.faces.bean.ManagedBean;*/
 
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

	

/**
 * Esta clase controlla a los locales tiene una instancia de los locales en la base de dato sy ademas tiene 
 * un local que es el que se esta creando para egistrarlo en la base de datos
 * todos los demas atributos que estan en esta clase son para realizar busquedas, 
 * @author PIXEL
 */
@Controller("localController")
@Scope("session")
@ManagedBean
public class LocalController {
    @Autowired
    private LocalService localService;
        
    
    private Local local;
    private List<Local> locales;
    private List<Local> localesAdmin;
        private List<Local> top;

    private String busqueda = "";
        
    private List<Pumabus> pumabuses;
    private List<Metro> metros;
    private List<BiciPuma> biciPumas;
    private List<Metrobus> metrobuses;
    private MapModel simpleModel; // es usado en la vista del ver locales
   
    private List<Facultad> facultades;
    
    private List<Categoria> especialidades;

    private List<Categoria> categoriasAdmin;
    
    private Boolean aprobado = true;
    private String nombre = "";
    private Categoria categoria;
    private String categoriaBusqueda = "";
    private Integer rangoInferior = 20;
    private Integer rangoSuperior = 200;
    private Boolean wifi = false;
    private Boolean estacionamiento = false;
    private Integer comeOLleva = 3;
    private String busquedaFacultad = "";
    private String pumabus = "";
    private String bicipuma = "";
    private String metro = "";
    private String metrobus = "";
    private Boolean admin = false;
    private String facultad = "";
    private Integer localId ;
    private Boolean bano = false;
    private boolean comer = true, llevar =true;
    

    @Autowired
    private JavaMailSenderImpl mailSender;
    
    
    
    String [] categorias ={
"Aguas",
"Antojitos",
"Café",
"Corrida",
"Empanada",
"Ensaladas",
"Fruta",
"Gourmet",
"Internacional",
"Japonesa",
"Jugos",
"Licuados",
"Mexicana",
"Panes (baguettes, chapatas, cuernitos, etc.)",
"Paquete",
"Postres",
"Rápida",
"Sandwiches",
"Tacos",
"Tacos de canasta",
"Tiendita",
"Tortas",
"Vegetariana",
"Entradas",
"Carnes",
"Pescados",
"Pastas"
};

    

String[] bicipumaArray ={
    "anexo de ingenieria"
    ,"arquitectura"
    ,"bicicentro metro c.u."
    ,"ciencias"
    ,"ciencias politicas"
    ,"derecho"
    ,"estadio olimpico"
    ,"estadio tapatio mendez"
    ,"filosofia"
    ,"ingenieria"
    ,"medicina"
    ,"quimica"
    };

    
    String[] pumabusArray ={
"universidad"
,"cendi"
,"psiquitria"
,"quimica"
,"cele"
,"ingenieria"
,"arquitectura"
,"rectoria"
,"psicologia"
,"filosofia"
,"derecho"
,"economia"
,"odontologia"
,"medicina"
,"veterinaria"
,"instituto de geofisica"
,"quimica conjunto d y e"
,"ciencias a"
,"ciencias circuito exterior"
,"contaduria"
,"trabajo social"
,"metrobus cu"
,"educacion a distancia"
,"computo academico"
,"ciencias b"
,"tienda unam"
,"ciencias politicas"
,"investigaciones juridicas"
,"biblioteca nacional"
,"sala nezahualcoyotl"
,"revalidacion de estudios"
,"personal academico"
,"archivo general"
,"universum"
,"teatro y danza"
,"tv unam"
,"ciencias"
,"estadio de practicas"
,"campos de futbol 1"
,"jardin botanico"
,"campos de futbol 2"
,"psiquiatria"
,"av. universidad"
,"ciencias del mar y limnologia"
,"invernadero"
,"posgrado de ingenieria"
,"camino verde"
,"muca"
,"e1"
,"e2"
,"e3"
,"e4"
,"e6"
,"e7"
,"e8"
,"investigaciones biomedicas"
,"servicios medicos"
,"alberca olimpica"
,"frontones"
,"iimas"
,"biblioteca nacional"
,"sala nezahualcoyotl"
,"relaciones laborales"
,"obras y conservacion"
,"aapaunam"
,"udual"
,"pista de calentamiento"
,"pumitas"
,"instituto de materiales"
,"ciencias del mar"
,"investigacion cientifica"
,"instituto de quimica"
    };

    String [] metrobusArray= {
        "ccu",
        "cu",
        "dr galvez"
    };

    String[] facultadArray = {
        "Anexo de Ingenieria"
,"Arquitectura"
,"Ciencias"
,"Ciencias Politicas y Sociales"
,"Contaduria y Administracion"
,"Derecho"
,"Economia"
,"Filosofia y Letras"
,"Ingenieria"
,"Medicina"
,"Medicina Veterinaria y Zootecnia"
,"Odontologia"
,"Psicologia"
,"Trabajo Social"
,"Quimica"
,"Zona Cultural"

    };
    
    
    public void findLocalByID(){
        local = localService.findById(localId);
    }
    
    public Integer getLocalId() {
        return localId;
    }

    public void setLocalId(Integer localId) {
        this.localId = localId;
    }

    public String[] getCategorias() {

        return categorias;
    }

    public void setCategorias(String[] categorias) {

        this.categorias = categorias;
    }
    
    /**
     * El metodo donde se incializan las variables para administrar y sugerir un local
     */
    @PostConstruct
    public void init(){
 
        ArrayList<Usuario> users = localService.findUsuarios();
        if (users == null ){
            Usuario master = new Usuario();
            master.setAdministrador(true);
            master.setActivo(true);
            master.setContrasena("MUFFIN_ADMIN");
            master.setNombre("Muffin");
            master.setNombreUsuario("Muffin");
            master.setCorreo("");
            users = new ArrayList<Usuario>();
            localService.guardaUsuario(master);
        }else{
            boolean hayMaster = false;
            for (Usuario u : users){
                if (u.getNombreUsuario().equals("Muffin")){
                    hayMaster = true;
                    break;
                }
            }
                            if (!hayMaster){
                               Usuario master = new Usuario();
            master.setAdministrador(true);
            master.setActivo(true);
            master.setContrasena("MUFFIN_ADMIN");
            master.setNombre("Muffin");
            master.setNombreUsuario("Muffin");
            master.setCorreo("");
            users = new ArrayList<Usuario>();
            localService.guardaUsuario(master);
                }
        }
        
        if(local == null){this.local=new Local();
        this.local.setCalificacion(5);
        }
        //las especialidades tiene como precio mayor 0
        especialidades = this.localService.findEspecialidades();
        
        //se va a borrar pues estan en las facultades
        pumabuses=this.localService.findAllPumabus();
        metros=this.localService.findAllMetro();
        biciPumas=this.localService.findAllBiciPuma();
        metrobuses=this.localService.findAllMetrobus();
        //se va a borrar pues estan en las facultades        
        locales=this.localService.findAll();
        top = getTop();
       
        facultades = localService.findAllFacultades();
        if (facultades == null || facultades.size() == 0 ) inicializaFacultades();
        
        simpleModel = new DefaultMapModel(); 

    }
    
    /**
     * De las categorias que se agregan las añade a la lista de categorias
     * del local que esta por añadirse
     */
    public void guardarCategoria(){
       if(local == null){this.local=new Local();
       this.local.setCalificacion(5);
       }
       if (local.getCategorias() == null)local.setCategorias(new ArrayList<Categoria>());
       
       local.getCategorias().add(categoria);
       categoria = new Categoria(); 
    }
    
    /**
     * Añade a la base de datos un local nuevo verificando que tanto sus comentarios como 
     * categorias esten enlazados correctamente e incializa los campos para que se pueda 
     * añadir un nuevo local
     */
    public void guardarLocal(){

               
        for(Facultad f:facultades){

            if(f.getNombreFac().equals(busqueda)){
                local.setFacultad(f);
                break;
            }
        }
        int comerLlevar = (comer && llevar )? 3:(comer)? 1:2;
        local.setComerOLlevar(comerLlevar);
        
        this.localService.guardaLocal(local);
        
        this.locales=localService.findAll();
        this.local=new Local();
        this.local.setCalificacion(5);
        this.local.setCategorias(new ArrayList<Categoria>());
        
        
        
        
        
    }
    
    /**
     * Si un local ya existe con esto lo actualiza en la base de datos
     * se usa para añadir los comentarios
     */
    public void actualizaLocal(){
        this.localService.guardaLocal(local);
        this.locales = localService.findAll();
        this.localesAdmin = localService.findAllAdmin();
        local = new Local();
        
    }
    
    /**
     * Metodo que obtiene las coordenadas que se uaran para el local cuando
     * este esta siendo sugerido
     * @param event el enevnto que indica las coordenadas
     */
    public void seleccion(PointSelectEvent event){
        
        if(local == null){this.local=new Local(); 
              this.local.setCalificacion(5);
               }
        
         LatLng latlng = event.getLatLng();
         this.local.setLatitud(latlng.getLat());
         this.local.setLongitud(latlng.getLng());
         
         System.out.println(""+latlng.getLat());
    }

    /**
     * En base al campo busqueda de este controlador solicita al service
     * todos los locales que conisidan con el nombre
     * @return La lista que coincida con "busqueda" como nombre
     */
    public List<Local> buscarPorNombre(){
        this.locales=localService.findByNombre(busqueda);
         simpleModel = new DefaultMapModel(); 
        for(Local l:this.locales){
            LatLng coord = new LatLng(l.getLatitud(), l.getLongitud()); 
            simpleModel.addOverlay(new Marker(coord, l.getNombre()));
        }
        busqueda = "";
        return locales;
    }
    
    /**
     * En base a todos los campos de busquda si estos valores son vacios o por default 
     * no se realiza su busqueda, y si tiene valores asignados si se realiza la busqueda
     * en base a esos valores y se actualiza la lista "locales" que se muestra con los 
     * resultados de la consulta
     */
    public void busquedaAvanzada(){
        this.locales = localService.busquedaAvanzada(nombre,
                rangoInferior, rangoSuperior, wifi, estacionamiento,busquedaFacultad,
                pumabus, bicipuma, metrobus,bano,categoriaBusqueda);         
        nombre = "";
        rangoInferior = 0;
        rangoSuperior = 200;
        wifi = false;
        estacionamiento = false;
        busquedaFacultad = "";
        pumabus = "";
        bicipuma = "";
        metrobus = "";
        bano = false;
        categoriaBusqueda = "";
        
        simpleModel = new DefaultMapModel(); 
        for(Local l:this.locales){
            LatLng coord = new LatLng(l.getLatitud(), l.getLongitud()); 
            simpleModel.addOverlay(new Marker(coord, l.getNombre()));
        }
    }
    
    /**
     * Elimina un local de la base de datos 
     */
    public void borraLocal(){
        
         localService.eliminaLocal(local);
         local = new Local();
         this.locales=localService.findAll();
          simpleModel = new DefaultMapModel(); 
        for(Local l:this.locales){
            LatLng coord = new LatLng(l.getLatitud(), l.getLongitud()); 
            simpleModel.addOverlay(new Marker(coord, l.getNombre()));
        }
     }
    
    /**
     * Recibe la foto que se añade a la descripcion de un local
     * y la guarda en el campo correspondiente
     * @param event
     */
    public void handleFileUpload(FileUploadEvent event) {
        
        if(local == null){this.local=new Local();
        
                     this.local.setCalificacion(5);

        }
            UploadedFile file=event.getFile();
            this.local.setFoto( file.getContents());   
    }
     
    public String getFacultad() {
        return facultad;
    }

    public void setFacultad(String facultad) {
        System.out.println(facultad);
        this.facultad = facultad;
    }



    public LocalService getLocalService() {
        return localService;
    }

    public void setLocalService(LocalService localService) {
        this.localService = localService;
    }

    public Local getLocal() {
        
        if(local == null){this.local=new Local();
                      this.local.setCalificacion(5);

        }
        return local;
    }

    public void setLocal(Local local) {
        this.local = local;
    }

    public List<Local> getLocales() {
        return locales;
    }

    public List<Local> getLocalesAdmin() {
        //en vez de este, hacer una if dependiendo de el usuario que lo pide;
        return localService.findAllAdmin();
    }
    
    
    
    public void setLocales(List<Local> locales) {
        this.locales = locales;
    }

    public List<Pumabus> getPumabuses() {
        return pumabuses;
    }

    public void setPumabuses(List<Pumabus> pumabuses) {
        this.pumabuses = pumabuses;
    }
    
    

    public List<Metro> getMetros() {
        return metros;
    }

    public void setMetros(List<Metro> metros) {
        this.metros = metros;
    }

    public List<BiciPuma> getBiciPumas() {
        return biciPumas;
    }

    public void setBiciPumas(List<BiciPuma> biciPumas) {
        this.biciPumas = biciPumas;
    }

    public List<Metrobus> getMetrobuses() {
        return metrobuses;
    }

    public void setMetrobuses(List<Metrobus> metrobuses) {
        this.metrobuses = metrobuses;
    }

    public MapModel getSimpleModel() {
        LatLng coord = new LatLng(local.getLatitud(), local.getLongitud());
        simpleModel.addOverlay(new Marker(coord, local.getNombre()));
        return simpleModel;
    }

    public void setSimpleModel(MapModel simpleModel) {
        this.simpleModel = simpleModel;
    }

    public boolean isAprobado() {
        return aprobado;
    }

    public void setAprobado(boolean aprobado) {
        this.aprobado = aprobado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        System.out.println(nombre);
        this.nombre = nombre;
    }

    public Categoria getCategoria() {
                if(categoria == null )categoria = new Categoria();
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
                if(categoria == null )categoria = new Categoria();
        this.categoria = categoria;
    }

    public Integer getRangoInferior() {
        return rangoInferior;
    }

    public void setRangoInferior(Integer rangoInferior) {
                System.out.println(rangoInferior);

        this.rangoInferior = rangoInferior;
    }

    public Integer getRangoSuperior() {
        return rangoSuperior;
    }

    public void setRangoSuperior(Integer rangoSuperior) {
                        System.out.println(rangoSuperior);

        this.rangoSuperior = rangoSuperior;
    }

    public Boolean getWifi() {
        return wifi;
    }

    public void setWifi(Boolean wifi) {
                        System.out.println(wifi+"");

        this.wifi = wifi;
    }

    public Boolean getEstacionamiento() {
        return estacionamiento;
    }

    public void setEstacionamiento(Boolean estacionamiento) {
                        System.out.println(estacionamiento);

        this.estacionamiento = estacionamiento;
    }

    public Integer getComeOLleva() {
        return comeOLleva;
    }

    public void setComeOLleva(Integer comeOLleva) {
        this.comeOLleva = comeOLleva;
    }

    public String getBusquedaFacultad() {
        return busquedaFacultad;
    }

    public void setBusquedaFacultad(String busquedaFacultad) {
                        System.out.println(busquedaFacultad);

        this.busquedaFacultad = busquedaFacultad;
    }

    public String getPumabus() {
        return pumabus;
    }

    public void setPumabus(String pumabus) {
                        System.out.println(pumabus);

        this.pumabus = pumabus;
    }

    public String getBicipuma() {
        return bicipuma;
    }

    public void setBicipuma(String bicipuma) {
                        System.out.println(bicipuma);

        this.bicipuma = bicipuma;
    }

    public String getMetro() {
        return metro;
    }

    public void setMetro(String metro) {
                        System.out.println(metro);

        this.metro = metro;
    }

    public String getMetrobus() {
        return metrobus;
    }

    public void setMetrobus(String metrobus) {
                        System.out.println(metrobus);

        this.metrobus = metrobus;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public List<Facultad> getFacultades() {
        if (facultad == null || facultades.size()== 0)inicializaFacultades();
        return facultades;
    }

    public void setFacultades(List<Facultad> facultades) {
        this.facultades = facultades;
    }

    public Boolean getAprobado() {
        return aprobado;
    }

    public void setAprobado(Boolean aprobado) {
        this.aprobado = aprobado;
    }

    public JavaMailSenderImpl getMailSender() {
        return mailSender;
    }

    public void setMailSender(JavaMailSenderImpl mailSender) {
        this.mailSender = mailSender;
    }

    public List<Categoria> getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(List<Categoria> especialidades) {
        this.especialidades = especialidades;
    }
    
    
    
    public void visitaLocal(Local l){
        this.local = l;
    }
    
    /**
     * Obtiene los 5 locales mejor calificados de la base de datos
     * @return los 5 locales mejor calificados
     */
    public List<Local> getTop() {
                locales = localService.findAll();

         Collections.sort(locales,new Comparator<Local>() {

            @Override
            public int compare(Local o1, Local o2) {
                if (o2.getCalificacion() == null)o2.setCalificacion(5);
                if (o1.getCalificacion() == null)o1.setCalificacion(5);
                return o2.getCalificacion().compareTo(o1.getCalificacion());
            }
        });
        
        top= (locales.size()>= 5)?locales.subList(0,5):locales;
        return top;
    }

    public void setTop(List<Local> top) {
        this.top = top;
    }

    public List<Categoria> getCategoriasAdmin() {
        
        return categoriasAdmin;
    }

    public void setCategoriasAdmin(List<Categoria> CategoriasAdmin) {
        this.categoriasAdmin = CategoriasAdmin;
    }

    public String getBusqueda() {
        return busqueda;
    }

    public void setBusqueda(String busqueda) {
        System.out.println(busqueda);
        this.busqueda = busqueda;
    }

    public String getCategoriaBusqueda() {
        return categoriaBusqueda;
    }

    public void setCategoriaBusqueda(String categoriaBusqueda) {

        this.categoriaBusqueda = categoriaBusqueda;
    }

    public Boolean getBano() {
        return bano;
    }

    public void setBano(Boolean bano) {
        this.bano = bano;
    }
   
    public boolean isComer() {
        return comer;
    }

    public void setComer(boolean comer) {
        this.comer = comer;
    }

    public boolean isLlevar() {
        return llevar;
    }

    public void setLlevar(boolean llevar) {
        this.llevar = llevar;
    }

    public String[] getBicipumaArray() {
        return bicipumaArray;
    }

    public void setBicipumaArray(String[] bicipumaArray) {
        this.bicipumaArray = bicipumaArray;
    }

    public String[] getPumabusArray() {
        return pumabusArray;
    }

    public void setPumabusArray(String[] pumabusArray) {
        this.pumabusArray = pumabusArray;
    }

    public String[] getMetrobusArray() {
        return metrobusArray;
    }

    public void setMetrobusArray(String[] metrobusArray) {
        this.metrobusArray = metrobusArray;
    }

    public String[] getFacultadArray() {
        return facultadArray;
    }

    public void setFacultadArray(String[] facultadArray) {
        this.facultadArray = facultadArray;
    }
    
    
    public void ajaxListener(){};
    
    public void paraSugerir(){
        local = new Local();
        categoria = new Categoria();
    }
    
    
    private void inicializaFacultades(){
        ArrayList<BiciPuma> bp = new ArrayList<BiciPuma>();
        ArrayList<Metro> m = new ArrayList<Metro>();
        ArrayList<Metrobus> mb = new ArrayList<Metrobus>();
        ArrayList<Pumabus> pb = new ArrayList<Pumabus>();
        
                
        for (int i = 0, l = bicipumaArray.length; i < l ; i ++){
            BiciPuma bpaux = new BiciPuma();
            bpaux.setName(bicipumaArray[i]);
            bp.add(bpaux);
        }

        for (int i = 0, l = metrobusArray.length; i < l ; i ++){
            Metrobus bpaux = new Metrobus();
            bpaux.setNombre(metrobusArray[i]);
            mb.add(bpaux);
        }

        for (int i = 0, l = pumabusArray.length; i < l ; i ++){
            Pumabus bpaux = new Pumabus();
            bpaux.setEstacion(pumabusArray[i]);
            pb.add(bpaux);
        }
        
        Metro cu = new Metro();
        cu.setNombre("universidad");
                Metro copilco = new Metro();
        copilco.setNombre("copilco");
        
        
        Facultad f = new Facultad();
        f.setNombreFac(facultadArray[0]);
        f.setEstacionamiento(3);


        f.setBiciPuma(new ArrayList<BiciPuma>());
        //f.getBiciPuma().add();
        f.setPumabus(new ArrayList<Pumabus>());
        f.getPumabus().add(pb.get(46));
        
        f.setMetroBus(new ArrayList<Metrobus>());

        localService.guardaFacultad(f);
        
        ////CAMBIOOOO
         f = new Facultad();
        f.setNombreFac(facultadArray[1]);
        f.setEstacionamiento(3);


        f.setBiciPuma(new ArrayList<BiciPuma>());
        f.getBiciPuma().add(bp.get(0));
        f.setPumabus(new ArrayList<Pumabus>());
        f.getPumabus().add(pb.get(6));
        
        f.setMetroBus(new ArrayList<Metrobus>());
        
        
        localService.guardaFacultad(f);
                
        ////CAMBIOOOO
         f = new Facultad();
        f.setNombreFac(facultadArray[2]);
        f.setEstacionamiento(4);


        f.setBiciPuma(new ArrayList<BiciPuma>());
        f.getBiciPuma().add(bp.get(3));
        f.setPumabus(new ArrayList<Pumabus>());
        f.getPumabus().add(pb.get(17));  
        f.getPumabus().add(pb.get(18));
        f.getPumabus().add(pb.get(24));

        
        f.setMetroBus(new ArrayList<Metrobus>());
        f.getMetro().add(cu);
        
        localService.guardaFacultad(f);
                ////CAMBIOOOO
         f = new Facultad();
        f.setNombreFac(facultadArray[3]);
        f.setEstacionamiento(2);


        f.setBiciPuma(new ArrayList<BiciPuma>());
        f.getBiciPuma().add(bp.get(4));
        f.setPumabus(new ArrayList<Pumabus>());
        f.getPumabus().add(pb.get(26));

        
        f.setMetroBus(new ArrayList<Metrobus>());
        f.getMetro().add(cu);
        
        localService.guardaFacultad(f);
                        ////CAMBIOOOO
         f = new Facultad();
        f.setNombreFac(facultadArray[4]);
        f.setEstacionamiento(0);


        f.setBiciPuma(new ArrayList<BiciPuma>());
        f.getBiciPuma().add(bp.get(5));
        f.setPumabus(new ArrayList<Pumabus>());
        f.getPumabus().add(pb.get(19));

        
        f.setMetroBus(new ArrayList<Metrobus>());
        
        localService.guardaFacultad(f);
                                ////CAMBIOOOO
         f = new Facultad();
        f.setNombreFac(facultadArray[5]);
        f.setEstacionamiento(4);


        f.setBiciPuma(new ArrayList<BiciPuma>());
        f.setPumabus(new ArrayList<Pumabus>());
        f.getPumabus().add(pb.get(10));

        
        f.setMetroBus(new ArrayList<Metrobus>());
        
        localService.guardaFacultad(f);

                        ////CAMBIOOOO
         f = new Facultad();
        f.setNombreFac(facultadArray[6]);
        f.setEstacionamiento(4);


        f.setBiciPuma(new ArrayList<BiciPuma>());
        f.setPumabus(new ArrayList<Pumabus>());
        f.getPumabus().add(pb.get(1));

        
        f.setMetroBus(new ArrayList<Metrobus>());
        
        localService.guardaFacultad(f);
                                ////CAMBIOOOO
         f = new Facultad();
        f.setNombreFac(facultadArray[7]);
        f.setEstacionamiento(2);


        f.setBiciPuma(new ArrayList<BiciPuma>());
        f.getBiciPuma().add(bp.get(8));
        f.setPumabus(new ArrayList<Pumabus>());
        f.getPumabus().add(pb.get(9));

        
        f.setMetroBus(new ArrayList<Metrobus>());
        
        localService.guardaFacultad(f);
                                        ////CAMBIOOOO
         f = new Facultad();
        f.setNombreFac(facultadArray[8]);
        f.setEstacionamiento(4);


        f.setBiciPuma(new ArrayList<BiciPuma>());
        f.getBiciPuma().add(bp.get(9));
        f.setPumabus(new ArrayList<Pumabus>());
        f.getPumabus().add(pb.get(5));

        
        f.setMetroBus(new ArrayList<Metrobus>());
        
        localService.guardaFacultad(f);
                                      ////CAMBIOOOO
         f = new Facultad();
        f.setNombreFac(facultadArray[9]);
        f.setEstacionamiento(4);


        f.setBiciPuma(new ArrayList<BiciPuma>());
        f.getBiciPuma().add(bp.get(10));
        f.setPumabus(new ArrayList<Pumabus>());
        f.getPumabus().add(pb.get(13));

        f.getMetro().add(copilco);
        
        f.setMetroBus(new ArrayList<Metrobus>());
        
        localService.guardaFacultad(f);
                                       ////CAMBIOOOO
         f = new Facultad();
        f.setNombreFac(facultadArray[10]);
        f.setEstacionamiento(4);


        f.setBiciPuma(new ArrayList<BiciPuma>());
        //f.getBiciPuma().add(bp.get(10));
        f.setPumabus(new ArrayList<Pumabus>());
        f.getPumabus().add(pb.get(14));

        f.getMetro().add(copilco);
        
        f.setMetroBus(new ArrayList<Metrobus>());
        
        localService.guardaFacultad(f);
                        ////CAMBIOOOO
         f = new Facultad();
        f.setNombreFac(facultadArray[11]);
        f.setEstacionamiento(4);


        f.setBiciPuma(new ArrayList<BiciPuma>());
        //f.getBiciPuma().add(bp.get(10));
        f.setPumabus(new ArrayList<Pumabus>());
        f.getPumabus().add(pb.get(12));

        //f.getMetro().add(copilco);
        
        f.setMetroBus(new ArrayList<Metrobus>());
        
        localService.guardaFacultad(f);
        ////CAMBIOOOO
         f = new Facultad();
        f.setNombreFac(facultadArray[12]);
        f.setEstacionamiento(4);


        f.setBiciPuma(new ArrayList<BiciPuma>());
        //f.getBiciPuma().add(bp.get(10));
        f.setPumabus(new ArrayList<Pumabus>());
        f.getPumabus().add(pb.get(8));

        //f.getMetro().add(copilco);
        
        f.setMetroBus(new ArrayList<Metrobus>());
        f.getMetroBus().add(mb.get(2));
        
        localService.guardaFacultad(f);
                ////CAMBIOOOO
         f = new Facultad();
        f.setNombreFac(facultadArray[13]);
        f.setEstacionamiento(1);


        f.setBiciPuma(new ArrayList<BiciPuma>());
        //f.getBiciPuma().add(bp.get(10));
        f.setPumabus(new ArrayList<Pumabus>());
        f.getPumabus().add(pb.get(20));

        //f.getMetro().add(copilco);
        
        f.setMetroBus(new ArrayList<Metrobus>());
        f.getMetroBus().add(mb.get(1));
        
        localService.guardaFacultad(f);
                        ////CAMBIOOOO
         f = new Facultad();
        f.setNombreFac(facultadArray[14]);
        f.setEstacionamiento(4);


        f.setBiciPuma(new ArrayList<BiciPuma>());
        f.getBiciPuma().add(bp.get(11));
        f.setPumabus(new ArrayList<Pumabus>());
        f.getPumabus().add(pb.get(3));

        //f.getMetro().add(copilco);
        
        f.setMetroBus(new ArrayList<Metrobus>());
        //f.getMetroBus().add(mb.get(1));
        
        localService.guardaFacultad(f);
                                ////CAMBIOOOO
         f = new Facultad();
        f.setNombreFac(facultadArray[15]);
        f.setEstacionamiento(4);


        f.setBiciPuma(new ArrayList<BiciPuma>());
        //f.getBiciPuma().add(bp.get(11));
        f.setPumabus(new ArrayList<Pumabus>());
        f.getPumabus().add(pb.get(28));
        f.getPumabus().add(pb.get(29));

        //f.getMetro().add(copilco);
        
        f.setMetroBus(new ArrayList<Metrobus>());
        f.getMetroBus().add(mb.get(0));
        
        localService.guardaFacultad(f);
        
        
        
        facultades = localService.findAllFacultades();
    }
    
}
