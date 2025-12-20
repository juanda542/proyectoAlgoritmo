package proyectoalgoritmo;

import java.util.*;

public class GrafoDirigido {

    private final Map<Integer, Paradero> paraderos = new HashMap<>();
    private final Map<Paradero, List<Arco>> adyacencia = new HashMap<>();

    public void agregarParadero(Paradero p) {
        paraderos.put(p.getId(), p);
        adyacencia.putIfAbsent(p, new ArrayList<>());
    }

    public void agregarArco(int idOrigen, int idDestino, double peso) {
        Paradero o = paraderos.get(idOrigen);
        Paradero d = paraderos.get(idDestino);
        if (o != null && d != null) {
            adyacencia.get(o).add(new Arco(o, d, peso));
        }
    }

    public void imprimirGrafo() {
        System.out.println("=== Grafo ===");
        for (Paradero p : adyacencia.keySet()) {
            System.out.println("Desde " + p + ":");
            for (Arco a : adyacencia.get(p)) {
                System.out.println("  " + a);
            }
        }
    }

    // DIJKSTRA: devuelve Ruta con arcos y costo
    public Ruta dijkstra(int idOrigen, int idDestino) {
        Paradero origen = paraderos.get(idOrigen);
        Paradero destino = paraderos.get(idDestino);
        if (origen == null || destino == null) {
            return null;
        }

        Map<Paradero, Double> dist = new HashMap<>();
        Map<Paradero, Paradero> prev = new HashMap<>();
        PriorityQueue<Paradero> pq = new PriorityQueue<>(Comparator.comparingDouble(p -> dist.getOrDefault(p, Double.POSITIVE_INFINITY)));

        for (Paradero p : adyacencia.keySet()) {
            dist.put(p, Double.POSITIVE_INFINITY);
            prev.put(p, null);
        }
        dist.put(origen, 0.0);
        pq.add(origen);

        while (!pq.isEmpty()) {
            Paradero u = pq.poll();
            double du = dist.get(u);
            if (du == Double.POSITIVE_INFINITY) {
                break;
            }
            if (u.equals(destino)) {
                break;
            }

            for (Arco arco : adyacencia.getOrDefault(u, Collections.emptyList())) {
                Paradero v = arco.getDestino();
                double alt = du + arco.getPeso();
                if (alt < dist.get(v)) {
                    dist.put(v, alt);
                    prev.put(v, u);
                    pq.remove(v);
                    pq.add(v);
                }
            }
        }

        if (prev.get(destino) == null && !origen.equals(destino)) {
            return null;
        }

        // Reconstruir lista de paraderos
        LinkedList<Paradero> pathNodos = new LinkedList<>();
        Paradero step = destino;
        while (step != null) {
            pathNodos.addFirst(step);
            step = prev.get(step);
        }

        // CONVERSIÓN CRÍTICA: De Paraderos a Arcos para que el Bus funcione
        List<Arco> pathArcos = new ArrayList<>();
        for (int i = 0; i < pathNodos.size() - 1; i++) {
            Paradero u = pathNodos.get(i);
            Paradero v = pathNodos.get(i + 1);
            for (Arco a : adyacencia.get(u)) {
                if (a.getDestino().equals(v)) {
                    pathArcos.add(a);
                    break;
                }
            }
        }

        return new Ruta(pathArcos, dist.get(destino));
    }

    public List<Ruta> rutasAlternativas(int idOrigen, int idDestino, int k) {
        List<Ruta> resultados = new ArrayList<>();
        Ruta principal = dijkstra(idOrigen, idDestino);
        if (principal == null) {
            return resultados;
        }
        resultados.add(principal);

        Set<String> evitados = new HashSet<>();

        for (int i = 0; i < k - 1; i++) {
            Ruta base = resultados.get(i);
            List<Arco> arcosBase = base.getArcos();
            boolean encontrada = false;

            for (int j = 0; j < arcosBase.size() && !encontrada; j++) {
                Arco arcoARemover = arcosBase.get(j);
                Paradero a = arcoARemover.getOrigen();
                Paradero b = arcoARemover.getDestino();

                String key = a.getId() + "->" + b.getId();
                if (evitados.contains(key)) {
                    continue;
                }

                List<Arco> listaAdy = adyacencia.get(a);
                listaAdy.remove(arcoARemover);

                Ruta alternativa = dijkstra(idOrigen, idDestino);
                listaAdy.add(arcoARemover); // Restaurar

                evitados.add(key);

                if (alternativa != null) {
                    boolean dup = resultados.stream().anyMatch(r -> r.getArcos().equals(alternativa.getArcos()));
                    if (!dup) {
                        resultados.add(alternativa);
                        encontrada = true;
                    }
                }
            }
            if (!encontrada) {
                break;
            }
        }
        return resultados;
    }

    public boolean existeConexion(int idOrigen, int idDestino) {
        return dijkstra(idOrigen, idDestino) != null;
    }

    public Paradero getParadero(int id) {
        return paraderos.get(id);
    }

    public java.util.Collection<Paradero> getVertices() {
        return paraderos.values();
    }

    public List<Arco> getAdyacentes(Paradero p) {
        return adyacencia.getOrDefault(p, new ArrayList<>());
    }

    public int getSize() {
        return paraderos.size();
    }

    public Ruta calcularRuta(int idO, int idD) {
        return dijkstra(idO, idD);
    }
}