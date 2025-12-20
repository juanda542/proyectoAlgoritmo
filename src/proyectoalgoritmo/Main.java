package proyectoalgoritmo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Inicialización del Grafo
        GrafoDirigido ciudad = new GrafoDirigido();

        // Paraderos (ID, Nombre, X, Y)
        Paradero p1 = new Paradero(1, "Terminal", 100, 100);
        Paradero p2 = new Paradero(2, "Saucache", 400, 300);
        
        ciudad.agregarParadero(p1);
        ciudad.agregarParadero(p2);
        ciudad.agregarArco(1, 2, 20.0);
        ciudad.agregarArco(2, 1, 20.0);

        // Crear Vista y Bus
        GraphView vista = new GraphView(ciudad);
        Bus bus = new Bus(101, p1, p2, ciudad);
        
        // Agregar el bus visualmente
        vista.getChildren().add(bus.getVista());

        // Botón de simulación
        Button btn = new Button("Avanzar 10 min");
        btn.setOnAction(e -> bus.avanzar10Min(ciudad));

        VBox root = new VBox(20, vista, btn);
        Scene scene = new Scene(root, 600, 500);
        
        primaryStage.setTitle("Simulador UTA - Proyecto Algoritmos");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}