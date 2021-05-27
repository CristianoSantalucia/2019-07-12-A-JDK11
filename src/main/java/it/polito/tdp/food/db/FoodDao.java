package it.polito.tdp.food.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.food.model.Adiacenza;
import it.polito.tdp.food.model.Condiment;
import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.Portion;

public class FoodDao
{
	public List<Food> listAllFoods()
	{
		String sql = "SELECT * FROM food";
		try
		{
			Connection conn = DBConnect.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);

			List<Food> list = new ArrayList<>();

			ResultSet res = st.executeQuery();

			while (res.next())
			{
				try
				{
					list.add(new Food(res.getInt("food_code"), res.getString("display_name")));
				}
				catch (Throwable t)
				{
					t.printStackTrace();
				}
			}

			conn.close();
			return list;

		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return null;
		}

	}

	public List<Condiment> listAllCondiments()
	{
		String sql = "SELECT * FROM condiment";
		try
		{
			Connection conn = DBConnect.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);

			List<Condiment> list = new ArrayList<>();

			ResultSet res = st.executeQuery();

			while (res.next())
			{
				try
				{
					list.add(new Condiment(res.getInt("condiment_code"), res.getString("display_name"),
							res.getDouble("condiment_calories"), res.getDouble("condiment_saturated_fats")));
				}
				catch (Throwable t)
				{
					t.printStackTrace();
				}
			}

			conn.close();
			return list;

		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public List<Portion> listAllPortions()
	{
		String sql = "SELECT * FROM portion";
		try
		{
			Connection conn = DBConnect.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);

			List<Portion> list = new ArrayList<>();

			ResultSet res = st.executeQuery();

			while (res.next())
			{
				try
				{
					list.add(new Portion(res.getInt("portion_id"), res.getDouble("portion_amount"),
							res.getString("portion_display_name"), res.getDouble("calories"),
							res.getDouble("saturated_fats"), res.getInt("food_code")));
				}
				catch (Throwable t)
				{
					t.printStackTrace();
				}
			}

			conn.close();
			return list;

		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return null;
		}

	}
	
	public void getVertices(Map<Integer, Food> map, Integer porzioni)
	{
		String sql = "SELECT DISTINCT(f.food_code), f.display_name "
					+ "FROM food_pyramid_mod.portion AS p, food_pyramid_mod.food AS f "
					+ "WHERE p.food_code = f.food_code AND p.portion_amount <= ?";
		try
		{
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, porzioni);
			
			ResultSet res = st.executeQuery();

			while (res.next())
			{
				try
				{
					if(!map.containsKey(res.getInt("food_code")))
					{
						Food f = new Food(res.getInt("food_code"), res.getString("display_name")); 
						map.put(f.getFood_code(), f);
					}
				}
				catch (Throwable t)
				{
					t.printStackTrace();
				}
			}

			conn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public List<Adiacenza> getArchi()
	{
		String sql = "SELECT fc1.food_code AS c1, fc2.food_code AS c2, "
					+ "		COUNT(*) AS qnt, "
					+ "		AVG(c.condiment_calories) AS avg "
					+ "FROM food_pyramid_mod.food_condiment AS fc1, "
					+ "	  food_pyramid_mod.food_condiment AS fc2, "
					+ "	  food_pyramid_mod.condiment AS c "
					+ "WHERE fc1.condiment_code = fc2.condiment_code "
					+ "		AND fc1.food_code < fc2.food_code "
					+ "		AND c.condiment_code = fc1.condiment_code "
					+ "GROUP BY fc1.food_code, fc2.food_code "
					+ "ORDER BY fc1.food_code";
		try
		{
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			List<Adiacenza> list = new ArrayList<>();
			
			ResultSet res = st.executeQuery();

			while (res.next())
			{
				try
				{
					 Adiacenza a = new Adiacenza(res.getInt("c1"), res.getInt("c2"), res.getDouble("c2"));
					 list.add(a);
				}
				catch (Throwable t)
				{
					t.printStackTrace();
				}
			}

			conn.close();
			return list;

		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return null;
		}

	}
}
