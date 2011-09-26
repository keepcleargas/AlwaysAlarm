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

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

public class AddAlarmActivity extends Activity implements OnMultiChoiceClickListener, OnClickListener
{	
	private boolean monday = false;
	private boolean tuesday = false;
	private boolean wednesday = false;
	private boolean thursday = false;
	private boolean friday = false;
	private boolean saturday = false;
	private boolean sunday = false;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_alarm);

        TimePicker time = (TimePicker)findViewById(R.id.timePicker1);
        time.setIs24HourView(DateFormat.is24HourFormat(this));
    }
    
    public void alarmAdd(View v)
    {
        TimePicker time = (TimePicker)findViewById(R.id.timePicker1);
    	
    	Intent myIntent = new Intent(AddAlarmActivity.this, AlwaysAlarmActivity.class);
    	myIntent.putExtra("mode", "new_alarm");
    	
    	myIntent.putExtra("monday", monday);
    	myIntent.putExtra("tuesday", tuesday);
    	myIntent.putExtra("wednesday", wednesday);
    	myIntent.putExtra("thursday", thursday);
    	myIntent.putExtra("friday", friday);
    	myIntent.putExtra("saturday", saturday);
    	myIntent.putExtra("sunday", sunday);
    	
    	myIntent.putExtra("hour", time.getCurrentHour());
    	myIntent.putExtra("minute", time.getCurrentMinute());
    	
    	AddAlarmActivity.this.startActivity(myIntent);
    	this.finish();
    }
    
    public void cancelAdd(View v)
    {
    	Intent myIntent = new Intent(AddAlarmActivity.this, AlwaysAlarmActivity.class);
    	AddAlarmActivity.this.startActivity(myIntent);
    }
    
    public void addDays(View v)
    {
    	String[] days = {this.getString(R.string.monday),
    	this.getString(R.string.tuesday),
    	this.getString(R.string.wednesday),
    	this.getString(R.string.thursday),
    	this.getString(R.string.friday),
    	this.getString(R.string.saturday),
    	this.getString(R.string.sunday)};
    	
    	AlertDialog.Builder adb = new AlertDialog.Builder(this);
    	adb.setTitle(R.string.repeat_days);
    	adb.setMultiChoiceItems(days, null, this);
    	adb.setNeutralButton(this.getString(R.string.close), this);
    	adb.show();
    	
    }

	@Override
	public void onClick(DialogInterface arg0, int w, boolean v)
	{
		if(w == 0)
		{
			monday = v;
		}
		else if(w == 1)
		{
			tuesday = v;
		}
		else if(w == 2)
		{
			wednesday = v;
		}
		else if(w == 3)
		{
			thursday = v;
		}
		else if(w == 4)
		{
			friday = v;
		}
		else if(w == 5)
		{
			saturday = v;
		}
		else
		{
			sunday = v;
		}
	}

	@Override
	public void onClick(DialogInterface arg0, int arg1)
	{
		((AlertDialog)arg0).dismiss();
		
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
}
