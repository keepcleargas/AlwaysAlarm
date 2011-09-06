package org.worldsproject.alarmclock;

import java.util.Calendar;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

public class Alarm
{
	private int hour;
	private int minute;
	
	private int distance;
	
	private boolean monday;
	private boolean tuesday;
	private boolean wednesday;
	private boolean thursday;
	private boolean friday;
	private boolean saturday;
	private boolean sunday;
	
	public Alarm(int hour, int minute, int distance, boolean monday,
			boolean tuesday, boolean wednesday, boolean thursday,
			boolean friday, boolean saturday, boolean sunday)
	{
		super();
		this.hour = hour;
		this.minute = minute;
		this.distance = distance;
		this.monday = monday;
		this.tuesday = tuesday;
		this.wednesday = wednesday;
		this.thursday = thursday;
		this.friday = friday;
		this.saturday = saturday;
		this.sunday = sunday;
	}
	
	/**
	 * 
	 * @return The time the alarm is to go off in the format HH:MM
	 */
	public String timeString()
	{
		if(minute < 10)
			return "" +hour + ":" + "0" + minute;
		else
			return "" + hour + ":" + minute;
	}
	
	public View generateView(Activity a)
	{
		View alarm = View.inflate(a, R.layout.alarm, null);
		
		TextView tv = (TextView)alarm.findViewById(R.id.textView1);
		tv.setText(timeString());
		
		if(monday)
			((TextView)alarm.findViewById(R.id.view_monday)).setTextColor(Color.MAGENTA);
		if(tuesday)
			((TextView)alarm.findViewById(R.id.view_tuesday)).setTextColor(Color.MAGENTA);
		if(wednesday)
			((TextView)alarm.findViewById(R.id.view_wednesday)).setTextColor(Color.MAGENTA);
		if(thursday)
			((TextView)alarm.findViewById(R.id.view_thursday)).setTextColor(Color.MAGENTA);
		if(friday)
			((TextView)alarm.findViewById(R.id.view_friday)).setTextColor(Color.MAGENTA);
		if(saturday)
			((TextView)alarm.findViewById(R.id.view_saturday)).setTextColor(Color.MAGENTA);
		if(sunday)
			((TextView)alarm.findViewById(R.id.view_sunday)).setTextColor(Color.MAGENTA);
		
		return alarm;
	}
	
	/**
	 * 
	 * @return the date of the next Alarm, or null if no more.
	 */
	public Calendar nextAlarmEvent()
	{
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		
		int day = cal.get(Calendar.DAY_OF_WEEK);
		int curHour = cal.get(Calendar.HOUR_OF_DAY);
		int curMin = cal.get(Calendar.MINUTE);
		
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		
		//how many days we have to go forward.
		int daysUntil = -1;
		
		//first check if there even is a repeat
		if(!monday && !tuesday && !wednesday && !thursday && !friday && !saturday && !sunday)
		{
			if(curHour < hour || (curHour == hour && curMin < minute))
			{
				return cal;
			}
			
			return null;
		}
		
		switch(day)
		{
			case Calendar.MONDAY:
				if(tuesday)
					daysUntil = 1;
				else if(wednesday)
					daysUntil = 2;
				else if(thursday)
					daysUntil = 3;
				else if(friday)
					daysUntil = 4;
				else if(saturday)
					daysUntil = 5;
				else if(sunday)
					daysUntil = 6;
				else if(monday)
					if(curHour >= hour && curMin > minute)
						daysUntil = 7;
					else
						daysUntil = 0;
			case Calendar.TUESDAY:
				if(wednesday)
					daysUntil = 1;
				else if(thursday)
					daysUntil = 2;
				else if(friday)
					daysUntil = 3;
				else if(saturday)
					daysUntil = 4;
				else if(sunday)
					daysUntil = 5;
				else if(monday)
					daysUntil = 6;
				else if(tuesday)
					if(curHour >= hour && curMin > minute)
						daysUntil = 7;
					else
						daysUntil = 0;
			case Calendar.WEDNESDAY:
				if(thursday)
					daysUntil = 1;
				else if(friday)
					daysUntil = 2;
				else if(saturday)
					daysUntil = 3;
				else if(sunday)
					daysUntil = 4;
				else if(monday)
					daysUntil = 5;
				else if(tuesday)
					daysUntil = 6;
				else if(wednesday)
					if(curHour >= hour && curMin > minute)
						daysUntil = 7;
					else
						daysUntil = 0;
			case Calendar.THURSDAY:
				if(friday)
					daysUntil = 1;
				else if(saturday)
					daysUntil = 2;
				else if(sunday)
					daysUntil = 3;
				else if(monday)
					daysUntil = 4;
				else if(tuesday)
					daysUntil = 5;
				else if(wednesday)
					daysUntil = 6;
				else if(thursday)
					if(curHour >= hour && curMin > minute)
						daysUntil = 7;
					else
						daysUntil = 0;
			case Calendar.FRIDAY:
				if(saturday)
					daysUntil = 1;
				else if(sunday)
					daysUntil = 2;
				else if(monday)
					daysUntil = 3;
				else if(tuesday)
					daysUntil = 4;
				else if(wednesday)
					daysUntil = 5;
				else if(thursday)
					daysUntil = 6;
				else if(friday)
					if(curHour >= hour && curMin > minute)
						daysUntil = 7;
					else
						daysUntil = 0;
			case Calendar.SATURDAY:
				if(sunday)
					daysUntil = 1;
				else if(monday)
					daysUntil = 2;
				else if(tuesday)
					daysUntil = 3;
				else if(wednesday)
					daysUntil = 4;
				else if(thursday)
					daysUntil = 5;
				else if(friday)
					daysUntil = 6;
				else if(saturday)
					if(curHour >= hour && curMin > minute)
						daysUntil = 7;
					else
						daysUntil = 0;
			case Calendar.SUNDAY:
				if(monday)
					daysUntil = 1;
				else if(tuesday)
					daysUntil = 2;
				else if(wednesday)
					daysUntil = 3;
				else if(thursday)
					daysUntil = 4;
				else if(friday)
					daysUntil = 5;
				else if(saturday)
					daysUntil = 6;
				else if(sunday)
					if(curHour >= hour && curMin > minute)
						daysUntil = 7;
					else
						daysUntil = 0;
		}
		
		cal.add(Calendar.DAY_OF_MONTH, daysUntil);
		cal.set(Calendar.SECOND, 0);
		return cal;
	}
	
	public int getDistance()
	{
		return distance;
	}
}
