package com.team4.meal;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class MealsActivity extends Activity {
	
	private ListView _listView;
	private EditText _editText;
	private ArrayAdapter<Meal> _adapter;
	private TFMenu _menu;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_meals);
		
		_listView = (ListView)findViewById(R.id.list_view);
		_editText = (EditText)findViewById(R.id.edit_text);

		int menuIndex = getIntent().getIntExtra("menu_index", 0);
		
		_menu = MealManager.instance().getMenus().get(menuIndex);
		
		_adapter = new ArrayAdapter<Meal>(this, 
				android.R.layout.simple_expandable_list_item_1, 
				_menu.getMeals());
		_listView.setAdapter(_adapter);
		registerForContextMenu(_listView);
	}

	public void onClickAdd(View view) {
		String text = _editText.getText().toString();
		if (text.length() > 0) {
			Meal meal = new Meal(text);
			_adapter.add(meal);
			MealManager.instance().save(this);
			_editText.getText().clear();
		}
	}
	
    // 长按菜单响应函数
    public boolean onContextItemSelected(MenuItem item) {
    	switch(item.getItemId()){
    	case 1:
    		 AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
    		 Meal meal = _menu.getMeals().get(info.position);
    		 _adapter.remove(meal);
    		 MealManager.instance().save(this);
    		 break; 
    	}
    	
    	return super.onContextItemSelected(item);
    }
    
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        menu.add(Menu.NONE, 1, Menu.NONE, "删除");
        super.onCreateContextMenu(menu, v, menuInfo);
    }
	
}
