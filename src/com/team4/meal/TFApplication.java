package com.team4.meal;

import android.app.Application;

public class TFApplication extends Application {

    @Override 
    public void onCreate() { 
        // TODO Auto-generated method stub 
        super.onCreate();
        
        MealManager.instance().init(this);
    }    
    
    
}
