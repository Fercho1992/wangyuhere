package graph;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;

public class MatrixGraph implements Graph {

	public int SIZE;
	public Edge[][] matrix;
	public ArrayList<Vertex> vertices;
	private Hashtable<Vertex, LinkedList<Edge>> edges;

	public MatrixGraph(int n) {
		SIZE = n;
		init();
	}

	private void init() {
		matrix = new Edge[SIZE][SIZE];
		vertices = new ArrayList<Vertex>(SIZE);
		edges = new Hashtable<Vertex, LinkedList<Edge>>();
		for (int i = 0; i < SIZE; i++) {
			addVertex(new Vertex(i));
		}
	}

	@Override
	public Iterable<Edge> getAdjacentEdges(Vertex v) {
		return edges.get(v);
	}

	@Override
	public Iterable<Vertex> vertices() {
		return vertices;
	}

	@Override
	public void addEdge(Edge e) {
		LinkedList<Edge> es = edges.get(e.v1);
		assert es != null : "invalid vertex in edge";
		es.add(e);
		matrix[e.v1.number][e.v2.number] = e;
	}

	@Override
	public boolean areAdjacent(Vertex v1, Vertex v2) {
		assert v1.number < SIZE : "invalid v1 number";
		assert v2.number < SIZE : "invalid v2 number";
		return matrix[v1.number][v2.number] != null;
	}

	/**
	 * Parse a graph from input string array, start from "start" position. 4 0 1
	 * 0 0 1 0 0 0 1 1 0 0 0 0 1 0
	 * 
	 * @param input
	 * @param start
	 */
	public static MatrixGraph parseGraph(String[] input, int start) {
		MatrixGraph g;
		int size = Integer.parseInt(input[start].trim());
		g = new MatrixGraph(size);
		for (int i = 0; i < g.SIZE; i++) {
			String[] parts = input[i + start + 1].split("\\s+");
			assert parts.length == g.SIZE : "Invalid input graph";
			for (int j = 0; j < g.SIZE; j++) {
				int value = Integer.parseInt(parts[j]);
				if (value != 0) {
					Edge e = new Edge(g.vertices.get(i), g.vertices.get(j));
					e.value = value;
					g.addEdge(e);
				}
			}
		}
		return g;
	}

	private void addVertex(Vertex v) {
		vertices.add(v);
		edges.put(v, new LinkedList<Edge>());
	}

}
