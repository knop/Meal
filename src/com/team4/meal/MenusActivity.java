package com.team4.meal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class MenusActivity extends Activity implements OnItemClickListener{
	
	private final static int ITEM_SELECT = 1;
	private final static int ITEM_DELETE = 2;
	
	private ListView _listView;
	private EditText _editText;
	private ArrayAdapter<TFMenu> _adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menus);
		
		_listView = (ListView)findViewById(R.id.list_view);
		_editText = (EditText)findViewById(R.id.edit_text);

		_adapter = new ArrayAdapter<TFMenu>(this, 
				android.R.layout.simple_expandable_list_item_1, 
				MealManager.instance().getMenus());
		_listView.setAdapter(_adapter);
		_listView.setOnItemClickListener(this);
		registerForContextMenu(_listView);
	}
	
	public void onClickAdd(View view) {
		String text = _editText.getText().toString();
		if (text.length() > 0) {
			TFMenu menu = new TFMenu(text);
			MealManager.instance().addMenu(menu);
			_adapter.notifyDataSetChanged();
			_editText.getText().clear();
		}
	}
	
    // 长按菜单响应函数
    public boolean onContextItemSelected(MenuItem item) {
    	AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
    	int index = info.position;
    	switch(item.getItemId()){
    	case ITEM_SELECT:
    		MealManager.instance().selectedMenu(index);
    		_adapter.notifyDataSetChanged();
    		break;
    	case ITEM_DELETE:
    		 TFMenu menu = MealManager.instance().getMenus().get(index);
    		 MealManager.instance().removeMenu(menu);
    		 _adapter.notifyDataSetChanged();
    		 break; 
    	}
    	
    	return super.onContextItemSelected(item);
    }
    
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
    	menu.add(Menu.NONE, ITEM_SELECT, Menu.NONE, "选择");
        menu.add(Menu.NONE, ITEM_DELETE, Menu.NONE, "删除");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = new Intent(this, MealsActivity.class);
		intent.putExtra("menu_index", position);
		startActivity(intent);
	}
}
