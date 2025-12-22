/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package palg;

import java.awt.Point;

/**
 *Representa los buses que recorren el grafo
 * 
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

    /**
     * Construye un bus con sus rutas de ida y vuelta ya calculadas.
     *
     * @param id identificador del bus
     * @param paraderoOrigen paradero de inicio del recorrido
     * @param paraderoLlegada paradero de término del recorrido
     * @param rutaIda ruta desde el origen hasta el destino
     * @param rutaVuelta ruta desde el destino hasta el origen
     */    
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

    /**
     * Inicia el recorrido del bus desde su paradero actual.
     * 
     * Si el bus se encuentra en el paradero de origen, comienza la ruta de ida.
     * Si se encuentra en el paradero de llegada, comienza la ruta de vuelta.
     *
     * @return true si la ruta se inició correctamente, false en caso contrario
     */
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
     * Avanza el estado del bus en un intervalo de 10 minutos de simulación.
     * 
     * El bus puede recorrer uno o más arcos dentro del mismo intervalo,
     * dependiendo del tiempo restante de cada tramo.
     *
     * @return true si el bus llegó al final de la ruta,
     * false si continúa en movimiento
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

    /**
     * @return id del bus
     */
    public int getId() {
        return id;
    }

    /**
     * @return paradero en el que se encuentra el bus
     */
    public Paradero getParaderoActual() {
        return paraderoActual;
    }

    /**
    *@return arco actual, o null si el bus está detenido en un paradero
    */
    public Arco getArcoActual() {
        return arcoActual;
    }

    /**
     * @return tiempo total del viaje
     */
    public int getTiempoAcumuladoMin() {
        return tiempoAcumuladoMin;
    }

    /**
     * 
     * @return true si esta en movimiento, false sino
     */
    public boolean isEnMovimiento() {
        return arcoActual != null;
    }
    
    
    // point es una clase que representa cordendas (x,y)
    /**
     * Obtiene la posicion actual del bus en el plano.
     * 
     * Si el bus se encuentra detenido en un paradero, retorna las coordenadas
     * del paradero. Si se encuentra en transito, interpola su posición entre
     * el paradero de origen y destino del arco actual.
     *
     * @return posición actual del bus como un punto (x, y)
     */
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
