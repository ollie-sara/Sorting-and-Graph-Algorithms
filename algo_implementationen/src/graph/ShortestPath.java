package graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

import graph.Graph.*;

public class ShortestPath {
	Graph graph;
	
	public ShortestPath(Graph graph) {
		this.graph = graph;
	}
	
	
	// DIJKSTRA
	public HashMap<Node, Double> dijsktra(int start){
		Node S = graph.getNode(start);								// Get node element with passed ID number
		HashMap<Node, Double> distances = new HashMap<>();			// Create our output HashMap
		
		PriorityQueue<DijPair> minheap = new PriorityQueue<>();		// Create minheap
		minheap.offer(new DijPair(S, 0));							// Put start node into minheap
		
		Set<Integer> marked = new HashSet<>();						// Keep track of all nodes we visited so we dont have to revisit it
		
		while(!minheap.isEmpty()) {
			DijPair cur = minheap.poll();							// Take next smallest element of minheap
			if(marked.contains(cur.node.ID())) continue;			// Did we check this already? if yes: go on to the next element
			
			marked.add(cur.node.ID());								// Lets mark this so we don't check it again
			distances.put(cur.node, cur.value);						// Lets also save how far it took us to get here (distance)
			
			for(Edge e : cur.node.outInc()) {						// Go through all outbound edges
				if(!marked.contains(e.to().ID())) {								// Did we check the outbound node already? If yes: continue to next edge
					minheap.offer(new DijPair(e.to(), cur.value + e.weight()));	// If no: add it to our minheap with the distance it took to get there
				}
			}
		}
		
		
		return distances;											// Send it off into space
	}
	
	private class DijPair implements Comparable<DijPair> {		// Comparable so we can do a minheap easily.
		
		Node node;
		double value;
		
		DijPair(Node node, double value) {
			this.node = node;
			this.value = value;
		}
		
		@Override
		public int compareTo(DijPair o) {
			if(value > o.value) return 1;
			else if(value < o.value) return -1;
			else return 0;
		}
		
	}
	
	// BELLMAN-FORD
	public HashMap<Node, Double> bellmanford(int start) {				// Similar to Dijkstra, but we forcibly relax our distance V-1 times
		Node s = graph.getNode(start);									// Get node element with passed ID number
		HashMap<Node, Double> d = new HashMap<>();						// instance our output distance map
		for(int n : graph.vertices.keySet()) {							// fill it with infinite values for each edge
			d.put(graph.getNode(n), Double.MAX_VALUE);					
		}
		d.put(s, 0.0);													// Override our starting node with length 0
		
		for(int i = 0; i < graph.vertices.size()-1; i++) {				// Relax each node V-1 times
			for(String es : graph.edges.keySet()) {						// Go through each edge
				Edge e = graph.getEdge(es);			
				if(d.get(e.to()) > d.get(e.from()) + e.weight()) {		// Is the distance + weight of the inbound adjacent node smaller than what I currently?
					d.put(e.to(), d.get(e.from()) + e.weight());		// If yes: Relax the node
				}
			}
		}
		
		for(String es : graph.edges.keySet()) {							// Relax one more time
			Edge e = graph.getEdge(es);			
			if(d.get(e.to()) > d.get(e.from()) + e.weight()) {			// Do I have to relax a node further?
				return null;											// If yes: I shouldn't. This must mean there's a negative cycle!!!!!
			}
		}
		
		return d;														// Send it off into space
	}
	
