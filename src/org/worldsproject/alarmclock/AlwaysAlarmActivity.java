/*
 * Copyright Tim Butram 2011
 * 
 * This file is part of AlwaysAlarm.
 *
 *  AlwaysAlarm is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.

 *  AlwaysAlarm is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.

 *  You should have received a copy of the GNU General Public License
 *  along with AlwaysAlarm.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.worldsproject.alarmclock;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

public class AlwaysAlarmActivity extends Activity
{
	public static ArrayList<Alarm> alarms = new ArrayList<Alarm>();

	public static LinearLayout root;
	
	private final long MSINWEEK = 604800000;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Intent intent = getIntent();
		String mode = intent.getStringExtra("mode");
		
		root = (LinearLayout)findViewById(R.id.alarms);
		if(root != null)
			root.removeAllViews();
		
		for(Alarm v:alarms)
		{
			if(v.nextAlarmEvent() == null)
				continue;
			
			try
			{
				root.addView(v);
			}
			catch(Exception e)
			{
				((LinearLayout)v.getParent()).removeView(v);
				root.addView(v);
			}
		}

		if(mode != null)
		{
			if(mode.equals("new_alarm"))
			{
				Alarm alarm = new Alarm(this,
						intent.getIntExtra("hour", 0),
						intent.getIntExtra("minute", 0),
						intent.getBooleanExtra("monday", false),
						intent.getBooleanExtra("tuesday", false),
						intent.getBooleanExtra("wednesday", false),
						intent.getBooleanExtra("thursday", false),
						intent.getBooleanExtra("friday", false),
						intent.getBooleanExtra("saturday", false),
						intent.getBooleanExtra("sunday", false), 
						(DateFormat.is24HourFormat(this)));

				if(createAlarm(alarm))
				{
					root.addView(alarm);
					alarms.add(alarm);
				}
			}
		}
	}

	/*
	 * @return true is a new Alarm view should be added
	 */
	private boolean createAlarm(Alarm alarm)
	{
		ArrayList<Calendar> cal = alarm.nextAlarmEvent();

		if(cal == null)
		{
			Toast.makeText(getBaseContext(), "Alarm exists in past.\n  No alarm added", Toast.LENGTH_LONG).show();
			return false;
		}
		else
		{
			Calendar now = Calendar.getInstance();
			long n = now.getTimeInMillis();
			long l = cal.get(0).getTimeInMillis();
			
			long diff = l - n;
			
			long diffSeconds = (diff / 1000)%60;
			long diffMinutes = (diff / (60 * 1000))%60;
			long diffHours = (diff / (60 * 60 * 1000))%24;
			long diffDays = diff / (24 * 60 * 60 * 1000);
			
			StringBuffer buf = new StringBuffer("Next Alarm in\n");
			if(diffDays > 0)
				buf.append(diffDays + " days\n");
			if(diffHours > 0)
				buf.append(diffHours + " hours\n");
			if(diffMinutes > 0)
				buf.append(diffMinutes + " minutes\n");
			buf.append(diffSeconds + " seconds.");
			
			Toast.makeText(getBaseContext(), buf.toString(), Toast.LENGTH_LONG).show();
		}

		Intent alarmIntent = new Intent(AlwaysAlarmActivity.this, AlarmReceiver.class);
		
		PendingIntent sender = PendingIntent.getBroadcast(this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

		alarm.setIntent(sender);
		
		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
		
		for(Calendar c:cal)
			am.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), MSINWEEK, sender);
		
		((ToggleButton)alarm.findViewById(R.id.toggleButton1)).setChecked(true);
		return true;
	}

	public void removeAlarm(View v)
	{
		Alarm temp = (Alarm)v.getParent().getParent().getParent();//TODO This feels dirty...
		root.removeView(temp);
		alarms.remove(temp);
		((AlarmManager)getSystemService(ALARM_SERVICE)).cancel(temp.getAlarmIntent());
	}

	public void toggled(View v)
	{
		Alarm alarm = (Alarm)v.getParent().getParent().getParent();
		ToggleButton tb = (ToggleButton)v;

		if(tb.isChecked())
		{
			createAlarm(alarm);
		}
		else
		{
			((AlarmManager)getSystemService(ALARM_SERVICE)).cancel(alarm.getAlarmIntent());
		}
	}

	public void addAlarm(View v)
	{
		Intent myIntent = new Intent(AlwaysAlarmActivity.this, AddAlarmActivity.class);
		AlwaysAlarmActivity.this.startActivity(myIntent);
	}

	public void showPreferences(View v)
	{
		Intent myIntent = new Intent(AlwaysAlarmActivity.this, AlarmPreferences.class);
		AlwaysAlarmActivity.this.startActivity(myIntent);
	}
}