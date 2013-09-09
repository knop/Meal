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
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.iflytek.speech.ErrorCode;
import com.iflytek.speech.ISpeechModule;
import com.iflytek.speech.InitListener;
import com.iflytek.speech.SpeechConstant;
import com.iflytek.speech.SpeechSynthesizer;
import com.iflytek.speech.SynthesizerListener;

public class MainActivity extends Activity implements SensorEventListener{

	private int _shakeCount = 0;
	private SensorManager _sensorManager;
	private TextView _textView;
	private TextView _tvMenuName;
	
	private SpeechSynthesizer _iFlyTTS;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		_textView = (TextView)findViewById(R.id.text_view);
		_tvMenuName = (TextView)findViewById(R.id.text_view_menu);
		
		_iFlyTTS = new SpeechSynthesizer(this, new InitListener() {

			@Override
			public void onInit(ISpeechModule arg0, int code) {
				Log.d("[Xiaohui]", "InitListener init() code = " + code);
	        	if (code == ErrorCode.SUCCESS) {
//	        		((Button)findViewById(R.id.tts_play)).setEnabled(true);
	        	}
			}
	    });
	}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        _iFlyTTS.stopSpeaking(mTtsListener);
        // 退出时释放连接
        _iFlyTTS.destory();
    }
	
	@Override
	protected void onPause() {
		super.onPause();
		unregisterSensor();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		TFMenu menu = MealManager.instance().currentMenu();
		if (menu == null)
			return;
		_tvMenuName.setText(menu.getName());
		registerSensor();
	}
	
	private void registerSensor() {
		if (_sensorManager == null)
			_sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		Sensor acceleromererSensor = _sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		_sensorManager.registerListener(this, acceleromererSensor, SensorManager.SENSOR_DELAY_NORMAL);
		_shakeCount = 0;
	}
	
	private void unregisterSensor() {
		if (_sensorManager == null)
			return;
		_sensorManager.unregisterListener(this);
	}
	
	public void onClickClear(View view) {
		registerSensor();
		clearText();
	}
	
	public void onClickSetting(View view) {
		Intent intent = new Intent(this, MenusActivity.class);
		startActivity(intent);
	}
	
	private void randomMeal() {
		unregisterSensor();
		TFMenu menu = MealManager.instance().currentMenu();
		if (menu == null || menu.getMeals().size() <= 0)
			Toast.makeText(this, "请先添加就餐地址!", Toast.LENGTH_SHORT).show();
		int count = menu.getMeals().size();
		if (count > 0) {
			Random random = new Random();
			int n = random.nextInt(count);
			updateMealWithIndex(n);
		}
	}
	
	private void clearText() {
		_textView.setText("");
	}
	
	private void updateMealWithIndex(int index) {
		TFMenu menu = MealManager.instance().currentMenu();
		TFMeal meal = menu.getMeals().get(index);
		String text = meal.toString();
		_textView.setText(text);
		speak(menu.getName() + "去吃" + text + ",不准再摇!");
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
	
	private void speak(String text) {
		_iFlyTTS.setParameter(SpeechConstant.ENGINE_TYPE, SpeechSynthesizer.TTS_ENGINE_TYPE_LOCAL);
		_iFlyTTS.setParameter(SpeechSynthesizer.VOICE_NAME, SpeechSynthesizer.CLOUD_TTS_ROLE_XIAOYU);
		_iFlyTTS.setParameter(SpeechSynthesizer.SPEED, "50");
		_iFlyTTS.setParameter(SpeechSynthesizer.PITCH, "50");
		int code = _iFlyTTS.startSpeaking(text, mTtsListener);
		if(code != 0) {
			Log.d("[Xiaohui]", "start speak error : " + code);
		} else {
			Log.d("[Xiaohui]", "start speak success.");
		}
	}
	
    /**
     * 合成回调监听。
     */

    private SynthesizerListener mTtsListener = new SynthesizerListener.Stub() {
    	
        @Override
        public void onBufferProgress(int progress) throws RemoteException {
        	Log.d("[Xiaohui]", "onBufferProgress :" + progress);
        }

        @Override
        public void onCompleted(int code) throws RemoteException {
            Log.d("[Xiaohui]", "onCompleted code =" + code);
        }

        @Override
        public void onSpeakBegin() throws RemoteException {
            Log.d("[Xiaohui]", "onSpeakBegin");
        }

        @Override
        public void onSpeakPaused() throws RemoteException {
        	 Log.d("[Xiaohui]", "onSpeakPaused.");
        }

        @Override
        public void onSpeakProgress(int progress) throws RemoteException {
        	Log.d("[Xiaohui]", "onSpeakProgress :" + progress);
        }

        @Override
        public void onSpeakResumed() throws RemoteException {
        	Log.d("[Xiaohui]", "onSpeakResumed.");
        }
    };
}
