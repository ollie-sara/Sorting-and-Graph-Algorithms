package graph;

import java.util.ArrayList;
import java.util.Set;

import graph.Graph.*;

public class DFS {
	Graph graph;
	
	public DFS(Graph graph){
		this.graph = graph;
	}
	
	public ArrayList<Node> topoSort(int startNode){
		Set<Integer> vertices = graph.vertices.keySet();		// Get a set if all vertices in the graph
		Node start = graph.getNode(startNode);					// Get the node element with the passed ID
		Node[] post = new Node[vertices.size()];				// Create an array that holds all nodes in post value order
		fillPost(start, vertices, post, new IntWrapper(0));		// Call recursive function that fills said post array
		ArrayList<Node> out = new ArrayList<>();				// Make a cool new arraylist
		for(int o = post.length-1; o >= 0; o--) {				// Reverse all items from post
			out.add(post[o]);
		}														
		return out;												// Send it off into space
	}
	
	private void fillPost(Node v, Set<Integer> verts, Node[] post, IntWrapper i) {
		verts.remove(v.ID());					// Remove the vertex from our list of vertices, so we dont look at it twice
		for(Node u : v.outAdj()) {				// Go through all the outbound nodes
			if(verts.contains(u.ID())) {		// Did we look at this outbound node already?
				fillPost(u, verts, post, i);	// If no: lets look at it now :Pog:
			}						
		}	
		post[i.value++] = v;					// Once we looked at all our children, add it to the array					
		return;									// Send it off into space
	}
	
	private class IntWrapper {					// Wrapper class so that I pass an Integer pointer instead of the literal
		int value;								// That way I can increment it in recursive calls and it will affect their callers too
		
		IntWrapper(int start){
			value = start;
		}
	}
}
