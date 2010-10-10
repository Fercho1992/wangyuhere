package graph;

import java.util.Stack;

public class DFS {

	public static void run(Graph g, Vertex v) {
		// mark all vertices to white
		for (Vertex ve : g.vertices()) {
			ve.state = Vertex.WHITE;
		}

		Stack<Vertex> stack = new Stack<Vertex>();
		// current vertex
		Vertex vertex = v;
		// next white vertex
		Vertex next = v;

		do {
			if (next == null) {
				Iterable<Edge> es = g.getAdjacentEdges(vertex);
				for (Edge e : es) {
					if (e.v2.state == Vertex.WHITE) {
						next = e.v2;
						break;
					}
				}
			}
			if (next == null) {
				stack.pop();
				vertex.state = Vertex.BLACK;
				// TODO end explore v
			} else {
				next.state = Vertex.GREY;
				stack.push(next);
				// TODO begin to explore v
				System.out.println(next.number);

				next = null;
			}
			if (!stack.isEmpty())
				vertex = stack.peek();
			else
				vertex = null;
		} while (vertex != null);
	}

	public static void recursiveDFS(Graph g, Vertex v) {
		for (Vertex ve : g.vertices()) {
			ve.state = Vertex.WHITE;
		}
		runDFS(g, v);
	}

	private static void runDFS(Graph g, Vertex v) {
		v.state = Vertex.GREY;

		// begin to explore v
		System.out.println(v.number);

		Iterable<Edge> es = g.getAdjacentEdges(v);
		for (Edge e : es) {
			if (e.v2.state == Vertex.WHITE) {
				runDFS(g, e.v2);
			}
		}
		v.state = Vertex.BLACK;
	}
}
