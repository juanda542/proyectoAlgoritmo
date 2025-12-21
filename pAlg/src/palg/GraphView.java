/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package palg;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author juand
 */
public class GraphView extends JPanel {
    
    private GrafoDirigido grafo; // grafo que va a leer para saber que dibujar
    private List<Bus> buses = new ArrayList<>();

    public GraphView(GrafoDirigido grafo){ 
        this.grafo = grafo;
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
        // Para cada paradero se piden sus cordenadas
        for (Paradero p : grafo.getParaderos()){ 
            int x1 = p.getX();
            int y1 = p.getY();
                
                
            // Para cada calle del paradero que estamos recorriendo, se obtiene su destino y cordenadas
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
                g2.setColor(Color.BLUE); 
                g2.fillOval(x - 10, y - 10, 20, 20); // ()
                
                
                // y se escribe el nombre del paradero abajo del circulo
                g2.setColor(Color.BLACK); 
                g2.drawString(p.getNombre(), x - 15, y - 15);
        
        }
        for (Bus b : buses) {
            Point pos = b.getPosicionActual();
            g2.setColor(Color.RED);
            g2.fillOval(pos.x - 5, pos.y - 5, 10, 10); // círculo del bus
        }
        
    }
    
            
}


