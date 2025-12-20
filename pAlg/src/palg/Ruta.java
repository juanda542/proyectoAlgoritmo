/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package palg;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author PC GAMER
 */
public class Ruta {

    private  List<Arco> arcos;
    private int costoTotal;

    // La ruta YA viene calculada
    public Ruta(List<Arco> arcos) {
        this.arcos = new ArrayList<>(arcos); // copia defensiva

        int costo = 0;
        for (Arco a : arcos) {
            costo += a.getPeso();
        }
        this.costoTotal = costo;
    }

    public List<Arco> getTramos() {
        return Collections.unmodifiableList(arcos);
    }

    public Arco getArcoIndex(int i) {
        return arcos.get(i);
    }

    public double getCostoTotal() {
        return costoTotal;
    }

    public boolean estaVacia() {
        return arcos.isEmpty();
    }
}
