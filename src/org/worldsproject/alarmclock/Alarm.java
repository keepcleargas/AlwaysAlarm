package org.worldsproject.alarmclock;

import java.util.Calendar;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Alarm extends LinearLayout
{
	private int hour;
	private int minute;
	
	private int steps;
	
	private boolean monday;
	private boolean tuesday;
	private boolean wednesday;
	private boolean thursday;
	private boolean friday;
	private boolean saturday;
	private boolean sunday;
	
	private boolean twofour;
	
	
	public Alarm(Activity parent, int hour, int minute, int steps, boolean monday,
			boolean tuesday, boolean wednesday, boolean thursday,
			boolean friday, boolean saturday, boolean sunday, boolean twofour)
	{
		super(parent);
		
		this.hour = hour;
		this.minute = minute;
		this.steps = steps;
		this.monday = monday;
		this.tuesday = tuesday;
		this.wednesday = wednesday;
		this.thursday = thursday;
		this.friday = friday;
		this.saturday = saturday;
		this.sunday = sunday;
		this.twofour = twofour;
		
		View.inflate(parent, R.layout.alarm, this);
		
		
		TextView tv = (TextView)findViewById(R.id.textView1);
		tv.setText(timeString());
		
		if(monday)
			((TextView)findViewById(R.id.view_monday)).setTextColor(Color.MAGENTA);
		if(tuesday)
			((TextView)findViewById(R.id.view_tuesday)).setTextColor(Color.MAGENTA);
		if(wednesday)
			((TextView)findViewById(R.id.view_wednesday)).setTextColor(Color.MAGENTA);
		if(thursday)
			((TextView)findViewById(R.id.view_thursday)).setTextColor(Color.MAGENTA);
		if(friday)
			((TextView)findViewById(R.id.view_friday)).setTextColor(Color.MAGENTA);
		if(saturday)
			((TextView)findViewById(R.id.view_saturday)).setTextColor(Color.MAGENTA);
		if(sunday)
			((TextView)findViewById(R.id.view_sunday)).setTextColor(Color.MAGENTA);
		
		
	}
	
	/**
	 * 
	 * @return The time the alarm is to go off in the format HH:MM
	 */
	public String timeString()
	{
		String sHour = "" + hour;
		String amPM = "";
		
		if(!twofour)
		{
			if(hour > 12)
			{
				sHour = "" + (hour - 12);
				amPM = " PM";
			}
			else
				amPM = " AM";
		}
		
		if(minute < 10)
			return "" +sHour + ":" + "0" + minute + amPM;
		else
			return "" + sHour + ":" + minute + amPM;
	}
	
	/**
	 * 
	 * @return the date of the next Alarm, or null if no more.
	 */
	public Calendar nextAlarmEvent()
	{
		Calendar now = Calendar.getInstance();
		Calendar rv = Calendar.getInstance();
		rv.set(Calendar.HOUR_OF_DAY, hour);
		rv.set(Calendar.MINUTE, minute);
		rv.set(Calendar.SECOND, 0);
		
		//Determine if oneshot alarm.
		if(!monday && !tuesday && !wednesday && !thursday && !friday && !friday && !saturday && !sunday)
		{
			if(now.before(rv))
				return rv;
			else
				return null;
		}
		
		Calendar mon = getNext(Calendar.MONDAY, hour, minute);
		Calendar tue = getNext(Calendar.TUESDAY, hour, minute);
		Calendar wed = getNext(Calendar.WEDNESDAY, hour, minute);
		Calendar thu = getNext(Calendar.THURSDAY, hour, minute);
		Calendar fri = getNext(Calendar.FRIDAY, hour, minute);
		Calendar sat = getNext(Calendar.SATURDAY, hour, minute);
		Calendar sun = getNext(Calendar.SUNDAY, hour, minute);
		
		Calendar closest = Calendar.getInstance();
		closest.setTimeInMillis(System.currentTimeMillis());
		closest.add(Calendar.MONTH, 10);
		
		if(monday)
		{
			if(now.before(mon) && mon.before(closest))
			{
				closest = mon;
			}
		}
		
		if(tuesday)
		{
			if(now.before(tue) && tue.before(closest))
			{
				closest = tue;
			}
		}
		
		if(wednesday)
		{
			if(now.before(wed) && wed.before(closest))
			{
				closest = wed;
			}
		}
		
		if(thursday)
		{
			if(now.before(thu) && thu.before(closest))
			{
				closest = thu;
			}
		}
		
		if(friday)
		{
			if(now.before(fri) && fri.before(closest))
			{
				closest = fri;
			}
		}
		
		if(saturday)
		{
			if(now.before(sat) && sat.before(closest))
			{
				closest = sat;
			}
		}
		
		if(sunday)
		{
			if(now.before(sun) && sun.before(closest))
			{
				closest = sun;
			}
		}
		
		return closest;
	}
	
	private Calendar getNext(int day, int hour, int minute)
	{
		Calendar rv = Calendar.getInstance();
		rv.setTimeInMillis(System.currentTimeMillis());
		
		while(day != rv.get(Calendar.DAY_OF_WEEK))
		{
			rv.add(Calendar.DAY_OF_MONTH, 1);
		}
		
		rv.set(Calendar.HOUR_OF_DAY, hour);
		rv.set(Calendar.MINUTE, minute);
		rv.set(Calendar.SECOND, 0);
		return rv;
	}
	
	public int getSteps()
	{
		return steps;
	}
}
