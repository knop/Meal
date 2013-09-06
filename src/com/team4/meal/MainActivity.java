package com.team4.meal;

import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements SensorEventListener{

	private int _shakeCount = 0;
	private SensorManager _sensorManager;
	private TextView _textView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		_textView = (TextView)findViewById(R.id.text_view);
	}

	@Override
	protected void onPause() {
		super.onPause();
		_sensorManager.unregisterListener(this);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		_sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		Sensor acceleromererSensor = _sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		_sensorManager.registerListener(this, acceleromererSensor, SensorManager.SENSOR_DELAY_NORMAL);
		_shakeCount = 0;
	}
	
	public void onClickClear(View view) {
		clearText();
	}
	
	public void onClickSetting(View view) {
		Intent intent = new Intent(this, MenusActivity.class);
		startActivity(intent);
	}
	
	private void randomMeal() {
		int count = MealManager.instance().currentMenu().getMeals().size();
		if (count > 0) {
			Random random = new Random();
			int n = random.nextInt(count);
			updateMealWithIndex(n);
		} else {
			Toast.makeText(this, "请先添加就餐地址!", Toast.LENGTH_SHORT).show();
		}		
	}
	
	private void clearText() {
		_textView.setText("");
	}
	
	private void updateMealWithIndex(int index) {
		Meal meal = MealManager.instance().currentMenu().getMeals().get(index);
		_textView.setText(meal.toString());
	}
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		float[] values = event.values;
		if(Math.abs(values[0])>12
				||Math.abs(values[1])>12
				||Math.abs(values[2])>12){
			if (++_shakeCount == 2) {
				_shakeCount = 0;
				Log.v("[Xiaohui]", "randomMeal");
				randomMeal();
			}
		}
	}
}
