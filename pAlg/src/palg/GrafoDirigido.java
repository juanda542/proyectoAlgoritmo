package palg;

import java.util.*;



public class GrafoDirigido {
    private final Map<Integer, Paradero> paraderos;
    private final Map<Paradero, List<Arco>> adyacencia;

    public GrafoDirigido() {
        this.paraderos = new HashMap<>();
        this.adyacencia = new HashMap<>();
    }

    public void agregarParadero(Paradero p) {
        paraderos.putIfAbsent(p.getId(), p);
        adyacencia.putIfAbsent(p, new ArrayList<>());
    }

    public void agregarArco(int idOrigen, int idDestino, int peso) {
        Paradero o = paraderos.get(idOrigen);
        Paradero d = paraderos.get(idDestino);
        if (o == null || d == null) {
            throw new IllegalArgumentException("Paradero inexistente");
        }
        adyacencia.get(o).add(new Arco(o, d,peso));
    }

    public void imprimirGrafo() {
        System.out.println("=== Grafo ===");
        for (Paradero p : adyacencia.keySet()) {
            System.out.println("Desde " + p + ":");
            for (Arco a : adyacencia.get(p)) System.out.println("  " + a);
        }
    }

    // DIJKSTRA: devuelve Ruta con nodos y costo
    public Ruta dijkstra(int idOrigen, int idDestino) {
        Paradero origen = paraderos.get(idOrigen);
        Paradero destino = paraderos.get(idDestino);
        if (origen == null || destino == null) return null;

        Map<Paradero, Integer> dist = new HashMap<>();
        Map<Paradero, Paradero> prev = new HashMap<>();
        PriorityQueue<Paradero> pq = new PriorityQueue<>(Comparator.comparingInt(dist::get)); // cambie comparingInteger a comparingInt

        for (Paradero p : adyacencia.keySet()) {
            dist.put(p, Integer.MAX_VALUE); // Cambie POSITIVE_INFINITY (de double) a MAX_VALUE (de int)
            prev.put(p, null);
        }
        dist.put(origen, 0);

        pq.add(origen);

        while (!pq.isEmpty()) {
            Paradero u = pq.poll();
            int du = dist.get(u);
            if (du == Integer.MAX_VALUE) break;  // Cambie POSITIVE_INFINITY (de double) a MAX_VALUE (de int)
            if (u.equals(destino)) break;

            for (Arco arco : adyacencia.getOrDefault(u, Collections.emptyList())) {
                Paradero v = arco.getDestino();
                int alt = du + arco.getPeso();
                if (alt < dist.get(v)) {
                    dist.put(v, alt);
                    prev.put(v, u);
                    // update pq: remove+add or add duplicates (we'll add duplicates but comparator uses dist map)
                    pq.remove(v);
                    pq.add(v);
                }
            }
        }

        if (prev.get(destino) == null && !origen.equals(destino)) {
            return null; // no path
        }

        // reconstruir ruta
         LinkedList<Arco> arcosRuta = new LinkedList<>();
        Paradero actual = destino;

        while (prev.get(actual) != null) {
            Paradero anterior = prev.get(actual);

            // buscar el arco anterior -> actual
            for (Arco a : adyacencia.get(anterior)) {
                if (a.getDestino().equals(actual)) {
                    arcosRuta.addFirst(a);
                    break;
                }
            }

        actual = anterior;
        }
        Ruta ruta = new Ruta(arcosRuta);
        return ruta;
    }

    // Rutas alternativas (heurística simple): para k alternativas, eliminamos temporalmente cada arco
    // del camino encontrado y recalculamos Dijkstra. No es Yen completo pero devuelve alternativas.
    public List<Ruta> rutasAlternativas(int idOrigen, int idDestino, int k) {
        List<Ruta> resultados = new ArrayList<>();
        Ruta principal = dijkstra(idOrigen, idDestino);
        if (principal == null) return resultados;
        resultados.add(principal);

        Set<String> evitados = new HashSet<>(); // "oId->dId" form to avoid same removal

        for (int i = 0; i < k - 1; i++) {
            Ruta base = resultados.get(i);
            List<Arco> arcos = base.getTramos();
            boolean encontrada = false;
            for (int j = 0; j < arcos.size() && !encontrada; j++) {

                Arco arcoActual = arcos.get(j);
                Paradero a = arcoActual.getOrigen();
                Paradero b = arcoActual.getDestino();

                String key = a.getId() + "->" + b.getId();
                if (evitados.contains(key)){ 
                    continue;
                }

                // eliminar temporalmente arco a->b
                List<Arco> lista = adyacencia.get(a);
                Arco removido = null;
                for (Iterator<Arco> it = lista.iterator(); it.hasNext();) {
                    Arco ar = it.next();
                    if (ar.equals(arcoActual)) {
                        removido = ar;
                        it.remove();
                        break;
                    }                    
                }

                Ruta alternativa = dijkstra(idOrigen, idDestino);
                // restaurar arco
                if (removido != null) {
                    adyacencia.get(a).add(removido);
                }                

                evitados.add(key);

                if (alternativa != null) {
                    // evitar duplicados muy similares
                    boolean dup = resultados.stream().anyMatch(r -> r.getTramos().equals(alternativa.getTramos()));                    
                    if (!dup) {
                        resultados.add(alternativa);
                        encontrada = true;
                    }
                }
            }
            
            if (!encontrada) break; // no más alternativas
        }
        return resultados;
    }

    public boolean existeConexion(int idOrigen, int idDestino) {
        return dijkstra(idOrigen, idDestino) != null;
    }

    // utilitario: obtener paradero por id
    public Paradero getParadero(int id) { 
        return paraderos.get(id); 
    }
    public List<Paradero> getParaderos() {
        return new ArrayList<>(paraderos.values());
    }
    
    public List<Arco> getArcosParadero(Paradero p) {
    return adyacencia.get(p);
    }

    
}

    
    
