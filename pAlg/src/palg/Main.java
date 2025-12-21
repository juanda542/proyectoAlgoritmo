/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package palg;

import java.util.*;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        // 1️⃣ Crear grafo
        GrafoDirigido grafo = new GrafoDirigido();

        Paradero a = new Paradero(0, "A", 100, 100);
        Paradero b = new Paradero(1, "B", 300, 100);
        Paradero c = new Paradero(2, "C", 200, 300);

        grafo.agregarParadero(a);
        grafo.agregarParadero(b);
        grafo.agregarParadero(c);

        grafo.agregarArco(a.getId(), b.getId(), 10);
        grafo.agregarArco(b.getId(), c.getId(), 15);
        grafo.agregarArco(a.getId(), c.getId(), 20);

        // crear buses
        List<Bus> buses = new ArrayList<>();

        Bus bus1 = new Bus(1, a, c, grafo.dijkstra(a.getId(), c.getId()), grafo.dijkstra(c.getId(), a.getId()));
        buses.add(bus1);
        //Ahora se anhade y se inicia la ruta de cada bus
        for(Bus i: buses) {
            i.iniciarRuta();
        }
        //bus1.iniciarRuta();
        
        
        // 2️⃣ Crear vista
        GraphView view = new GraphView(grafo);
        view.setBuses(buses); 
        // 3️⃣ Crear ventana
        JFrame frame = new JFrame("Transporte Urbano");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(view);
        frame.setSize(600, 500); // aquí defines el tamaño
        frame.setVisible(true);
        
        // sTimer para simular movimient
        new javax.swing.Timer(1000, e -> { // cada segundo = 10 minutos
        for (Bus h : buses) {
            //Si el bus termina el recorrido, se inicia su ruta de nuevo
            if (h.avanzar10Min()) {
                h.iniciarRuta();
            }
            //h.avanzar10Min();
        }
        view.setBuses(buses); // ahora sí actualiza la vista
        }
        ).start();
    }
    
}

