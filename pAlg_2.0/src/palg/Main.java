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

        //1️⃣ Crear grafo
        GrafoDirigido grafo = new GrafoDirigido();
        Paradero a = new Paradero(0, "A", 100, 100);
        Paradero b = new Paradero(1, "B", 300, 100);
        Paradero c = new Paradero(2, "C", 200, 300);
        Paradero d = new Paradero(3, "D", 500, 150);
        Paradero e = new Paradero(4, "E", 400, 300);
        Paradero f = new Paradero(5, "F", 150, 400);


        grafo.agregarParadero(a);
        grafo.agregarParadero(b);
        grafo.agregarParadero(c);
        grafo.agregarParadero(d);
        grafo.agregarParadero(e);
        grafo.agregarParadero(f);
        
        
            
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
        
        // crear ventana
        JFrame frame = new JFrame("Simulación de Transporte Urbano");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout()); // primero layout

        
        // Crear vista
        GraphView view = new GraphView(grafo);
        view.setBuses(buses);
        frame.add(view, BorderLayout.CENTER);
     
        // Panel y botón
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnSimulacion = new JButton("Detener");
        btnSimulacion.setFocusable(false);
        panelBoton.add(btnSimulacion);
        frame.add(panelBoton, BorderLayout.SOUTH); // botón abajo centrado

        frame.setVisible(true); // al final
        
        
        // sTimer para simular movimient
        javax.swing.Timer timer = new javax.swing.Timer(1000, ev -> { // cada segundo = 10 minutos
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
