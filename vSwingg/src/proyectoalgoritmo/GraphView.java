package proyectoalgoritmo;

import javax.swing.*;
import java.awt.*;

public class GraphView extends JPanel {
    private GrafoDirigido g;
    private java.util.List<Bus> buses;

    public GraphView(GrafoDirigido g, java.util.List<Bus> buses) {
        this.g = g;
        this.buses = buses;
        this.setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g2d) {
        super.paintComponent(g2d);
        Graphics2D g2 = (Graphics2D) g2d;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 1. Dibujar Arcos (Calles)
        g2.setColor(Color.LIGHT_GRAY);
        for (Paradero p : g.getVertices()) {
            for (Arco a : g.getAdyacentes(p)) {
                g2.drawLine(p.getX(), p.getY(), a.getDestino().getX(), a.getDestino().getY());
            }
        }

        // 2. Dibujar Paraderos
        for (Paradero p : g.getVertices()) {
            g2.setColor(new Color(100, 149, 237)); // Azul claro
            g2.fillOval(p.getX() - 10, p.getY() - 10, 20, 20);
            g2.setColor(Color.BLACK);
            g2.drawOval(p.getX() - 10, p.getY() - 10, 20, 20);
            g2.drawString(p.getNombre(), p.getX() - 15, p.getY() - 15);
        }

        // 3. Dibujar Buses
        g2.setColor(Color.RED);
        for (Bus b : buses) {
            // Obtenemos la posición calculada por el bus
            Point pos = b.getPosicionActual(); 
            g2.fillOval(pos.x - 7, pos.y - 7, 14, 14);
            g2.drawString("Bus " + b.id, pos.x + 10, pos.y);
        }
    }
}