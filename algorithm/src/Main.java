import graph.BFS;
import graph.DFS;
import graph.Edge;
import graph.ListGraph;
import graph.MatrixGraph;
import graph.Vertex;

public class Main {

	public static void main(String[] args) {
		testMatrixGraph();
	}

	private static void testListGraph() {
		ListGraph g = new ListGraph();

		Vertex[] vs = new Vertex[6];
		for (int i = 0; i < vs.length; i++) {
			vs[i] = new Vertex(i);
			g.addVertex(vs[i]);
		}

		g.addEdge(new Edge(vs[0], vs[1]));
		g.addEdge(new Edge(vs[1], vs[0]));
		g.addEdge(new Edge(vs[1], vs[2]));
		g.addEdge(new Edge(vs[2], vs[1]));
		g.addEdge(new Edge(vs[2], vs[3]));
		g.addEdge(new Edge(vs[3], vs[2]));
		g.addEdge(new Edge(vs[3], vs[4]));
		g.addEdge(new Edge(vs[4], vs[3]));
		g.addEdge(new Edge(vs[4], vs[5]));
		g.addEdge(new Edge(vs[5], vs[4]));
		g.addEdge(new Edge(vs[1], vs[5]));
		g.addEdge(new Edge(vs[5], vs[1]));

		DFS.run(g, vs[0]);
		BFS.run(g, vs[0]);
	}

	private static void testMatrixGraph() {
		String[] input = { "5", "0 2 3 4 5", "2 0 19 20 4", "3 19 0 11 9",
				"4 20 11 0 10", "5 4 9 10 0" };
		MatrixGraph g = MatrixGraph.parseGraph(input, 0);
		DFS.run(g, g.vertices.get(0));
		BFS.run(g, g.vertices.get(0));
	}
}
