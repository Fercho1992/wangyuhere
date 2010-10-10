package graph;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;

public class ListGraph implements Graph {

	private ArrayList<Vertex> vertices;
	private Hashtable<Vertex, LinkedList<Edge>> edges;

	public ListGraph() {
		vertices = new ArrayList<Vertex>();
		edges = new Hashtable<Vertex, LinkedList<Edge>>();
	}

	public void addVertex(Vertex v) {
		vertices.add(v);
		edges.put(v, new LinkedList<Edge>());
	}

	@Override
	public void addEdge(Edge e) {
		LinkedList<Edge> es = edges.get(e.v1);
		assert es != null : "invalid vertex in edge";
		es.add(e);
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
	public boolean areAdjacent(Vertex v1, Vertex v2) {
		LinkedList<Edge> es = edges.get(v1);
		if (es == null)
			return false;
		for (Edge e : es) {
			if (e.v2 == v2) {
				return true;
			}
		}
		return false;
	}
}
