package com.biorobot.ExperimentTime.Util;

import android.app.*;
import android.view.*;

public class ShowHideSystemUI
{

	static View decorView;
	static Activity activity;

	public ShowHideSystemUI(Activity activity){
		this.activity=activity;
		decorView= activity. getWindow().getDecorView();
	}


	//скрываем/показываем панели андроид
	public static void showHideSystemUI(boolean showHideSystemUI){

		if(showHideSystemUI){//скрываем
		    //создадим flag
			final int flagsHide = 
				View.SYSTEM_UI_FLAG_LAYOUT_STABLE
				| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
				| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
				| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
				| View.SYSTEM_UI_FLAG_FULLSCREEN
				| View.SYSTEM_UI_FLAG_LOW_PROFILE
				| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
				;

			//применим переменную flag для скрытия
			decorView.setSystemUiVisibility(flagsHide);

		}else{//показываем	 
		    //создадим flag
			final int flagsShow=
				View.SYSTEM_UI_FLAG_LAYOUT_STABLE
				//| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
				//| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
				;

			//применим переменную flag для показа
			decorView.setSystemUiVisibility(flagsShow);

		}

	}//скрываем/показываем панели андроид






}
