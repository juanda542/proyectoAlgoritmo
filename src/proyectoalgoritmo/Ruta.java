/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoalgoritmo;

import java.util.List;

public class Ruta {
    private List<Arco> arcos;
    private double costoTotal;
    
    public Ruta(List<Arco> arcos,double costoTotal){
        this.arcos = arcos;
        this.costoTotal = costoTotal;
       
        }   

    public List<Arco> getNodos() {
        return arcos;
    }
    
    public Arco getArcoIndex(int i) {
        return arcos.get(i);
    }
    
    public double getCostoTotal(){
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
    } */
    
    public List<Arco> getArcos() { 
        return arcos; }
}