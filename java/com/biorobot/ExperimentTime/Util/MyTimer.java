package com.biorobot.ExperimentTime.Util;

import android.app.*;
import com.biorobot.ExperimentTime.*;
import java.util.*;

public class MyTimer
{
	public Activity activity;
	public Timer mTimer;
	public MyTimerTask mMyTimerTask;
	
	public MyTimer(Activity activity){
		this.activity=activity;
	}
	
	
	public void startTimer(){
		if (mTimer != null) {
			mTimer.cancel();//void cancel() - прерывает поток таймера
		}
		
		mTimer = new Timer();
		mMyTimerTask = new MyTimerTask();
		
		// delay 1000ms, repeat in 5000ms
		//mTimer.schedule(mMyTimerTask, 1000, 5000);
		mTimer.schedule(mMyTimerTask,7000);
	}
	
	
	
	public void stopTimer(){
		if (mTimer != null) {
			mTimer.cancel();
			mTimer = null;
		}
	}
	
	
	
	
	//Запланированная к выполнению задача должна быть экземпляром класса TimerTask.
	public class MyTimerTask extends TimerTask {

		@Override
		public void run() {

			activity.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						MainActivity.viewPager.setCurrentItem(0,false);
					}
				});
		}
	}
	
	
	
}
