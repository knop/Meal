package com.team4.meal;

import java.util.ArrayList;

public class TFMenu {

	private ArrayList<Meal> _meals;
	private String _name;

	public TFMenu(String name) {
		setName(name);
		_meals = new ArrayList<Meal>();
	}
	
	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}
	
	public ArrayList<Meal> getMeals() {
		return _meals;
	}

	public void setMeals(ArrayList<Meal> meals) {
		_meals = meals;
	}	
	
	public void addMeal(Meal meal) {
		_meals.add(meal);
	}
	
	public void removeMeal(Meal meal) {
		_meals.remove(meal);
	}
	
	public String getMealString() {
		String mealString = "";
		int mealSize = _meals.size();
		for (int i=0; i<mealSize; i++) {
			Meal meal = _meals.get(i);
			mealString += meal.toString() + ";";
		}
		
		return mealString;
	}
	
	public String toString() {
		return _name;
	}
	
}
