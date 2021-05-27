package it.polito.tdp.food.model;

import java.util.Comparator;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

public class ComparatoreFood implements Comparator<Food>
{
	private Graph<Food, DefaultWeightedEdge> grafo; 
	private Food vertice;
	
	public ComparatoreFood(Graph<Food, DefaultWeightedEdge> grafo, Food vertice)
	{
		 this.grafo = grafo;
		 this.vertice = vertice; 
	}
	
	@Override public int compare(Food f1, Food f2)
	{
		DefaultWeightedEdge e1 = this.grafo.getEdge(vertice, f1);
		DefaultWeightedEdge e2 = this.grafo.getEdge(vertice, f2);
		return - (int) (this.grafo.getEdgeWeight(e1) - this.grafo.getEdgeWeight(e2));
	}

}
