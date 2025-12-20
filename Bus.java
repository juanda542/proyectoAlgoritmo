/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoaed;

/**
 *
 * @author PC GAMER
 */
public class Bus {
    int id;
    
    Paradero paraderoOrigen;
    Paradero paraderoLlegada;
    Paradero paraderoActual;
    int indiceActual = 0;
    
    Arco arcoActual;
    Ruta rutaIda;
    Ruta rutaVuelta;
    Ruta rutaActual;
    
    int tiempoAcumuladoMin = 0;
    int tiempoRestanteArco = 0;
    
    int tiempoSobrante;
    
    public Bus(int id, Paradero paraderoOrigen, Paradero paraderoLlegada){
        this.id = id;
        this.paraderoOrigen = paraderoOrigen;
        this.paraderoLlegada = paraderoLlegada;
        this.paraderoActual = paraderoOrigen;
        
         //NECESITO ESTAs FUNCIONES
        this.rutaIda = new Ruta(paraderoOrigen, paraderoLlegada);
        this.rutaVuelta = new Ruta(paraderoLlegada, paraderoOrigen);
    }
    
    public boolean iniciarRuta() {
        if (paraderoActual.equals(paraderoOrigen)) {
            rutaActual = rutaIda;
        }
        
        else if (paraderoActual.equals(paraderoLlegada)) {
            rutaActual = rutaVuelta;
        }
        
        else {
            return false;
        }
        
        tiempoAcumuladoMin = 0;
        
        indiceActual = 0;
        arcoActual = rutaActual.getNodos().get(0);
        
        tiempoRestanteArco = arcoActual.getPeso();
        return true;
    }
    
    public boolean avanzar10Min() {
        tiempoAcumuladoMin += 10;
        int tiempoTotal = tiempoSobrante + 10;
        
        if (tiempoRestanteArco > tiempoTotal) { //Bus no llega a siguente paradero
            
            tiempoRestanteArco -= tiempoTotal;
            tiempoSobrante = 0;
            
            paraderoActual = null;
            arcoActual = rutaActual.getArcoIndex(indiceActual);
            return false;
        }
        
        else if (tiempoRestanteArco < tiempoTotal) { //Bus se pasa del siguente paradero
            
            indiceActual += 1;
            if (indiceActual == rutaActual.getNodos().size() - 1) {   //Llega a ultimo paradero de la ruta
                paraderoActual = rutaActual.getArcoIndex(indiceActual).getParadero(0);
                arcoActual = null;
                tiempoSobrante =  tiempoTotal - tiempoRestanteArco;
                tiempoRestanteArco = 0;
                return true;
            }
            else{
                paraderoActual = null;
                arcoActual = rutaActual.getArcoIndex(indiceActual);
                
                tiempoSobrante = tiempoTotal - tiempoRestanteArco;
                tiempoRestanteArco = arcoActual.getPeso() - 
            }
            paraderoActual = null;
            arcoActual = rutaActual.getArcoIndex(indiceActual);
            
            tiempoSobrante = tiempoTotal - tiempoRestanteArco;
            tiempoRestanteArco = arcoActual.getPeso();
            
        }
        else { //Bus llega justamente al paradero
            indiceActual += 1;
            paraderoActual = rutaActual.getArcoIndex(indiceActual).getParadero(0); //Nececito getParadero
            
        }
    }
}