	//// ALL TO ALL
	// n*Dijskstra
	public double[][] nDijkstra(int n) {								// all-to-all with just n times Dijkstra
		double[][] d = new double[n][n];								// Instance output array
		
		for(int node = 1; node <= 10; node++) {
			Node S = graph.getNode(node);								// Choose new start with each iteration
			
			PriorityQueue<DijPair> minheap = new PriorityQueue<>();		// Create minheap
			minheap.offer(new DijPair(S, 0));							// Put startnode into minheap
			
			Set<Integer> marked = new HashSet<>();						// Keep track of all already visited nodes
			
			while(!minheap.isEmpty()) {
				DijPair cur = minheap.poll();							// Take out min
				if(marked.contains(cur.node.ID())) continue;			// Did we visit min already? If yes: next min
				
				marked.add(cur.node.ID());								// Mark min as visited
				d[S.ID()-1][cur.node.ID()-1] = cur.value;				// Save distance to min into output
				
				for(Edge e : cur.node.outInc()) {						// Go through all outbound incident edges
					if(!marked.contains(e.to().ID())) {					// Did we visit the outbound node?
						minheap.offer(new DijPair(e.to(), cur.value + e.weight()));	// If no: add node to minheap
					}
				}
			}
		}
			
		return d;			// Send it off into space
	}
	
	// Floyd-Warshall
	public double[][] floydWarshall(int n) {		
		double[][][] d = new double[n+1][n+1][n+1];					// Instance output array
		
		for(int from = 1; from <= n; from++) {						// Instance very first D-Array (shortest path using no inbetween edge)
			for(int to = 1; to <= n; to++) {		
				if(from == to) d[0][from][to] = 0.0;
				else if(graph.getEdge(from, to) == null) d[0][from][to] = Double.MAX_VALUE;
				else {
					d[0][from][to] = graph.getEdge(from, to).weight();
				}
			}
		}
		
		for(int l = 1; l <= n; l++) {								// Continue DP for all non-basecases
			for(int from = 1; from <= n; from++) {
				for(int to = 1; to <= n; to++) {
					d[l][from][to] = Math.min(d[l-1][from][l] + d[l-1][l][to], d[l-1][from][to]);	// Either it uses l-th node for the shortest path or it doesnt
				}
			}
		}
		
		return d[10];			// Send the final array off into space
	}
	
	// Johnson
	public double[][] johnson(int n) {
		Graph copy = new Graph();						// Copy our graph
		for(int node : graph.vertices.keySet()) {
			copy.addNode(node);
		}
		for(String s : graph.edges.keySet()) {
			String[] en = s.split(":");
			copy.addEdge(Integer.parseInt(en[0]), Integer.parseInt(en[1]), graph.edges.get(s).weight());
		}												// Finish copying
		
		copy.addNode(-1);								// Add new node (our h node)
		for(int i = 1; i <= n; i++) {					
			copy.addEdge(-1, i, 0);						// Connect our h node with every other node using length 0
		}
		
		HashMap<Node, Double> h = new ShortestPath(copy).bellmanford(-1);	// Do bellman ford on our h node to get h values for every node in the graph
		double[][] d = new double[n][n];
		
		for(Integer node : graph.vertices.keySet()) {						// n times Dijkstra, but the distance we compare is w(u,v) + h(u) - h(v)
			Node start = graph.getNode(node);									// otherwise exactly like n times Dijkstra
			
			PriorityQueue<DijPair> minheap = new PriorityQueue<>();
			minheap.offer(new DijPair(start, 0));
			
			Set<Node> marked = new HashSet<>();
			
			while(!minheap.isEmpty()) {
				DijPair cur = minheap.poll();
				if(marked.contains(cur.node)) continue;
				marked.add(cur.node);
				d[node-1][cur.node.ID()-1] = cur.value - h.get(copy.getNode(node)) + h.get(copy.getNode(cur.node.ID()));	// When we save the distance, we must do
				for(Edge e : cur.node.outInc()) {																				// d'(u,v) - h(u) + h(v) = d(u,v) 
					if(!marked.contains(e.to())) {
						minheap.offer(new DijPair(e.to(), cur.value + e.weight() + h.get(copy.getNode(e.from().ID())) - h.get(copy.getNode(e.to().ID()))));
					}
				}
			}
		}
		
		return d;
	}
}
