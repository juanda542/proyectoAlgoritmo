/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package palg;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
/**
 *
 * @author juand
 */
public class GraphView extends JPanel {
    
    private GrafoDirigido grafo; // grafo que va a leer para saber que dibujar
    private List<Bus> buses = new ArrayList<>();
    private BufferedImage imgBus;

    public GraphView(GrafoDirigido grafo){ 
        this.grafo = grafo;
        this.setBackground(Color.WHITE);
        // es obligatorio poner try porque si no java da error tipo te obliga a manejar la posibilidad de que no este el archivo
        try {
        imgBus = ImageIO.read(new File("bus1.png"));
        } catch (Exception ex) {
            throw new RuntimeException("No se pudo cargar la imagen del bus", ex);
        }
     }
       
    // actualiza la posicion de los buses cada vez que se mueven
    public void setBuses(List<Bus> buses) {
    this.buses = buses;
    repaint(); // redibuja con la posición actual de los buses
}
    
    /* Metodo de swing (No se puede cambiar el nombre pero se encarga de dibujar el panel(contenedor) donde se dibuja el grafo
     Graphics g es el que se encarga de dibujar todo
    */
    @Override
    protected void paintComponent(Graphics g){ 
        super.paintComponent(g); // Limpia el panel?
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Centro de la ventana
        int centroX = getWidth() / 2;
        int centroY = getHeight() / 2;
        
        int grafoCentroX = 300; 
        int grafoCentroY = 250;
        
        g2.translate(centroX - grafoCentroX, centroY - grafoCentroY);
        
        // Para cada paradero se piden sus cordenadas
        for (Paradero p : grafo.getParaderos()){ 
            int x1 = p.getX();
            int y1 = p.getY();
                
                
            // Para cada calle del paradero que estamos recorriendo, se obtiene su destino y cordenadas
           
            g2.setColor(new Color(200, 200, 200)); // gris suave
            g2.setStroke(new BasicStroke(3)); // grosor de línea
            
            for (Arco a : grafo.getArcosParadero(p)) {
                Paradero d = a.getDestino(); 
                int x2 = d.getX(); 
                int y2 = d.getY(); 
                
                g2.drawLine(x1, y1, x2, y2); 
            
            }
        }
        // Para cada paradero se obtiene su cordenada 
            
            for (Paradero p : grafo.getParaderos()) {
                int x = p.getX(); 
                int y = p.getY();
                
                // en esas cordendas se dibuja un circulo azul
                g2.setColor(new Color(41, 128, 185)); 
                g2.fillOval(x - 10, y - 10, 20, 20); // ()
                
                
                // y se escribe el nombre del paradero abajo del circulo
                g2.setColor(Color.BLACK); 
                g2.setFont(new Font("Arial", Font.BOLD, 12));
                g2.drawString(p.getNombre(), x - 15, y - 15);
        
        }
        for (Bus b : buses) {
            Point pos = b.getPosicionActual();
            g2.drawImage(imgBus, pos.x - 15, pos.y - 15, 30, 30, null);
            g2.setColor(Color.DARK_GRAY);
            g2.drawString("Bus " + b.getId(), pos.x - 15, pos.y + 25);
        }
        
    }
    
            
}


