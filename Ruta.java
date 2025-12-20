/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoaed;
import java.util.List;
/**
 *
 * @author PC GAMER
 */
public class Ruta {
    List<Arco> arcos;
    int costoTotal;
    
    public Ruta(Paradero paraderoOrigen, Paradero paraderoLlegada){
        this.arcos = calcularRuta(paraderoOrigen, paraderoLlegada);
        this.costoTotal = 0;
        
        for (Arco a: arcos) {
            this.costoTotal += a.getPeso();
        }   
    }
    
    public List<Arco> getNodos() {
        return arcos;
    }
    
    public Arco getArcoIndex(int i) {
        return arcos.get(i);
    }
    
    public int getCostoTotal(){
        return costoTotal;
    }
    
/*    
    public String toString() {
        String st;
        for (int i = 0; i < arcos.size() - 1; i++) {
            Paradero paradero1 = arcos.get(i);
            Paradero paradero2 = arcos.get(i+1);
            
            
        }
        
        return st;
    }
}
