package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.junit.Test;

import graph.*;
import graph.Graph;
import graph.Graph.*;

public class GraphTest {

	@Test
	public void DFS() {
		long startTime = System.currentTimeMillis();
		Graph graph = new Graph();
		for(int i = 1; i <= 10; i++) {
			graph.addNode(i);
		}
		
		graph.addEdge(1, 2, 1);
		graph.addEdge(1, 3, 1);
		graph.addEdge(2, 5, 1);
		graph.addEdge(2, 4, 1);
		graph.addEdge(5, 9, 1);
		graph.addEdge(4, 8, 1);
		graph.addEdge(3, 6, 1);
		graph.addEdge(3, 10, 1);
		graph.addEdge(3, 7, 1);
		graph.addEdge(7, 10, 1);
		graph.addEdge(6, 8, 1);
		
		DFS dfs = new DFS(graph);
		ArrayList<Node> topoSort = dfs.topoSort(1);
		System.out.println("-- TopoSort Result: -----------------");
		for(Node n : topoSort) {
			System.out.print(n.ID() + " ");
		}
		System.out.println();
		System.out.println("- Time required: " + (System.currentTimeMillis()-startTime) + "ms -\n");
	}
	
	@Test
	public void BFS() {
		long startTime = System.currentTimeMillis();
		Graph graph = new Graph();
		for(int i = 1; i <= 10; i++) {
			graph.addNode(i);
		}
		
		graph.addDoubleEdge(1, 2, 1);
		graph.addDoubleEdge(1, 6, 1);
		graph.addDoubleEdge(1, 3, 1);
		graph.addDoubleEdge(2, 4, 1);
		graph.addDoubleEdge(2, 5, 1);
		graph.addDoubleEdge(3, 6, 1);
		graph.addDoubleEdge(3, 7, 1);
		graph.addDoubleEdge(4, 8, 1);
		graph.addDoubleEdge(5, 8, 1);
		graph.addDoubleEdge(5, 9, 1);
		graph.addDoubleEdge(6, 9, 1);
		graph.addDoubleEdge(6, 10, 1);
		
		BFS bfs = new BFS(graph);
		HashMap<Node, Integer> SP = bfs.shortestPath(1);
		
		System.out.println("-- Distances BFS on Node (1): -------");
		for(Node v : SP.keySet()) {
			System.out.println(v.ID() + ": " + SP.get(v));
		}
		System.out.println("- Time required: " + (System.currentTimeMillis()-startTime) + "ms -\n");
	}
	
	@Test
	public void BFSspeedTest() {
		long startstartTime = System.currentTimeMillis();
		
		int n = 100;
		int m = (int) ((int) ((n*(n-1))/2)*(3.0/4.0));
		System.out.println("-- BFSspeedTest ---------------------");
		
		for(int x = 0; x < 6; x++) {
			System.out.println("> n: " + n + ", m: " + m); 
			long startTime = System.currentTimeMillis();
			
			Graph graph = new Graph();
			for(int i = 1; i <= n; i++) graph.addNode(i);
			int k = 0;
			for(int i = 1; i <= n; i++) {
				for(int j = i+1; j <= n; j++) {
					if(k >= m) break;
					graph.addDoubleEdge(i, j, 1);
					k++;
				}
				if(k >= m) break;
			}
			
			BFS bfs = new BFS(graph);
			bfs.shortestPath(1);
			
			int oldn = n;
			int oldm = m;
			n = 2*(oldn + oldm) - 2*oldm;
			m = 2*(oldn + oldm) - 2*oldn;
			System.out.println(">> Time required: " + (System.currentTimeMillis()-startTime) + "ms");
		}
		System.out.println("- Time required: " + (System.currentTimeMillis()-startstartTime) + "ms -\n");
	}
	
