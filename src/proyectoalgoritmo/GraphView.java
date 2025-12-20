package proyectoalgoritmo;

import javafx.scene.layout.Pane;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;

public class GraphView extends Pane {
    public GraphView(GrafoDirigido g) {
        // Dibujar Arcos
        for (Paradero p : g.getVertices()) {
            for (Arco a : g.getAdyacentes(p)) {
                Line l = new Line(p.getX(), p.getY(), a.getDestino().getX(), a.getDestino().getY());
                l.setStroke(Color.GRAY);
                getChildren().add(l);
            }
        }
        // Dibujar Paraderos
        for (Paradero p : g.getVertices()) {
            Circle c = new Circle(p.getX(), p.getY(), 12, Color.LIGHTBLUE);
            c.setStroke(Color.DARKBLUE);
            Text t = new Text(p.getX() - 10, p.getY() - 15, p.getNombre());
            getChildren().addAll(c, t);
        }
    }
}