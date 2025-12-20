package proyectoalgoritmo;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Ruta de transporte urbano");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 750);

            // 1. Configurar Grafo (El Mapa de la Ciudad)
            GrafoDirigido ciudad = new GrafoDirigido();

            // Definimos paraderos (ID, Nombre, X, Y)
            Paradero p1 = new Paradero(1, "Puerto / Aduana", 80, 500);
            Paradero p2 = new Paradero(2, "Plaza Colón", 180, 420);
            Paradero p3 = new Paradero(3, "Terminal Rodoviario", 120, 150);
            Paradero p4 = new Paradero(4, "Campus Saucache (UTA)", 480, 380);
            Paradero p5 = new Paradero(5, "Entrada Azapa", 850, 500);
            Paradero p6 = new Paradero(6, "Terminal Asoagro", 850, 200);
            Paradero p7 = new Paradero(7, "Rotonda Tucapel", 500, 180);
            Paradero p8 = new Paradero(8, "Playa Chinchorro", 550, 50);

            // Agregar paraderos al sistema
            Paradero[] todos = {p1, p2, p3, p4, p5, p6, p7, p8};
            for (Paradero p : todos) ciudad.agregarParadero(p);

            // 2. Definir las calles (Conexiones Ida y Vuelta)
            // conectar(grafo, id_origen, id_destino, tiempo_minutos)
            conectar(ciudad, 1, 2, 5);   // Puerto <-> Plaza
            conectar(ciudad, 2, 3, 15);  // Plaza <-> Terminal
            conectar(ciudad, 2, 4, 12);  // Plaza <-> Saucache
            conectar(ciudad, 3, 7, 10);  // Terminal <-> Rotonda
            conectar(ciudad, 7, 8, 8);   // Rotonda <-> Chinchorro
            conectar(ciudad, 7, 4, 11);  // Rotonda <-> Saucache
            conectar(ciudad, 4, 5, 18);  // Saucache <-> Azapa
            conectar(ciudad, 5, 6, 12);  // Azapa <-> Asoagro
            conectar(ciudad, 6, 7, 14);  // Asoagro <-> Rotonda

            // 3. Crear la Flota de Buses con diferentes rutas
            List<Bus> flota = new ArrayList<>();
            
            // Bus 1: Línea 7 (Puerto hasta Asoagro)
            flota.add(new Bus(7, p1, p6, ciudad));
            
            // Bus 2: Línea 12 (Chinchorro hasta Azapa)
            flota.add(new Bus(12, p8, p5, ciudad));
            
            // Bus 3: Línea 2 (Saucache hasta Terminal Rodoviario)
            flota.add(new Bus(2, p4, p3, ciudad));
            
            // Bus 4: Línea 10 (Directo Asoagro al Centro)
            flota.add(new Bus(10, p6, p2, ciudad));

            // 4. Panel Visual
            GraphView panelMapa = new GraphView(ciudad, flota);
            
            // 5. Botón de simulación
            JButton btnAvanzar = new JButton("Simular +10 Minutos");
            btnAvanzar.setPreferredSize(new Dimension(200, 50));
            btnAvanzar.setFont(new Font("Arial", Font.BOLD, 14));
            
            btnAvanzar.addActionListener(e -> {
                for (Bus b : flota) {
                    b.avanzar10Min(ciudad);
                }
                panelMapa.repaint(); // Refresca la vista para ver el movimiento
            });

            // 6. Armar la Ventana
            frame.setLayout(new BorderLayout());
            frame.add(panelMapa, BorderLayout.CENTER);
            frame.add(btnAvanzar, BorderLayout.SOUTH);

            frame.setLocationRelativeTo(null); // Centrar en pantalla
            frame.setVisible(true);
        });
    }

    /**
     * Método útil para crear calles de doble sentido en un solo paso
     */
    private static void conectar(GrafoDirigido g, int id1, int id2, double tiempo) {
        g.agregarArco(id1, id2, tiempo);
        g.agregarArco(id2, id1, tiempo);
    }
}