	@Test
	public void dijkstraTest() {
		long startstartTime = System.currentTimeMillis();
		
		Graph graph = new Graph();
		for(int i = 1; i <= 10; i++) {
			graph.addNode(i);
		}
		
		graph.addDoubleEdge(1, 3, 2);
		graph.addDoubleEdge(1, 4, 3);
		graph.addDoubleEdge(1, 2, 4);
		graph.addDoubleEdge(2, 5, 6);
		graph.addDoubleEdge(2, 6, 5);
		graph.addDoubleEdge(2, 10, 3);
		graph.addDoubleEdge(4, 5, 3);
		graph.addDoubleEdge(3, 6, 2);
		graph.addDoubleEdge(6, 9, 7);
		graph.addDoubleEdge(5, 9, 5);
		graph.addDoubleEdge(5, 8, 2);
		graph.addDoubleEdge(8, 10, 2);
		graph.addDoubleEdge(9, 10, 4);
		
		
		ShortestPath sp = new ShortestPath(graph);
		HashMap<Node, Double> dist = sp.dijsktra(1);
		
		System.out.println("-- Dijsktra SP from Node (1): ------");
		for(Node n : dist.keySet()) {
			System.out.println(n.ID() + ": " + dist.get(n));
		}
		
		System.out.println("- Time required: " + (System.currentTimeMillis()-startstartTime) + "ms -\n");
	}
	
	@Test
	public void dijkstraSpeedTest() {
		long startstartTime = System.currentTimeMillis();
		
		int n = 100;
		int m = (int) ((int) ((n*(n-1))/2)*(3.0/4.0));
		System.out.println("-- DijkstraSpeedTest ----------------");

		for(int x = 0; x < 5; x++) {
			System.out.println("> n: " + n + ", m: " + m); 
			long startTime = System.currentTimeMillis();
			
			Graph graph = new Graph();
			for(int i = 1; i <= n; i++) graph.addNode(i);
			int k = 0;
			for(int i = 1; i <= n; i++) {
				for(int j = i+1; j <= n; j++) {
					if(k >= m) break;
					graph.addDoubleEdge(i, j, k);
					k++;
				}
				if(k >= m) break;
			}
			
			ShortestPath dijk = new ShortestPath(graph);
			dijk.dijsktra(1);
			
			int oldn = n;
			int oldm = m;
			double speed = (n+m)*Math.log(n);
			n = (int) ((int) (2*speed)/(Math.log(oldn)) - oldm);
			m = (int) ((int) (2*speed)/(Math.log(oldn)) - oldn);
			System.out.println(">> Time required: " + (System.currentTimeMillis()-startTime) + "ms");
		}
		System.out.println("- Time required: " + (System.currentTimeMillis()-startstartTime) + "ms -\n");
	}
	
	@Test
	public void bellmanFordTest() {
		long startstartTime = System.currentTimeMillis();
		
		Graph graph = new Graph();
		for(int i = 1; i <= 10; i++) graph.addNode(i);
		
		graph.addEdge(1, 2, -3);
		graph.addEdge(1, 3, 5);
		graph.addEdge(2, 4, 6);
		graph.addEdge(3, 4, -3);
		graph.addEdge(2, 5, -1);
		graph.addEdge(3, 10, 2);
		graph.addEdge(10, 7, 2);
		graph.addEdge(5, 7, -1);
		graph.addEdge(5, 8, 7);
		graph.addEdge(4, 6, 8);
		graph.addEdge(6, 5, 5);
		graph.addEdge(7, 6, 5);
		graph.addEdge(7, 8, -2);
		graph.addEdge(7, 9, 2);
		graph.addEdge(9, 6, 3);
		
		ShortestPath sp = new ShortestPath(graph);
		HashMap<Node, Double> d = sp.bellmanford(1);
		
		System.out.println("-- BellmanFord SP from Node (1): ----");
		if(d == null) System.out.println("> Negative cycle detected!!");
		else {
			for(Node n : d.keySet()) {
				System.out.println(n.ID() + ": " + d.get(n));
			}
		}
		
		System.out.println("- Time required: " + (System.currentTimeMillis()-startstartTime) + "ms -\n");
	}
	
