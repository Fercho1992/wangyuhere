package graph;

import java.util.LinkedList;
import java.util.Queue;

public class BFS {

	public static void run(Graph g, Vertex v) {
		for (Vertex ve : g.vertices()) {
			ve.state = Vertex.WHITE;
		}

		Queue<Vertex> queue = new LinkedList<Vertex>();
		v.state = Vertex.GREY;
		queue.add(v);

		while (!queue.isEmpty()) {
			Vertex vertex = queue.remove();

			// TODO begin to explore v
			System.out.println(vertex.number);

			Iterable<Edge> es = g.getAdjacentEdges(vertex);
			for (Edge e : es) {
				if (e.v2.state == Vertex.WHITE) {
					e.v2.state = Vertex.GREY;
					queue.add(e.v2);
				}
			}
			vertex.state = Vertex.BLACK;

			// TODO end explore v
		}
	}
}
