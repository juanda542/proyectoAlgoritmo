package palg;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

    
/**
 * Representa una ruta dentro del grafo.
 *
 * Una ruta esta compuesta por una secuencia ordenada de arcos y
 * tiene asociado un costo total calculado a partir de sus tramos.
 * La ruta se asume previamente calculada por el grafo.
 */
public class Ruta {

    /** Lista ordenada de arcos que componen la ruta */
    private List<Arco> arcos;

    /** Costo total de la ruta */
    private int costoTotal;

    /**
     * Construye una ruta a partir de una lista de arcos ya calculada.
     * @param arcos lista de arcos de la ruta
     */
    public Ruta(List<Arco> arcos) {
        this.arcos = new ArrayList<>(arcos);

        int costo = 0;
        for (Arco a : arcos) {
            costo += a.getPeso();
        }
        this.costoTotal = costo;
    }

    /**
     * Retorna los tramos de la ruta.
     * @return lista inmodificable de arcos
     */
    public List<Arco> getTramos() {
        return Collections.unmodifiableList(arcos);
    }

    /**
     * Retorna el arco en una posicion especifica.
     * @param i indice del arco
     * @return arco correspondiente
     */
    public Arco getArcoIndex(int i) {
        return arcos.get(i);
    }

    /**
     * Retorna el costo total de la ruta.
     * @return costo total
     */
    public int getCostoTotal() {
        return costoTotal;
    }

    /**
     * Indica si la ruta no contiene arcos.
     * @return true si la ruta esta vacia
     */
    public boolean estaVacia() {
        return arcos.isEmpty();
    }

    /**
     * Retorna una representacion en texto de la ruta.
     * @return descripcion de la ruta
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Arco arco : arcos) {
            sb.append(arco.getOrigen().getNombre())
              .append("->")
              .append(arco.getDestino().getNombre())
              .append(" (")
              .append(arco.getPeso())
              .append(" min)\n");
        }
        return sb.toString();
    }
}

