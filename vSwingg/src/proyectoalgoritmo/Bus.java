package proyectoalgoritmo;



import java.awt.Point;

public class Bus {
    int id;
    Paradero pOrigen, pDestino;
    Ruta rutaIda, rutaVuelta, rutaActual;
    int indiceArco = 0;
    Arco arcoActual;
    double tiempoRestanteArco = 0, tiempoSobrante = 0;

    public Bus(int id, Paradero o, Paradero d, GrafoDirigido g) {
        this.id = id;
        this.pOrigen = o;
        this.pDestino = d;
        this.rutaIda = g.dijkstra(o.getId(), d.getId());
        this.rutaVuelta = g.dijkstra(d.getId(), o.getId());
        this.rutaActual = rutaIda;
        
        // Inicializamos el primer arco
        if (rutaActual != null && !rutaActual.getArcos().isEmpty()) {
            this.arcoActual = rutaActual.getArcoIndex(0);
            this.tiempoRestanteArco = arcoActual.getPeso();
        }
    }

    public void avanzar10Min(GrafoDirigido g) {
        double tTotal = 10.0 + tiempoSobrante;
        tiempoSobrante = 0;
        
        if (arcoActual == null) return;

        if (tiempoRestanteArco > tTotal) {
            tiempoRestanteArco -= tTotal;
        } else {
            tiempoSobrante = tTotal - tiempoRestanteArco;
            indiceArco++;
            
            if (indiceArco < rutaActual.getArcos().size()) {
                arcoActual = rutaActual.getArcoIndex(indiceArco);
                tiempoRestanteArco = arcoActual.getPeso();
                avanzar10Min(g); // Recursión para usar el tiempo sobrante en el siguiente arco
            } else { 
                // Llegó al final de la ruta, cambia de sentido (Retorno)
                rutaActual = (rutaActual == rutaIda) ? rutaVuelta : rutaIda;
                indiceArco = 0;
                arcoActual = (rutaActual != null) ? rutaActual.getArcoIndex(0) : null;
                tiempoRestanteArco = (arcoActual != null) ? arcoActual.getPeso() : 0;
            }
        }
    }

    // Este método le dice a Swing en qué coordenadas X, Y dibujar el bus
    public Point getPosicionActual() {
        if (arcoActual == null) {
            return new Point(pOrigen.getX(), pOrigen.getY());
        }
        
        // Calculamos el porcentaje de avance en el arco actual
        double porcentaje = 1.0 - (tiempoRestanteArco / arcoActual.getPeso());
        
        int x = (int) (arcoActual.getOrigen().getX() + (arcoActual.getDestino().getX() - arcoActual.getOrigen().getX()) * porcentaje);
        int y = (int) (arcoActual.getOrigen().getY() + (arcoActual.getDestino().getY() - arcoActual.getOrigen().getY()) * porcentaje);
        
        return new Point(x, y);
    }
}