	@Test
	public void nDijsktraTest() {
		long startstartTime = System.currentTimeMillis();
		
		Graph graph = new Graph();
		for(int i = 1; i <= 10; i++) {
			graph.addNode(i);
		}
		
		graph.addDoubleEdge(1, 3, 2);
		graph.addDoubleEdge(1, 2, 4);
		graph.addDoubleEdge(3, 6, 2);
		graph.addDoubleEdge(4, 5, 9);
		graph.addDoubleEdge(1, 4, 3);
		graph.addDoubleEdge(2, 6, 5);
		graph.addDoubleEdge(2, 5, 6);
		graph.addDoubleEdge(2, 10, 3);
		graph.addDoubleEdge(6, 9, 7);
		graph.addDoubleEdge(5, 9, 5);
		graph.addDoubleEdge(5, 8, 2);
		graph.addDoubleEdge(9, 10, 4);
		graph.addDoubleEdge(10, 8, 2);
		graph.addDoubleEdge(8, 7, 3);
		graph.addDoubleEdge(7, 9, 5);
		graph.addDoubleEdge(3, 7, 10);
		
		
		ShortestPath sp = new ShortestPath(graph);
		double[][] dist = sp.nDijkstra(10);
		
		System.out.println("-- n*Dijsktra all-to-all -----------");
		for(int x = 0; x < 10; x++) {
			for(int y = 0; y < 10; y++) {
				System.out.print((dist[x][y] == 0.0 ? "---" : dist[x][y]) + "\t");
			}
			System.out.println();
		}
		
		System.out.println("- Time required: " + (System.currentTimeMillis()-startstartTime) + "ms -\n");
	}
	
	@Test
	public void floydWarshallTest() {
		long startstartTime = System.currentTimeMillis();
		
		Graph graph = new Graph();
		for(int i = 1; i <= 10; i++) {
			graph.addNode(i);
		}
		
		graph.addDoubleEdge(1, 3, 2);
		graph.addDoubleEdge(1, 2, 4);
		graph.addDoubleEdge(3, 6, 2);
		graph.addDoubleEdge(4, 5, 9);
		graph.addDoubleEdge(1, 4, 3);
		graph.addDoubleEdge(2, 6, 5);
		graph.addDoubleEdge(2, 5, 6);
		graph.addDoubleEdge(2, 10, 3);
		graph.addDoubleEdge(6, 9, 7);
		graph.addDoubleEdge(5, 9, 5);
		graph.addDoubleEdge(5, 8, 2);
		graph.addDoubleEdge(9, 10, 4);
		graph.addDoubleEdge(10, 8, 2);
		graph.addDoubleEdge(8, 7, 3);
		graph.addDoubleEdge(7, 9, 5);
		graph.addDoubleEdge(3, 7, 10);
		
		
		ShortestPath sp = new ShortestPath(graph);
		double[][] dist = sp.floydWarshall(10);
		
		System.out.println("-- Floyd-Warshall ------------------");
		for(int x = 1; x <= 10; x++) {
			for(int y = 1; y <= 10; y++) {
				System.out.print((dist[x][y] == 0.0 ? "---" : dist[x][y]) + "\t");
			}
			System.out.println();
		}
		
		System.out.println("- Time required: " + (System.currentTimeMillis()-startstartTime) + "ms -\n");
	}
	
	@Test
	public void floydWarshallTestNegativeEdges() {
		long startstartTime = System.currentTimeMillis();
		
		Graph graph = new Graph();
		for(int i = 1; i <= 10; i++) graph.addNode(i);
		
		graph.addEdge(1, 2, -3);
		graph.addEdge(1, 3, 5);
		graph.addEdge(2, 4, 6);
		graph.addEdge(3, 4, -3);
		graph.addEdge(2, 5, -1);
		graph.addEdge(3, 10, 2);
		graph.addEdge(10, 7, 2);
		graph.addEdge(5, 7, -1);
		graph.addEdge(5, 8, 7);
		graph.addEdge(4, 6, 8);
		graph.addEdge(6, 5, 5);
		graph.addEdge(7, 6, 5);
		graph.addEdge(7, 8, -2);
		graph.addEdge(7, 9, 2);
		graph.addEdge(9, 6, 3);
		
		
		ShortestPath sp = new ShortestPath(graph);
		double[][] dist = sp.floydWarshall(10);
		
		System.out.println("-- Floyd-Warshall Negative ---------");
		for(int x = 1; x <= 10; x++) {
			for(int y = 1; y <= 10; y++) {
				System.out.print((dist[x][y] == 0.0 || dist[x][y] > 5000 ? "---" : dist[x][y]) + "\t");
			}
			System.out.println();
		}
		
		System.out.println("- Time required: " + (System.currentTimeMillis()-startstartTime) + "ms -\n");
	}
	
