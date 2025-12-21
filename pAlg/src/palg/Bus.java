/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package palg;

import java.util.List;
import java.awt.Point;

/**
 *
 * @author PC GAMER
 */
public class Bus {

    private int id;

    private Paradero paraderoOrigen;
    private Paradero paraderoLlegada;
    private Paradero paraderoActual;

    private Ruta rutaIda;
    private Ruta rutaVuelta;
    private Ruta rutaActual;

    private Arco arcoActual;
    private int indiceActual;

    private int tiempoAcumuladoMin;
    private int tiempoRestanteArco;

        //Cambie constructor, se debe construir con OBJETOS RUTA, no LIST<ARCO>
    public Bus(int id, Paradero paraderoOrigen, Paradero paraderoLlegada, Ruta rutaIda, Ruta rutaVuelta) {
        this.id = id;
        this.paraderoOrigen = paraderoOrigen;
        this.paraderoLlegada = paraderoLlegada;
        this.paraderoActual = paraderoOrigen;
        this.rutaIda = rutaIda;
        this.rutaVuelta = rutaVuelta;
        this.arcoActual = null;
        this.indiceActual = 0;
        this.tiempoAcumuladoMin = 0;
        this.tiempoRestanteArco = 0;
    }

    public boolean iniciarRuta() {

        if (paraderoActual.equals(paraderoOrigen)) {
            rutaActual = rutaIda;
        } else if (paraderoActual.equals(paraderoLlegada)) {
            rutaActual = rutaVuelta;
        } else {
            return false;
        }

        if (rutaActual.getTramos().isEmpty()) {
            return false;
        }

        tiempoAcumuladoMin = 0;
        indiceActual = 0;

        arcoActual = rutaActual.getTramos().get(0);
        tiempoRestanteArco = arcoActual.getPeso();

        return true;
    }

    /**
     * @return true si llegó al final de la ruta, false si sigue en movimiento
     */
    public boolean avanzar10Min() {

        tiempoAcumuladoMin += 10;
        int tiempoDisp = 10;

        while (tiempoDisp > 0 && arcoActual != null) {

            if (tiempoRestanteArco > tiempoDisp) {
                tiempoRestanteArco -= tiempoDisp;
                tiempoDisp = 0;
            } else {
                tiempoDisp -= tiempoRestanteArco;

                paraderoActual = arcoActual.getDestino();
                indiceActual++;

                if (indiceActual >= rutaActual.getTramos().size()) {
                    return true;
                }

                arcoActual = rutaActual.getTramos().get(indiceActual);
                tiempoRestanteArco = arcoActual.getPeso();
            }
        }

        return false;
    }

    // Getters útiles
    public int getId() {
        return id;
    }

    public Paradero getParaderoActual() {
        return paraderoActual;
    }

    public Arco getArcoActual() {
        return arcoActual;
    }

    public int getTiempoAcumuladoMin() {
        return tiempoAcumuladoMin;
    }

    public boolean isEnMovimiento() {
        return arcoActual != null;
    }
    
    
    // point es una clase que representa cordendas (x,y)
    public Point getPosicionActual() {
    if (arcoActual == null) {
        return new Point(paraderoActual.getX(), paraderoActual.getY());
    }

    Paradero origen = arcoActual.getOrigen();
    Paradero destino = arcoActual.getDestino();
    double total = arcoActual.getPeso();
    double avanzado = total - tiempoRestanteArco;

    double ratio = avanzado / total;
    int x = (int) (origen.getX() + (destino.getX() - origen.getX()) * ratio);
    int y = (int) (origen.getY() + (destino.getY() - origen.getY()) * ratio);

    return new Point(x, y);
}
}

