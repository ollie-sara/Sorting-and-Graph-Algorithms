package graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Graph {
	
	// Hash Maps with IDs to fetch specific Node objects using an ID
	Map<Integer, Node> vertices = new HashMap<>();
	Map<String, Edge> edges = new HashMap<>();
	
	// Getter for Node and Edge that handles the "conversion" to Object
	public Node getNode(int ID) {
		return vertices.get(ID);
	}
	
	public Edge getEdge(int fromID, int toID) {
		return edges.get(fromID + ":" + toID);
	}
	
	public Edge getEdge(String s) {
		return edges.get(s);
	}
	
	// Functions for the creation of a graph
	public void addNode(int ID) {
		vertices.put(ID, new Node(ID));
	}
	
	public Set<String> edges() {
		return edges.keySet();
	}
	
	public Set<Integer> vertices() {
		return vertices.keySet();
	}
	
	public void addEdge(int fromID, int toID, double weight) {
		Node from = getNode(fromID);
		Node to = getNode(toID);
		if(from == null || to == null) throw new IllegalStateException("One of the nodes does not exist yet");
		Edge edge = new Edge(from, to, weight);
		from.outInc.add(edge);
		to.inInc.add(edge);
		from.outAdj.add(to);
		to.inAdj.add(from);
		edges.put(fromID + ":" + toID, edge);
	}
	
	public void addDoubleEdge(int first, int second, double weight) {
		Node fst = getNode(first);
		Node snd = getNode(second);
		if(fst == null || snd == null) throw new IllegalStateException("One of the nodes does not exist yet");
		Edge e1 = new Edge(fst, snd, weight);
		Edge e2 = new Edge(snd, fst, weight);
		
		// INCIDENT EDGES
		fst.outInc.add(e1);
		fst.inInc.add(e2);
		snd.outInc.add(e2);
		snd.inInc.add(e1);
		
		// ADJACENT NODES
		fst.outAdj.add(snd);
		fst.inAdj.add(snd);
		snd.outAdj.add(fst);
		snd.inAdj.add(fst);
		
		edges.put(first + ":" + second, e1);
		edges.put(second + ":" + first, e2);
	}
	
	/**
	 *  NODE CLASS
	 *  @param ID = the ID with which we can find the Node object later
	 *  @param outInc = every outward facing Edge
	 *  @param inInc = every inward facing Edge
	 *  @param outAdj = every outward adjacent Node
	 *  @param inAdj = every inward adjacent Node
	 */
	public class Node {
		private int ID;
		private Set<Edge> outInc = new HashSet<>();
		private Set<Edge> inInc = new HashSet<>();
		private Set<Node> outAdj = new HashSet<>();
		private Set<Node> inAdj = new HashSet<>();
		
		Node(int ID) {
			this.ID = ID;
		}
		
		/**
		 * Returns set of outward facing edges
		 */
		public Set<Edge> outInc() {
			return outInc;
		}
		
		/**
		 * Returns set of inward facing edges
		 */
		public Set<Edge> inInc() {
			return inInc;
		}
		
		/**
		 * Returns set of outward adjacent Nodes
		 */
		public Set<Node> outAdj() {
			return outAdj;
		}
		
		/**
		 * Returns set of inward adjacent Nodes
		 */
		public Set<Node> inAdj() {
			return inAdj;
		}
		
		/**
		 * Returns ID of Node
		 */
		public int ID() {
			return ID;
		}
	}
	
	public class Edge implements Comparable<Edge> {
		private Node from;
		private Node to;
		private double weight;
		
		Edge(Node from, Node to, double weight) {
			this.from = from;
			this.to = to;
			this.weight = weight;
		}
		
		public Node from() {
			return from;
		}
		
		public Node to() {
			return to;
		}
		
		public double weight() {
			return weight;
		}
		
		public String toString() {
			return from.ID() + ":" + to.ID();
		}
		
		@Override
		public int compareTo(Edge o) {
			if(weight > o.weight) return 1;
			if(weight < o.weight) return -1;
			else return 0;
		}
	}
}
