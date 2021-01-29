package graph;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

public class UnionFind<T> {
	HashMap<T, T> rep;
	HashMap<T, LinkedList<T>> members;
	Set<T> components;
	
	UnionFind(Collection<T> elems) {
		rep = new HashMap<>();
		members = new HashMap<>();
		components = new HashSet<>();
		Iterator<T> it = elems.iterator();
		for(int i = 0; i < elems.size(); i++) {
			T item = it.next();
			rep.put(item, item);
			members.put(item, new LinkedList<>());
			members.get(item).add(item);
			components.add(item);
		}
	}
	
	public boolean same(T u, T v) {
		return rep.get(u) == rep.get(v);
	}
	
	public void union(T u, T v) {
		T repU = rep.get(u);
		T repV = rep.get(v);
		
		if(u == null || v == null) throw new IllegalArgumentException("Passed items cannot be null");
		if(repU == repV) throw new IllegalStateException("Both items already share the same connected component");
		if(repU == null || repV == null) throw new IllegalStateException("Couldn't find connected component of one of the supplied items");
		
		if(members.get(repU).size() > members.get(repV).size()) {
			for(T t : members.get(repV)) {
				rep.put(t, repU);
				members.get(repU).add(t);
			}
			components.remove(repV);
			members.remove(repV);
		} else {
			for(T t : members.get(repU)) {
				rep.put(t, repV);
				members.get(repV).add(t);
			}
			components.remove(repU);
			members.remove(repU);
		}
	}
}
