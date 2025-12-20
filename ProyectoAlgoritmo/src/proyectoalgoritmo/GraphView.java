package proyectoalgoritmo;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.List;

public class GraphView extends JPanel {
    private GrafoDirigido g;
    private List<Bus> buses;
    private BufferedImage imgBus;

    public GraphView(GrafoDirigido g, List<Bus> buses) {
        this.g = g;
        this.buses = buses;
        this.setBackground(Color.WHITE);

        // Intentar cargar la imagen
        try {
            // Busca el archivo bus.png en la raíz del proyecto
            File archivoImagen = new File("bus1.png");
            if (archivoImagen.exists()) {
                imgBus = ImageIO.read(archivoImagen);    
            }
             else {
            System.err.println("LOG ERROR: No se encontró el archivo 'bus.png' en: " + archivoImagen.getAbsolutePath());
        }
            
        } catch (Exception e) {
            System.err.println("Error cargando imagen: " + e.getMessage());
            imgBus = null; // Si falla, queda en null y dibujaremos círculos
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        // Suavizar los dibujos (Antialiasing)
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 1. Dibujar las Calles (Arcos)
        g2.setColor(Color.LIGHT_GRAY);
        g2.setStroke(new BasicStroke(2));
        for (Paradero p : this.g.getVertices()) {
            for (Arco a : this.g.getAdyacentes(p)) {
                g2.drawLine(p.getX(), p.getY(), a.getDestino().getX(), a.getDestino().getY());
            }
        }

        // 2. Dibujar los Paraderos (Nodos)
        for (Paradero p : this.g.getVertices()) {
            g2.setColor(new Color(41, 128, 185)); // Azul elegante
            g2.fillOval(p.getX() - 8, p.getY() - 8, 16, 16);
            g2.setColor(Color.BLACK);
            g2.drawString(p.getNombre(), p.getX() + 10, p.getY() - 10);
        }

        // 3. Dibujar los Buses (Imagen o Círculo)
        for (Bus b : buses) {
            Point pos = b.getPosicionActual();
            if (imgBus != null) {
                // Dibujamos la imagen del bus (tamaño 30x30 centrado)
                g2.drawImage(imgBus, pos.x - 15, pos.y - 15, 30, 30, null);
            } else {
                // Si no hay imagen, dibujamos un círculo rojo por seguridad
                g2.setColor(Color.RED);
                g2.fillOval(pos.x - 8, pos.y - 8, 16, 16);
            }
            g2.setColor(Color.DARK_GRAY);
            g2.drawString("Bus " + b.id, pos.x - 15, pos.y + 25);
        }
    }
}