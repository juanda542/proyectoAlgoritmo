/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package palg;

import java.util.List;
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

    public Bus(int id, Paradero paraderoOrigen, Paradero paraderoLlegada, List<Arco> rutaIda, List<Arco> rutaVuelta) {
        this.id = id;
        this.paraderoOrigen = paraderoOrigen;
        this.paraderoLlegada = paraderoLlegada;
        this.paraderoActual = paraderoOrigen;

        // Idealmente estas rutas las entrega el Grafo
        this.rutaIda = new Ruta(rutaIda);
        this.rutaVuelta = new Ruta(rutaVuelta);

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
                    arcoActual = null;
                    tiempoRestanteArco = 0;
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
}
