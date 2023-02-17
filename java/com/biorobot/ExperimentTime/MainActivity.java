package com.biorobot.ExperimentTime;


import android.app.*;
import android.os.*;
import android.view.*;
import androidx.viewpager2.widget.*;
import com.biorobot.ExperimentTime.Util.*;


public class MainActivity extends Activity 
{

	public static ViewPager2 viewPager;
	public static ViewPagerAdapter viewPagerAdapter;
	ShowHideSystemUI showHideSystemUI;
	int page;
	public static MyTimer myTimer;

	String str1="";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

		//полноэкранный режим
		showHideSystemUI=new ShowHideSystemUI(this);
		showHideSystemUI. showHideSystemUI(true);

		//запрещаем экрану активности отключаться
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


		//находим viewPager2:
		viewPager= findViewById(R.id.viewPager);
		//создаём адаптер:
		viewPagerAdapter=new ViewPagerAdapter(this);
		//назначаем ViewPager2 адаптер:
		viewPager.setAdapter(viewPagerAdapter);
		//регистрируем Listener
		viewPager.registerOnPageChangeCallback(onPageChangeCallback);

		myTimer=new MyTimer(this);
    }





	@Override
	protected void onResume()
	{
		// TODO: Implement this method
		viewPager.setCurrentItem(0,false);
		super.onResume();
	}












	ViewPager2.OnPageChangeCallback onPageChangeCallback = new ViewPager2.OnPageChangeCallback() {

		@Override
		// Этот метод срабатывает, когда для текущей страницы есть какая-либо активность прокрутки.
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { 
			super.onPageScrolled(position, positionOffset, positionOffsetPixels); 
			//TODO: Implement this method
			findView();//находим элементы разметок
		} 



		@Override
        // срабатывает при выборе новой страницы
		public void onPageSelected(int position) { 
			super.onPageSelected(position); 
			//Toaster.toast(mGPSActivity,"onPageSelected");
			//TODO: Implement this method
			page=position;//делаем видимой переменную для других методов 
			findView();//находим элементы разметок

			viewPagerAdapter. notifyDataSetChanged();

			switch(page){
				case 0:
					//обновляем view в разметках 
					//(этот метод вызывается из GnssStatusCallback, и отсюда, при листании стр. А также вызовется при onResume())
					update();
					myTimer.stopTimer();
					break;
			}
		} 
	};



	public void findView(){}

	public void update(){}



}
