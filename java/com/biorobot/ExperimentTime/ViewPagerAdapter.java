package com.biorobot.ExperimentTime;



import android.app.*;
import android.content.*;
import android.icu.util.*;
import android.os.*;
import android.text.*;
import android.view.*;
import android.view.animation.*;
import android.view.animation.Animation.AnimationListener;
import android.widget.*;
import android.widget.Chronometer.*;
import androidx.recyclerview.widget.*;
import com.biorobot.ExperimentTime.*;
import com.biorobot.ExperimentTime.Util.*;
import android.graphics.*;


public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewHolder> implements SharedConstants
{
	int page;
	String str1="";
	String seconds="";
	int year=-1;
	int month=-1;
	int day=-1;
	Activity activity;
	// имя настройки
	public static final String APP_PREFERENCES = "mysettings";
	//ключи, для Б.Д.
	public static final String APP_PREFERENCES_YEAR = "Year";
	public static final String APP_PREFERENCES_MONTH = "Month";
	public static final String APP_PREFERENCES_DAY = "Day";
	static SharedPreferences mSettings;
    boolean isTouch;
	int timeDelay;
	int pageNumber;


	public ViewPagerAdapter(Activity activity){
		this.activity=activity;
		isTouch=true;
	}

	//Вызывается, когда RecyclerView требуется новый RecyclerView.ViewHolder заданного типа для представления элемента.
	@Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view=LayoutInflater.from(parent.getContext()).inflate(layoutAdapterPage[viewType], parent, false);
		return new ViewHolder(view);
    }



	//Возвращает тип представления элемента в позиции для повторного использования представления.
	@Override
    public int getItemViewType(int position) {
		page=position;
		return position;
    }


	//Вызывается RecyclerView для отображения данных в указанной позиции.
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {



		switch(page){
			case 0://первая страница
				final RelativeLayout relativeLayout=holder.relativeLayout;
				//relativeLayout.setOnClickListener((View.OnClickListener) oncl);
				final EditText editTextYear=holder.editTextYear;
				final EditText editTextMonth=holder.editTextMonth;
				final EditText editTextDay=holder.editTextDay;
				final TextView textView=holder.textView;
				TextView textView2=holder.textView2;
				final TextView textView3=holder.textView3;
				final Button button=holder.button;

				final Chronometer chronometer=holder.chronometer;
				chronometer.setOnChronometerTickListener(new myCh(textView,textView3));



				relativeLayout.setOnTouchListener((View.OnTouchListener)OnTouch);







				//достаем значения из настроек
				getPref();
				//и если они не равны -1
				if(year!=-1 && month!=-1 && day!=-1){
					editTextYear.setText(String.valueOf(year));
					editTextMonth.setText(String.valueOf(month+1));//для методов считалось от 0, и запомнили в настройках число для методов, оно на 1 меньше. Поэтому если мы хотим посмотреть введенное число снова, надо прибавить 1
					editTextDay.setText(String.valueOf(day));
					//запускаем хронометр
					//посчитаем отрезок времени, в мс:
					long initialTime=getInitialTime(year,month,day);
					//отправляем на вход методу отрезок времени в мс:
					//найдем разность, чтобы отобразить прошедшее время назад
					chronometer.setBase(SystemClock.elapsedRealtime()-initialTime);//вот, так покажет с разностью времени последней фиксации (плюс будет тикать время, прибавляться)
					//chronometer.setBase(SystemClock.elapsedRealtime());//а так, показал бы от нуля. И тикало/прибавлялось бы, от нуля
					//запустим чтобы тикало:
					chronometer.start();

					button.setEnabled(false);
					button.setVisibility(View.INVISIBLE);
					editTextYear.setEnabled(false);
					editTextMonth.setEnabled(false);
					editTextDay.setEnabled(false);
				}




				button.setBackgroundResource(R.drawable.states_shapes);

				button.setOnClickListener(new View.OnClickListener(){

						@Override
						public void onClick(View p1)
						{
							// TODO: Implement this method
							String strYear=editTextYear.getText().toString();
							String strMonth=editTextMonth.getText().toString();
							String strDay=editTextDay.getText().toString();


							//если все три строки не пусты
							if(!strYear.isEmpty()&
							   !strMonth.isEmpty()&
							   !strDay.isEmpty()){



								year=Integer.valueOf(strYear);
								month=  getMounth( Integer.valueOf(strMonth) );
								day=Integer.valueOf(strDay);

								//посчитаем отрезок времени, в мс:
								long initialTime=getInitialTime(year,month,day);
								//отправляем на вход методу отрезок времени в мс:
								//найдем разность, чтобы отобразить прошедшее время назад
								chronometer.setBase(SystemClock.elapsedRealtime()-initialTime);//вот, так покажет с разностью времени последней фиксации (плюс будет тикать время, прибавляться)
								//chronometer.setBase(SystemClock.elapsedRealtime());//а так, показал бы от нуля. И тикало/прибавлялось бы, от нуля
								//запустим чтобы тикало:
								chronometer.start();

								button.setEnabled(false);
								button.setVisibility(View.INVISIBLE);
								editTextYear.setEnabled(false);
								editTextMonth.setEnabled(false);
								editTextDay.setEnabled(false);


								//положим в Б.Д. введенные с клавиатуры значения
								putInt(year,month,day);


							}////если все три строки не пусты
							else{
								//иначе, какие-то строки пусты
								Toaster.toast(activity,"Заполните данные");
							}



							Vibration.vibrate(activity);
						}
					});


				Spanned textSpan=getTextBottom();
				textView2.setText(textSpan);

				break;


			//первая последовательность
			case 1:
				TextView textViewPage1=holder.textViewPage_1;
				textViewPage1.setOnTouchListener((View.OnTouchListener)OnTouch);
				to_another_page(textViewPage1,2,2000);
				break;



			case 2:
				TextView textViewPage2=holder.textViewPage_2;
				textViewPage2.setOnTouchListener((View.OnTouchListener)OnTouch);
				to_another_page(textViewPage2,3,2000);
				break;



			case 3:
				TextView textViewPage3=holder.textViewPage_3;
				textViewPage3.setOnTouchListener((View.OnTouchListener)OnTouch);
				to_another_page(textViewPage3,4,2500);
				break;


			case 4:
				TextView textViewPage4=holder.textViewPage_4;
				textViewPage4.setOnTouchListener((View.OnTouchListener)OnTouch);
				timeDelay=500;
				pageNumber=4;
				anim(textViewPage4,timeDelay,pageNumber);
				to_another_page(textViewPage4,0,3000);
				break;

				
				
				
			//вторая последовательность
			case 5:
				TextView textViewPage5=holder.textViewPage_5;
				textViewPage5.setOnTouchListener((View.OnTouchListener)OnTouch);
				to_another_page(textViewPage5,6,1500);
				break;

			case 6:
				TextView textViewPage6=holder.textViewPage_6;
				textViewPage6.setOnTouchListener((View.OnTouchListener)OnTouch);
				to_another_page(textViewPage6,7,1500);
				break;


			case 7:
				TextView textViewPage7=holder.textViewPage_7;
				textViewPage7.setOnTouchListener((View.OnTouchListener)OnTouch);
				to_another_page(textViewPage7,8,3000);
				break;
				
				
			case 8:
				TextView textViewPage8=holder.textViewPage_8;
				textViewPage8.setOnTouchListener((View.OnTouchListener)OnTouch);
				to_another_page(textViewPage8,0,5000);
				break;
				
		}

	}


	


	View.OnTouchListener OnTouch=new View.OnTouchListener(){

		@Override
		public boolean onTouch(View p1, MotionEvent p2)
		{
			// TODO: Implement this method
			//Toaster.toast(activity,"Test");
			isTouch=false;
			return false;
		}
	};


	//android:repeatCount="infinite"
	public class MyAnimationListener implements AnimationListener
	{

		TextView textView;
		int count=0;
		int _page;

		public MyAnimationListener(TextView textView, int _page){
			this.textView=textView;
			this._page=_page;
		}

		@Override
		public void onAnimationStart(Animation p1)
		{
			// TODO: Implement this method
			if(_page==4){
				textView.setBackgroundColor(Color.parseColor("#ffffff"));
				textView.setTextColor(Color.parseColor("#000000"));
			}


		}

		@Override
		public void onAnimationEnd(Animation p1)
		{
			// TODO: Implement this method
			textView.startAnimation(p1);
		}

		@Override
		public void onAnimationRepeat(Animation p1)
		{
			// TODO: Implement this method

		}


	}








	public void anim(final TextView textView, int timeDelay, int _page){



		if(_page==4){
			textView.setBackgroundColor(Color.parseColor("#000000"));
			textView.setTextColor(Color.parseColor("#ff0000"));
		}

		

		if(isTouch){
			// Анимация
			final Animation myAnimation = AnimationUtils.loadAnimation(activity, R.anim.my_anim);
			myAnimation.setAnimationListener(new MyAnimationListener(textView,_page));

			if(textView!=null){
				textView.postDelayed(new Runnable(){

						@Override
						public void run()
						{
							// TODO: Implement this method
							// Подключаем анимацию к нужному View
							textView.startAnimation(myAnimation);

						}
					},timeDelay);
			}

		}
	}




	


	public void to_another_page(TextView textView,  final int pageNumber, int duration){
        if(isTouch){//if(isTouch)
			if(textView!=null){
				textView.postDelayed(new Runnable(){

						@Override
						public void run()
						{
							// TODO: Implement this method
							MainActivity.viewPager.setCurrentItem(pageNumber,false);
						}
					},duration);
			}
		}//if(isTouch)

		if(!isTouch){
			//стартуем новый таймер (предыдущий, здесь, этой строкой запущенный обнулится/удалится)
			MainActivity.myTimer.startTimer();
		}

	}







	//SystemClock.elapsedRealtime() //Возвращает миллисекунды с момента загрузки, включая время, проведенное в спящем режиме.
	//Chronometer getBase() //Верните базовое время, установленное с помощью setBase(long).
	public class myCh implements OnChronometerTickListener
	{
		TextView textView;
		TextView textView3;

		public myCh(TextView textView, TextView textView3){
			this.textView=textView;
			this.textView3=textView3;
		}

		@Override
		public void onChronometerTick(final Chronometer cArg) {
			long time = SystemClock.elapsedRealtime() - cArg.getBase();

			int year=getYear(time);//количество полных лет
			int month= getMonth(time);//количество месяцев в неполном годе
			int day=getDay(time);
			int hour=getHour(time);


			//установим формат отображаемого времени
			int h   = (int)(time /3600000);
			int m = (int)(time - h*3600000)/60000;
			int s= (int)(time - h*3600000- m*60000)/1000 ;
			final String hh = h < 10 ? "0"+h: h+"";
			final String mm = m < 10 ? "0"+m: m+"";
			final String ss = s < 10 ? "0"+s: s+"";

			seconds=ss;
			String text=year+" лет   "+month+" месяцев   "+day+" дней"+"\n"+
				hour+" часов "+mm+" минут "+ss+" секунд";//+"\n\n"+

			//"______________________"+
			//hh+" часов "+mm+" минут "+ss+" секунд" +" назад";

			str1=hh+" часов "+mm+" минут "+ss+" секунд" +" назад";
			cArg.setText(text);

			textView.setText(str1);

			String strTimeNow="Время сейчас: "+getTime();
            textView3.setText(strTimeNow);

			
			 
			

			if(seconds.equals("00")){
				MainActivity.viewPager.setCurrentItem(1,false);
				isTouch=true;
			}


			if(seconds.equals("30")){
				MainActivity.viewPager.setCurrentItem(5,false);
				isTouch=true;
			}

			
		}


	}














	public void putInt(int mYear, int mMonth, int mDay){
		//сохраняем в настройках
		mSettings = activity.getSharedPreferences(APP_PREFERENCES, activity.MODE_PRIVATE);
		SharedPreferences.Editor prefEdit = mSettings.edit();
		prefEdit.putInt(APP_PREFERENCES_YEAR, mYear);
		prefEdit.commit();
		prefEdit.putInt(APP_PREFERENCES_MONTH, mMonth);
		prefEdit.commit();
		prefEdit.putInt(APP_PREFERENCES_DAY, mDay);
		prefEdit.commit();
		//сохраняем в настройках
	}


	public void getPref(){
		//из настроек достаем значения переменных, и инициализируем
		mSettings = activity.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
		year= mSettings.getInt(APP_PREFERENCES_YEAR,-1);
		month= mSettings.getInt(APP_PREFERENCES_MONTH,-1);
		day= mSettings.getInt(APP_PREFERENCES_DAY,-1);
	}

	//возвращает отрезок времени, в мс
	//от введенного с клавиатуры времени, до текущего времени
	public long getInitialTime(int year, int month, int day){
		Calendar calendar = new GregorianCalendar(year,month,day);
		long timeInMillis = calendar.getTimeInMillis();
		return  (System.currentTimeMillis() - timeInMillis);
	}


   
	public Spanned getTextBottom(){
		String htmlTaggedString  = "<u>⇨ Отключите техсредства эксперимента!!! ⇦</u>";
		Spanned textSpan  =  android.text.Html.fromHtml(htmlTaggedString);
		return textSpan;
	}

	



	// принимает на вход время отрезка, выраженное в мс
	// возвращает число полных лет, содержащихся в этом отрезке
	public int getYear(long initialTime){

		//initialTime, время выраженное в мс.
		//в 1 секунде, содержится 1000 мс
		//тогда:
		//initialTime/1000, это секунды
		//initialTime/1000/60, это минуты
		//initialTime/1000/60/60, (или time/3 600 000) это часы

		int hour=(int)(initialTime/3600000);//столько часов в этом отрезке
		int hourInyear=8776;//В обычном году - 8 760 часов, в високосном - 8 784 часа

		int year=hour/hourInyear;
		return year;
	}






	// принимает на вход время отрезка, выраженное в мс
	// возвращает число полных месяцев, содержащихся в неполном годе
	public int getMonth(long initialTime){
		double hour=initialTime/3600000;//столько часов в этом отрезке
		//количество часов в году, положим таким:
		double hourInyear=8776;//В обычном году - 8 760 часов, в високосном - 8 784 часа
        //выясним число лет, с дробью
		double year=hour/hourInyear;
		//отхватим дробную часть (было например 1.5 года, отхватили дробную часть: 0.5 года)
		double fractional_part_year=fractional_part_of_numbers(year);//дробная часть числа
		//мы это вообще делаем, выяснить количество месяцев в неполном годе
		//вот например:
		//12*0.5=6  месяцев
		//12*1  =12 месяцев
		//теперь проделаем эту операцию:
		double month=12*fractional_part_year;
		//округлим в меньшую сторону
		int monthResult=(int)Math.floor(month);

		return monthResult;
	}



	// принимает на вход время отрезка, выраженное в мс
	//возвращает число дней в неполном месяце
	public int getDay(long initialTime){

		double hour=initialTime/3600000;//столько часов в этом отрезке
		//количество часов в году, положим таким:
		double hourInyear=8776;//В обычном году - 8 760 часов, в високосном - 8 784 часа
        //выясним число лет, с дробью
		double year=hour/hourInyear;
		//отхватим дробную часть (было например 1.5 года, отхватили дробную часть: 0.5 года)
		double fractional_part_year=fractional_part_of_numbers(year);//дробная часть числа
		//мы это вообще делаем, выяснить количество месяцев в неполном годе
		//вот например:
		//12*0.5=6  месяцев
		//12*1  =12 месяцев
		//теперь проделаем эту операцию:
		double month=12*fractional_part_year;
		//отхватим дробную часть
		double fractional_part_month=fractional_part_of_numbers(month);//дробная часть числа (неполный месяц)

		double day=30.417*fractional_part_month;
		//округлим в меньшую сторону
		int dayResult=(int)Math.floor(day);

		return dayResult;
	}



	// принимает на вход время отрезка, выраженное в мс
	//возвращает число часов в неполных сутках
	public int getHour(long initialTime){

		double hour=initialTime/3600000;//столько часов в этом отрезке
		//количество часов в году, положим таким:
		double hourInyear=8776;//В обычном году - 8 760 часов, в високосном - 8 784 часа
        //выясним число лет, с дробью
		double year=hour/hourInyear;
		//отхватим дробную часть (было например 1.5 года, отхватили дробную часть: 0.5 года)
		double fractional_part_year=fractional_part_of_numbers(year);//дробная часть числа
		//мы это вообще делаем, выяснить количество месяцев в неполном годе
		//вот например:
		//12*0.5=6  месяцев
		//12*1  =12 месяцев
		//теперь проделаем эту операцию:
		double month=12*fractional_part_year;
		//отхватим дробную часть (неполный месяц)
		double fractional_part_month=fractional_part_of_numbers(month);//дробная часть числа (неполный месяц)

		double day=30.417*fractional_part_month;

		//отхватим дробную часть (неполный день)
		double hour1=24*fractional_part_of_numbers(day);


		//округлим в меньшую сторону
		int hourResult=(int)Math.floor(hour1);

		return hourResult;
	}



	//возвращает время сейчас (текущее настоящее время)
	public String getTime(){
		String str="";
		Calendar now = Calendar.getInstance();
		str=now.getTime().toString();
		return str;
	}



	//дробная часть числа
	public static double fractional_part_of_numbers(double mdouble){

		String str=mdouble+"";
		String str1=str.substring(str.indexOf('.'));
		double дробная_часть=Double.parseDouble(str1);

		return Math.abs(дробная_часть);


	}





	public int getMounth(int mounth){

		int mMounth=0;

		switch(mounth){
			case 1:
				mMounth=GregorianCalendar.JANUARY;
			    break;

			case 2:
				mMounth=GregorianCalendar.FEBRUARY;
			    break;

			case 3:
				mMounth=GregorianCalendar.MARCH;
				break;

			case 4:
				mMounth=GregorianCalendar.APRIL;
				break;

			case 5:
				mMounth=GregorianCalendar.MAY;
				break;

			case 6:
				mMounth=GregorianCalendar.JUNE;
				break;

			case 7:
				mMounth=GregorianCalendar.JULY;
				break;

			case 8:
				mMounth=GregorianCalendar.AUGUST;
				break;

			case 9:
				mMounth=GregorianCalendar.SEPTEMBER;
				break;

			case 10:
				mMounth=GregorianCalendar.OCTOBER;
				break;

			case 11:
				mMounth=GregorianCalendar.NOVEMBER;
				break;

			case 12:
				mMounth=GregorianCalendar.DECEMBER;
				break;

		}

		return mMounth;
	}




	//Возвращает общее количество элементов в наборе данных, удерживаемом адаптером.
    @Override
    public int getItemCount() {
        return layoutAdapterPage.length;
    }


	public class ViewHolder extends RecyclerView.ViewHolder {

		//первая стр
		RelativeLayout relativeLayout;
		Chronometer chronometer;
		EditText editTextYear;
		EditText editTextMonth;
		EditText editTextDay;
		TextView textView;
		TextView textView2;
		TextView textView3;
		Button button;
		//первая стр

		//вторая стр
		TextView textViewPage_1;
		//третья стр
		TextView textViewPage_2;
		//четвертая стр
		//TextView textViewPage_3_1;
		TextView textViewPage_3;
		//TextView textViewPage_3_3;


		//пятая стр (это разметки для второй последовательности)
		TextView textViewPage_4;
		//шестая стр (это разметки для второй последовательности)
		TextView textViewPage_5;
		//седьмая стр (это разметки для второй последовательности)
		TextView textViewPage_6;
		//восьмая стр (это разметки для второй последовательности)
		TextView textViewPage_7;
		//девятая стр (это разметки для второй последовательности)
		TextView textViewPage_8;
		
		
		
		ViewHolder(View itemView)
		{
            super(itemView); 
			//первая стр
			relativeLayout=itemView. findViewById(R.id.page0RelativeLayout1);
			editTextYear=itemView. findViewById(R.id.editTextYear);
			editTextMonth=itemView. findViewById(R.id.editTextMonth);
			editTextDay=itemView. findViewById(R.id.editTextDay);
			textView=itemView. findViewById(R.id.mainTextView1);
			textView2=itemView. findViewById(R.id.mainTextView2);
			textView3=itemView. findViewById(R.id.mainTextView3);
			button=itemView. findViewById(R.id.mainButton1);
			chronometer=itemView. findViewById(R.id.chronometer);
			//первая стр

			//вторая стр
			textViewPage_1=itemView. findViewById(R.id.textViewPage1);
			//третья стр
			textViewPage_2=itemView. findViewById(R.id.textViewPage2);
			//четвертая стр
			textViewPage_3=itemView. findViewById(R.id.textViewPage3_2);
			

			//пятая стр
			textViewPage_4=itemView. findViewById(R.id.textViewPage4);
			//шестая стр
			textViewPage_5=itemView. findViewById(R.id.textViewPage5);
			//седьмая стр
			textViewPage_6=itemView. findViewById(R.id.textViewPage6);
			//восьмая стр
			textViewPage_7=itemView. findViewById(R.id.textViewPage7);
			//девятая стр
			textViewPage_8=itemView. findViewById(R.id.textViewPage8);
			
			
		}

	}


}

