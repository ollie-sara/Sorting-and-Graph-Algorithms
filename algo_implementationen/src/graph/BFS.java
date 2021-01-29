package graph;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

import graph.Graph.*;

public class BFS {
	Graph graph;
	
	public BFS(Graph graph) {
		this.graph = graph;
	}
	
	public HashMap<Node, Integer> shortestPath(int start){
		Node S = graph.getNode(start);														// Get Node element with passed ID
		if(S == null) throw new IllegalStateException("No such node: " + start);			// Check if the Node even exists
		Set<Integer> nodes = graph.vertices.keySet();										// Keeping a list so that I dont check a node twice
		HashMap<Node, Integer> out = new HashMap<>();										// My way of return a list of all distances
		
		LinkedList<Pair> queue = new LinkedList<>();										// Queue to enqueue what to look at next
		queue.offer(new Pair(S, 0));														// Enqueue start node
		
		while(!queue.isEmpty()) {	
			Pair p = queue.poll();															// Look at next node in queue
			out.put(p.node, p.value);														// Save the distance to the node (will for sure be the lowest)
			for(Node u : p.node.outAdj()) {													// Look at all outbound nodes
				if(nodes.contains(u.ID())) {												// Did we look at the outbound node already?
					queue.offer(new Pair(u, p.value+1));									// If no: put it in the queue, we'll look at it later
					nodes.remove(u.ID());													// Also make sure we dont put it in the queue twice
				}																				// the first to put it in is for sure the shortest
			}
		}
		
		return out;
	}
	
	private class Pair {		// Tupel to make it easier, holds node and its distance to start node
		Node node;
		int value;
		
		Pair(Node n, int val){
			this.node = n;
			this.value = val;
		}
	}
}
