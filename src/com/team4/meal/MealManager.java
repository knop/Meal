package com.team4.meal;

import java.util.ArrayList;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


public class MealManager {

	private static final String PREFS_NAME = "MyPrefsFile";
	
	private static MealManager sInstance;
	
	private Context _context;
	SharedPreferences _settings;
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
	
	public void init(Context context) {
		_context = context;
		_settings = _context.getSharedPreferences(PREFS_NAME, 0);
		restore();
	}
	
	public ArrayList<TFMenu> getMenus() {
		return _menus;
	}
	
	public void addMenu(TFMenu m) {
		updateMenu(m);
		_menus.add(m);
	}
	
	public void removeMenu(TFMenu m) {
		SharedPreferences.Editor editor = _settings.edit();
		editor.remove(m.getName());
		editor.commit();
		_menus.remove(m);
	}
	
	public void updateMenu(TFMenu m) {
		SharedPreferences.Editor editor = _settings.edit();
		Log.v("[Xiaohui]", m.getName()+":"+m.getMealString());
		editor.putString(m.getName(), m.getMealString());
		editor.commit();
	}
	
	public void selectedMenu(int index) {
		if (index >= _menus.size())
			return;
		for (int i=0; i<_menus.size(); i++) {
			TFMenu menu = _menus.get(i);
			menu.select(index == i);
		}
		_currentMenu = _menus.get(index);
	}
	
	public TFMenu currentMenu() {
		return _currentMenu;
	}
	
	public void restore() {
		Map<String,?> keys = _settings.getAll();
		for(Map.Entry<String,?> entry : keys.entrySet()){
			TFMenu menu = new TFMenu(entry.getKey());
			menu.restoreMeals(entry.getValue().toString());
			_menus.add(menu);        
		 }
		selectedMenu(0);
	}
	
	public void save() {
		SharedPreferences.Editor editor = _settings.edit();
		for (TFMenu menu : _menus) {
			Log.v("[Xiaohui]", menu.getName()+":"+menu.getMealString());
			editor.putString(menu.getName(), menu.getMealString()); 
		}
		editor.commit();
	}
}
