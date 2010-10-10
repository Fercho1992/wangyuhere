package graph;

public class Vertex {

	public static final int WHITE = 0;
	public static final int GREY = 1;
	public static final int BLACK = 2;
	public int state = WHITE;
	public int number = 0;

	public Vertex(int number) {
		this.number = number;
	}
}
