/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package palg;

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

        // 2️⃣ Crear vista
        GraphView view = new GraphView(grafo);

        // 3️⃣ Crear ventana
        JFrame frame = new JFrame("Mapa de Transporte");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(view);
        frame.setSize(600, 500); // aquí defines el tamaño
        frame.setVisible(true);
    }
}
