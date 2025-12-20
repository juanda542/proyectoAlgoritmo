package proyectoalgoritmo;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Ejecutar en el hilo de Swing
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("UTA - Simulador de Buses (Swing)");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);

            // 1. Configurar Grafo
            GrafoDirigido ciudad = new GrafoDirigido();
            Paradero p1 = new Paradero(1, "Terminal", 100, 100);
            Paradero p2 = new Paradero(2, "Saucache", 500, 400);
            ciudad.agregarParadero(p1);
            ciudad.agregarParadero(p2);
            ciudad.agregarArco(1, 2, 30.0);
            ciudad.agregarArco(2, 1, 30.0);

            // 2. Configurar Buses
            List<Bus> flota = new ArrayList<>();
            Bus b1 = new Bus(101, p1, p2, ciudad);
            flota.add(b1);

            // 3. Crear Panel del Mapa
            GraphView panelMapa = new GraphView(ciudad, flota);

            // 4. Botón de simulación
            JButton btnAvanzar = new JButton("Avanzar 10 Minutos");
            btnAvanzar.addActionListener(e -> {
                for (Bus b : flota) b.avanzar10Min(ciudad);
                panelMapa.repaint(); // Redibuja el mapa con las nuevas posiciones
            });

            // 5. Organizar Layout
            frame.setLayout(new BorderLayout());
            frame.add(panelMapa, BorderLayout.CENTER);
            frame.add(btnAvanzar, BorderLayout.SOUTH);

            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}