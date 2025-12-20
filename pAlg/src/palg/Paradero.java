package palg;
/**
 * Representa un paradero (nodo) en el grafo.
 * Se identifica de forma única por su id.
 */
public class Paradero {
    private final int id;
    private final String nombre;

    /**
     * Crea un Paradero.
     * @param id identificador único del paradero
     * @param nombre nombre descriptivo (p. ej. "Paradero A")
     */
    public Paradero(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    /** Retorna el id del paradero. */
    public int getId() {
        return id;
    }

    /** Retorna el nombre del paradero. */
    public String getNombre() {
        return nombre;
    }

    /**
     * toString legible para imprimir en consola.
     */
    @Override
    public String toString() {
        return nombre + " (ID:" + id + ")";
    }

    /**
     * HashCode y equals implementados solo por id.
     * Esto permite usar Paradero como clave en HashMap o Set.
     */
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Paradero)) return false;
        Paradero other = (Paradero) o;
        return this.id == other.id;
    }
}