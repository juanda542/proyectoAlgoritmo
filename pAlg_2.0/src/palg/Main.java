/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package palg;

import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        // Se crea el mapa id nombre cordenadas x y
        GrafoDirigido grafo = new GrafoDirigido();
        Paradero a = new Paradero(0, "A", 100, 100);
        Paradero b = new Paradero(1, "B", 300, 100);
        Paradero c = new Paradero(2, "C", 200, 300);
        Paradero d = new Paradero(3, "D", 500, 150);
        Paradero e = new Paradero(4, "E", 400, 300);
        Paradero f = new Paradero(5, "F", 150, 400);

        // se añaden los paraderos
        grafo.agregarParadero(a);
        grafo.agregarParadero(b);
        grafo.agregarParadero(c);
        grafo.agregarParadero(d);
        grafo.agregarParadero(e);
        grafo.agregarParadero(f);
        
        
        // se añaden conexiones
        grafo.agregarArco(a.getId(), b.getId(), 10);
        grafo.agregarArco(a.getId(), c.getId(), 20);
        grafo.agregarArco(a.getId(), f.getId(), 25);

        grafo.agregarArco(b.getId(), c.getId(), 15);
        grafo.agregarArco(b.getId(), d.getId(), 20);

        grafo.agregarArco(c.getId(), e.getId(), 10);
        grafo.agregarArco(c.getId(), f.getId(), 15);

        grafo.agregarArco(d.getId(), e.getId(), 12);
        grafo.agregarArco(d.getId(), b.getId(), 18);

        grafo.agregarArco(e.getId(), f.getId(), 10);
        grafo.agregarArco(f.getId(), a.getId(), 30);
        
        
        // crear buses
        List<Bus> buses = new ArrayList<>();

        Bus bus1 = new Bus(1, a, c, grafo.dijkstra(a.getId(), c.getId()), grafo.dijkstra(c.getId(), a.getId()));
        buses.add(bus1);
        
        Bus bus2 = new Bus(2, b, f, grafo.dijkstra(b.getId(), f.getId()), grafo.dijkstra(f.getId(), b.getId()));
        bus2.iniciarRuta();
        buses.add(bus2);
        
        Bus bus3 = new Bus(3, c, d, grafo.dijkstra(c.getId(), d.getId()), grafo.dijkstra(d.getId(), c.getId()));
        bus3.iniciarRuta();
        buses.add(bus3);
        //Ahora se anhade y se inicia la ruta de cada bus
        for(Bus i: buses) {
            i.iniciarRuta();
        }
        //bus1.iniciarRuta();
        
        // crear ventana simulacion
        JFrame frame = new JFrame("Simulación de Transporte Urbano");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.setLayout(new BorderLayout()); // primero layout
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);
      
        // Panel donde se dibuja el grafo y buses
    
        GraphView view = new GraphView(grafo);
        view.setBuses(buses);
        frame.add(view, BorderLayout.CENTER);
        
        // Panel y botón reanudar y detener
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnSimulacion = new JButton("Detener");
        btnSimulacion.setFocusable(false);
        panelBoton.add(btnSimulacion);
        frame.add(panelBoton, BorderLayout.SOUTH); 

        JButton btnConsultar = new JButton("Consultar ruta");
        btnConsultar.setFocusable(false);
        panelBoton.add(btnConsultar);
        // Cuando se aprete el boton
        btnConsultar.addActionListener(ev -> {
         // se crea un panel de tamaño
         JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
         
         // cajas que reciben texto
         JTextField eOrigen = new JTextField();
         JTextField eDestino = new JTextField();
         
         // Se añade al pane etiqueta que dice paradero origen y estino y los cuadro que reciben texto
         panel.add(new JLabel("Paradero origen:"));
         panel.add(eOrigen);
         panel.add(new JLabel("Paradero destino:"));
         panel.add(eDestino);

            // abre la ventana
         int result = JOptionPane.showConfirmDialog(frame, panel, 
            "Consultar Ruta", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

    if (result == JOptionPane.OK_OPTION) {
        String origenStr = eOrigen.getText().trim();
        String destinoStr = eDestino.getText().trim();

        // Busca el paradero de origen
        Paradero origen = null;
        for (Paradero p : grafo.getParaderos()) {
            if (p.getNombre().equalsIgnoreCase(origenStr)) {
                origen = p;
                break;
            }
        }
            
        // Busca el paradero destino
        Paradero destino = null;
        for (Paradero p : grafo.getParaderos()) {
            if (p.getNombre().equalsIgnoreCase(destinoStr)) {
                destino = p;
                break;
            }
        }
        
        // Si es que existen los paraderos
        if (origen != null && destino != null) {
            List<Ruta> rutas = grafo.rutasAlternativas(origen.getId(), destino.getId(), 3);
            // Si es que rutas no esta vacio osea
            if (!rutas.isEmpty()) {
                // si es que hay conexion se busca la ruta mas rapida
                StringBuilder sb = new StringBuilder();
                sb.append("Camino más corto:\n").append(rutas.get(0)).append("\n\n");
        // imprimir hasta 2 rutas ya     
        for (int i = 1; i < rutas.size() && i <= 2; i++) {
                sb.append("Alternativa ").append(i).append(":\n").append(rutas.get(i)).append("\n\n");
            }
                JOptionPane.showMessageDialog(frame, sb.toString());
                } else {
                    JOptionPane.showMessageDialog(frame, "No hay ruta entre esos paraderos.");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Paradero origen o destino no válido.");
                }
            }
        });
        
        
        
        frame.setVisible(true); // al final
        
        
        // sTimer para simular movimient
        javax.swing.Timer timer = new javax.swing.Timer(200, ev -> { // cada segundo = 10 minutos
        for (Bus h : buses) {
            //Si el bus termina el recorrido, se inicia su ruta de nuevo
            if (h.avanzar10Min()) {
                h.iniciarRuta();
            }
            //h.avanzar10Min();
        }
        view.setBuses(buses); // ahora sí actualiza la vista
        });
        timer.start();
        
        btnSimulacion.addActionListener(ev -> {
    if (timer.isRunning()) {
        timer.stop();
        btnSimulacion.setText("Reanudar");
    } else {
        timer.start();
        btnSimulacion.setText("Detener");
    }
    });
    }
    
    
}
