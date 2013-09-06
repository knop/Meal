package com.team4.meal;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


public class MealManager {

	private static final String PREFS_NAME = "MyPrefsFile";
	
	private static MealManager sInstance;
	
	private ArrayList<TFMenu> _menus;
	private TFMenu _currentMenu;
	
	private MealManager() {
		_menus = new ArrayList<TFMenu>();
	}
 	
	public static MealManager instance() {
		if (sInstance == null) {
			sInstance = new MealManager();
		} 
		
		return sInstance;
	}
	
	public ArrayList<TFMenu> getMenus() {
		return _menus;
	}
	
	public void addMenu(TFMenu m) {
		_menus.add(m);
	}
	
	public void removeMenu(TFMenu m) {
		_menus.remove(m);
	}
	
	public void selectedMenu(int index) {
		if (index >= _menus.size())
			return;
		_currentMenu = _menus.get(index);
	}
	
	public TFMenu currentMenu() {
		return _currentMenu;
	}
	
	public void restore(Context context) {
		SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
		settings.getAll();
		String mealOriginalString = settings.getString("Meal", "");
		if (mealOriginalString.length() > 0) {
			String[] meals = mealOriginalString.split(";");
			for (String s : meals) {
				Log.v("[Xiaohui]", s);
				_menus.add(new TFMenu(s));
			}
		}
	}
	
	public void save(Context context) {
		SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);  
		SharedPreferences.Editor editor = settings.edit();
		
		for (TFMenu menu : _menus) {
			Log.v("[Xiaohui]", menu.getName()+":"+menu.getMealString());
			editor.putString(menu.getName(), menu.getMealString());  
		}

		editor.commit();
	}
}
