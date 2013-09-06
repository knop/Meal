package com.team4.meal;

import android.app.Application;

import com.iflytek.speech.SpeechUtility;

public class TFApplication extends Application {

    @Override 
    public void onCreate() { 
        // TODO Auto-generated method stub 
        super.onCreate();
        SpeechUtility.getUtility(this).setAppid("5226b9ef");
        MealManager.instance().init(this);
    }    
    
    
}
