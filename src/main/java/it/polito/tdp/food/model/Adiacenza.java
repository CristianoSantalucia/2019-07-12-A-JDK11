package it.polito.tdp.food.model;

public class Adiacenza
{
	private Integer foodCode1;
	private Integer foodCode2;
	private Double avg;
	public Adiacenza(Integer foodCode1, Integer foodCode2, Double avg)
	{
		super();
		this.foodCode1 = foodCode1;
		this.foodCode2 = foodCode2;
		this.avg = avg;
	}
	public Integer getFoodCode1()
	{
		return foodCode1;
	}
	public void setFoodCode1(Integer foodCode1)
	{
		this.foodCode1 = foodCode1;
	}
	public Integer getFoodCode2()
	{
		return foodCode2;
	}
	public void setFoodCode2(Integer foodCode2)
	{
		this.foodCode2 = foodCode2;
	}
	public Double getAvg()
	{
		return avg;
	}
	public void setAvg(Double avg)
	{
		this.avg = avg;
	}
	@Override public String toString()
	{
		return "Adiacenza [foodCode1=" + foodCode1 + ", foodCode2=" + foodCode2 + ", avg=" + avg + "]";
	}
}
