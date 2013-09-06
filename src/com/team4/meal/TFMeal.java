package com.team4.meal;

public class TFMeal {

	private String _name;

	public TFMeal(String name) {
		setName(name);
	}
	
	public String getName() {
		return _name;
	}

	public void setName(String name) {
		this._name = name;
	}
	
	public String toString() {
		return _name;
	}
}
