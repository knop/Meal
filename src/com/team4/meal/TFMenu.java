package com.team4.meal;

import java.util.ArrayList;

import android.util.Log;

public class TFMenu {

	private ArrayList<TFMeal> _meals;
	private String _name;
	private boolean _isSelected;

	public TFMenu(String name) {
		setName(name);
		_meals = new ArrayList<TFMeal>();
	}
	
	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}
	
	public ArrayList<TFMeal> getMeals() {
		return _meals;
	}

	public void setMeals(ArrayList<TFMeal> meals) {
		_meals = meals;
	}	
	
	public void addMeal(TFMeal meal) {
		_meals.add(meal);
	}
	
	public void removeMeal(TFMeal meal) {
		_meals.remove(meal);
		MealManager.instance().updateMenu(this);
	}
	
	public void restoreMeals(String mealString) {
		if (mealString.length() > 0) {
			String[] meals = mealString.split(";");
			for (String s : meals) {
				Log.v("[Xiaohui]", s);
				addMeal(new TFMeal(s));
			}
		}
	}
	
	public String getMealString() {
		String mealString = "";
		int mealSize = _meals.size();
		for (int i=0; i<mealSize; i++) {
			TFMeal meal = _meals.get(i);
			mealString += meal.toString() + ";";
		}
		
		return mealString;
	}
	
	public void select(boolean selected) {
		_isSelected = selected;
	}
	
	public String toString() {
		if (_isSelected)
			return "*"+_name;
		return _name;
	}
	
}
