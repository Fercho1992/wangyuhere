package graph;

public interface Graph {

	Iterable<Edge> getAdjacentEdges(Vertex v);

	Iterable<Vertex> vertices();

	void addEdge(Edge e);

	boolean areAdjacent(Vertex v1, Vertex v2);

}