	@Test
	public void johnsonTest() {
		long startstartTime = System.currentTimeMillis();
		
		Graph graph = new Graph();
		for(int i = 1; i <= 10; i++) {
			graph.addNode(i);
		}
		
		graph.addDoubleEdge(1, 3, 2);
		graph.addDoubleEdge(1, 2, 4);
		graph.addDoubleEdge(3, 6, 2);
		graph.addDoubleEdge(4, 5, 9);
		graph.addDoubleEdge(1, 4, 3);
		graph.addDoubleEdge(2, 6, 5);
		graph.addDoubleEdge(2, 5, 6);
		graph.addDoubleEdge(2, 10, 3);
		graph.addDoubleEdge(6, 9, 7);
		graph.addDoubleEdge(5, 9, 5);
		graph.addDoubleEdge(5, 8, 2);
		graph.addDoubleEdge(9, 10, 4);
		graph.addDoubleEdge(10, 8, 2);
		graph.addDoubleEdge(8, 7, 3);
		graph.addDoubleEdge(7, 9, 5);
		graph.addDoubleEdge(3, 7, 10);
		
		
		ShortestPath sp = new ShortestPath(graph);
		double[][] dist = sp.johnson(10);
		
		System.out.println("-- Johnson's all-to-all ------------");
		for(int x = 0; x < 10; x++) {
			for(int y = 0; y < 10; y++) {
				System.out.print((dist[x][y] == 0.0 ? "---" : dist[x][y]) + "\t");
			}
			System.out.println();
		}
		
		System.out.println("- Time required: " + (System.currentTimeMillis()-startstartTime) + "ms -\n");
	}
	
	@Test
	public void johnsonTestNegative() {
		long startstartTime = System.currentTimeMillis();
		
		Graph graph = new Graph();
		for(int i = 1; i <= 10; i++) graph.addNode(i);
		
		graph.addEdge(1, 2, -3);
		graph.addEdge(1, 3, 5);
		graph.addEdge(2, 4, 6);
		graph.addEdge(3, 4, -3);
		graph.addEdge(2, 5, -1);
		graph.addEdge(3, 10, 2);
		graph.addEdge(10, 7, 2);
		graph.addEdge(5, 7, -1);
		graph.addEdge(5, 8, 7);
		graph.addEdge(4, 6, 8);
		graph.addEdge(6, 5, 5);
		graph.addEdge(7, 6, 5);
		graph.addEdge(7, 8, -2);
		graph.addEdge(7, 9, 2);
		graph.addEdge(9, 6, 3);
		
		ShortestPath sp = new ShortestPath(graph);
		double[][] dist = sp.johnson(10);
		
		System.out.println("-- Johnson Negative ----------------");
		for(int x = 0; x < 10; x++) {
			for(int y = 0; y < 10; y++) {
				System.out.print((dist[x][y] == 0.0 || dist[x][y] > 5000 ? "---" : dist[x][y]) + "\t");
			}
			System.out.println();
		}
		
		System.out.println("- Time required: " + (System.currentTimeMillis()-startstartTime) + "ms -\n");
	}
	
