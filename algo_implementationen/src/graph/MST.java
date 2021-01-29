package graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import graph.Graph.*;

public class MST {
	Graph graph;
	
	public MST(Graph graph) {
		this.graph = graph;
	}
	
	// Prims
	public Set<Edge> prims(){
		Node start = (Node) graph.vertices.values().toArray()[0];				// Choose any start node, I do this semi randomly
		Set<Integer> vertices = graph.vertices.keySet();						// Get a set of all vertices, used to keep track of added vertices
		Set<Edge> out = new HashSet<Edge>();									// Prepare output set
		vertices.remove(start.ID());											
		
		PriorityQueue<Pair> minheap = new PriorityQueue<>();					// Create minheap
		for(Edge e : start.outInc()) {
			minheap.add(new Pair(e, e.weight()));								// Add all outbound edges of our start node to minheap
		}
				
		while(!minheap.isEmpty()) {
			Pair cur = minheap.poll();											// Pull out min
			if(vertices.contains(cur.e.to().ID())) {							// Did we add the destination of the edge already?
				Node u = cur.e.to();											// if no: continue with our chosen edge
				vertices.remove(u.ID());										// mark the edge as visited
				out.add(cur.e);													// add the edge to our output
				for(Edge o : u.outInc()) {										// iterate through the outbound incident edges of the destination node
					if(vertices.contains(o.to().ID())) {						// did we add the destination of that edge?
						minheap.add(new Pair(o, o.weight()));					// if no: add it to the minheap
					}
				}
			}
		}
		
		return out;			// Send it off into space
		
	}
	
	// Kruskals with UnionFind
	public Set<Edge> kruskals() {
		PriorityQueue<Edge> edges = new PriorityQueue<>();		// Create minheap
		for(String s : graph.edges.keySet()) {
			edges.add(graph.getEdge(s));						// Fill minheap with literally every edge in the graph
		}
		
		Set<Edge> out = new HashSet<Edge>();					// Instance output set
		UnionFind<Integer> components = new UnionFind<>(graph.vertices());	// Create UnionFind with all of our cool ass nodes
		
		while(!edges.isEmpty()) {										
			Edge cur = edges.poll();
			if(!components.same(cur.from().ID(), cur.to().ID())) {
				out.add(cur);
				components.union(cur.from().ID(), cur.to().ID());
			}
		}
		
		return out;
	}
	
	private class Pair implements Comparable<Pair> {
		Edge e;
		double val;
		
		Pair(Edge e, double val){
			this.e = e;
			this.val = val;
		}

		@Override
		public int compareTo(Pair o) {
			if(o.val > val) return -1;
			else if(o.val < val) return 1;
			else return 0;
		}
	}
	
	public Set<Edge> boruvkas() {
		UnionFind<Integer> unionfind = new UnionFind<>(graph.vertices.keySet());
		HashMap<Integer, PriorityQueue<Edge>> edges = new HashMap<>();
		Set<Edge> out = new HashSet<>();
		 
		for(int i : graph.vertices.keySet()) {
			PriorityQueue<Edge> iEdges = new PriorityQueue<>();
			for(Edge e : graph.getNode(i).outInc()) iEdges.add(e);
			edges.put(i, iEdges);
		}
		
		while(unionfind.components.size() != 1) {
			Set<Integer> copy = new HashSet<Integer>();
			copy.addAll(unionfind.components);
			for(int n : copy) {
				if(unionfind.components.size() == 1) break;
				if(!unionfind.components.contains(n)) continue;
				Edge e = edges.get(n).poll();
				while(unionfind.same(n, e.to().ID())) e = edges.get(n).poll();
				int dstComp = unionfind.rep.get(e.to().ID());
				out.add(e);
				if(unionfind.members.get(n).size() > unionfind.members.get(dstComp).size()) {
					while(!edges.get(dstComp).isEmpty()) edges.get(n).add(edges.get(dstComp).poll());
					edges.remove(dstComp);
					unionfind.union(n, dstComp);
				} else {
					while(!edges.get(n).isEmpty()) edges.get(dstComp).add(edges.get(n).poll());
					edges.remove(n);
					unionfind.union(n, dstComp);
				}
			}
		}
		
		return out;
	}
}
