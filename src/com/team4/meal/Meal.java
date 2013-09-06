package com.team4.meal;

public class Meal {

	private String _name;

	public Meal(String name) {
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