	@Test
	public void primsTest() {
		long startstartTime = System.currentTimeMillis();
		
		Graph graph = new Graph();
		for(int i = 1; i <= 10; i++) {
			graph.addNode(i);
		}
		
		graph.addDoubleEdge(1, 3, 2);
		graph.addDoubleEdge(1, 2, 4);
		graph.addDoubleEdge(3, 6, 2);
		graph.addDoubleEdge(4, 5, 9);
		graph.addDoubleEdge(1, 4, 3);
		graph.addDoubleEdge(2, 6, 5);
		graph.addDoubleEdge(2, 5, 6);
		graph.addDoubleEdge(2, 10, 3);
		graph.addDoubleEdge(6, 9, 7);
		graph.addDoubleEdge(5, 9, 5);
		graph.addDoubleEdge(5, 8, 2);
		graph.addDoubleEdge(9, 10, 4);
		graph.addDoubleEdge(10, 8, 2);
		graph.addDoubleEdge(8, 7, 3);
		graph.addDoubleEdge(7, 9, 5);
		graph.addDoubleEdge(3, 7, 10);
		
		MST mst = new MST(graph);
		Set<Edge> span = mst.prims();
		
		System.out.println("-- Prims MST -----------------------");
		for(Edge e : span) {
			System.out.print(e.toString() + " ");
		}
		System.out.println();
		
		System.out.println("- Time required: " + (System.currentTimeMillis()-startstartTime) + "ms -\n");
	}
	
	@Test
	public void kruskalsTest() {
		long startstartTime = System.currentTimeMillis();
		
		Graph graph = new Graph();
		for(int i = 1; i <= 10; i++) {
			graph.addNode(i);
		}
		
		graph.addDoubleEdge(1, 3, 2);
		graph.addDoubleEdge(1, 2, 4);
		graph.addDoubleEdge(3, 6, 2);
		graph.addDoubleEdge(4, 5, 9);
		graph.addDoubleEdge(1, 4, 3);
		graph.addDoubleEdge(2, 6, 5);
		graph.addDoubleEdge(2, 5, 6);
		graph.addDoubleEdge(2, 10, 3);
		graph.addDoubleEdge(6, 9, 7);
		graph.addDoubleEdge(5, 9, 5);
		graph.addDoubleEdge(5, 8, 2);
		graph.addDoubleEdge(9, 10, 4);
		graph.addDoubleEdge(10, 8, 2);
		graph.addDoubleEdge(8, 7, 3);
		graph.addDoubleEdge(7, 9, 5);
		graph.addDoubleEdge(3, 7, 10);
		
		MST mst = new MST(graph);
		Set<Edge> span = mst.kruskals();
		
		System.out.println("-- Kruskals MST --------------------");
		for(Edge e : span) {
			System.out.print(e.toString() + " ");
		}
		System.out.println();
		
		System.out.println("- Time required: " + (System.currentTimeMillis()-startstartTime) + "ms -\n");
	}
	
	@Test
	public void boruvkasTest() {
long startstartTime = System.currentTimeMillis();
		
		Graph graph = new Graph();
		for(int i = 1; i <= 10; i++) {
			graph.addNode(i);
		}
		
		graph.addDoubleEdge(1, 3, 2);
		graph.addDoubleEdge(1, 2, 4);
		graph.addDoubleEdge(3, 6, 2);
		graph.addDoubleEdge(4, 5, 9);
		graph.addDoubleEdge(1, 4, 3);
		graph.addDoubleEdge(2, 6, 5);
		graph.addDoubleEdge(2, 5, 6);
		graph.addDoubleEdge(2, 10, 3);
		graph.addDoubleEdge(6, 9, 7);
		graph.addDoubleEdge(5, 9, 5);
		graph.addDoubleEdge(5, 8, 2);
		graph.addDoubleEdge(9, 10, 4);
		graph.addDoubleEdge(10, 8, 2);
		graph.addDoubleEdge(8, 7, 3);
		graph.addDoubleEdge(7, 9, 5);
		graph.addDoubleEdge(3, 7, 10);
		
		MST mst = new MST(graph);
		Set<Edge> span = mst.boruvkas();
		
		System.out.println("-- Boruvkas MST --------------------");
		for(Edge e : span) {
			System.out.print(e.toString() + " ");
		}
		System.out.println();
		
		System.out.println("- Time required: " + (System.currentTimeMillis()-startstartTime) + "ms -\n");
	}
}
