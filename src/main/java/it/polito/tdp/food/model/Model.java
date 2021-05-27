package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model
{
	private FoodDao dao;
	private Map<Integer, Food> foods;
	private List<Adiacenza> adiacenze ;
	private Graph<Food, DefaultWeightedEdge> grafo;

	public Model()
	{
		this.dao = new FoodDao();
	}

	public String creaGrafo(Integer porzioni)
	{
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		this.foods = new HashMap<Integer, Food>();

		this.dao.getVertices(foods, porzioni);
		
		//vertici 
		Graphs.addAllVertices(this.grafo, this.foods.values());
		
		//Archi 
		adiacenze = new ArrayList<>(this.dao.getArchi());
		this.calcolaArchi();
		
		return "\nVertici: " + this.grafo.vertexSet().size() + "\nArchi: " + this.grafo.edgeSet().size(); 
	} 
	
	private void calcolaArchi()
	{
		for(Adiacenza a : this.adiacenze)
		{
			Food f1 = this.foods.get(a.getFoodCode1());
			Food f2 = this.foods.get(a.getFoodCode2());
			if(f1 != null && f2 != null)
				Graphs.addEdgeWithVertices(this.grafo, f1, f2, a.getAvg());
		}
	}

	public ArrayList<Food> getFoods()
	{
		ArrayList<Food> list = new ArrayList<>(this.foods.values());
		list.sort((f1,f2)->f1.getDisplay_name().compareTo(f2.getDisplay_name()));
		return list;
	}
	
	public List<Food> getMax(Food vertice)
	{
		ArrayList<Food> adiacenti = new ArrayList<>(Graphs.neighborListOf(this.grafo, vertice));
		ArrayList<Food> max = new ArrayList<>();

		adiacenti.sort(new ComparatoreFood(grafo, vertice));
		 
		if(adiacenti.size() >= 5)
		{
			for(int i = 0; i < 5; i++)
				max.add(adiacenti.get(i));
		}
		else 
		{
			for (Food f : max)
				max.add(f);
		}
		
		
		return max;
	}
}
