package palg;

/**
 * Representa un arco dirigido entre dos paraderos.
 * El peso representa el tiempo en minutos para recorrer la calle.
 * 
 */
public class Arco {
    private final Paradero origen;
    private final Paradero destino;
    private final int peso; // usar double permite tiempos no enteros si fuera necesario

    /**
     * Crea un arco dirigido desde 'origen' hacia 'destino' con un peso.
     * @param origen paradero origen (nodo)
     * @param destino paradero destino (nodo)
     * @param peso tiempo/costo del arco (>=0)
     */
    public Arco(Paradero origen, Paradero destino, int peso) {
        if (peso < 0) throw new IllegalArgumentException("El peso no puede ser negativo.");
        this.origen = origen;
        this.destino = destino;
        this.peso = peso;
    }

    public Paradero getOrigen() {
        return origen;
    }

    public Paradero getDestino() {
        return destino;
    }

    public int getPeso() {
        return peso;
    }

    @Override
    public String toString() {
        return origen.getNombre() + " -> " + destino.getNombre() + " (" + peso + "min)";
    }
}
