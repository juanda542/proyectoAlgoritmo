package proyectoalgoritmo;

import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

public class Bus {
    int id;
    Paradero pOrigen, pDestino;
    Ruta rutaIda, rutaVuelta, rutaActual;
    int indiceArco = 0;
    Arco arcoActual;
    double tiempoRestanteArco = 0, tiempoSobrante = 0;
    Circle circulo;

    public Bus(int id, Paradero o, Paradero d, GrafoDirigido g) {
        this.id = id;
        this.pOrigen = o;
        this.pDestino = d;
        this.rutaIda = g.dijkstra(o.getId(), d.getId());
        this.rutaVuelta = g.dijkstra(d.getId(), o.getId());
        this.rutaActual = rutaIda;
        this.circulo = new Circle(o.getX(), o.getY(), 8, Color.RED);
        this.arcoActual = rutaActual.getArcoIndex(0);
        if (arcoActual != null) this.tiempoRestanteArco = arcoActual.getPeso();
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
                avanzar10Min(g); // Recursión para usar el tiempo sobrante
            } else { 
                // Llegó al final, cambia de sentido
                rutaActual = (rutaActual == rutaIda) ? rutaVuelta : rutaIda;
                indiceArco = 0;
                arcoActual = rutaActual.getArcoIndex(0);
                tiempoRestanteArco = (arcoActual != null) ? arcoActual.getPeso() : 0;
            }
        }
        actualizarPosicionGrafica();
    }

    private void actualizarPosicionGrafica() {
        if (arcoActual == null) return;
        double p = 1.0 - (tiempoRestanteArco / arcoActual.getPeso());
        double x = arcoActual.getOrigen().getX() + (arcoActual.getDestino().getX() - arcoActual.getOrigen().getX()) * p;
        double y = arcoActual.getOrigen().getY() + (arcoActual.getDestino().getY() - arcoActual.getOrigen().getY()) * p;
        circulo.setCenterX(x);
        circulo.setCenterY(y);
    }
    public Circle getVista() { 
        return circulo; }
}