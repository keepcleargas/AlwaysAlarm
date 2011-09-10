package org.worldsproject.alarmclock;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TimePicker;

public class AddAlarmActivity extends Activity
{	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_alarm);
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        
        String which = pref.getString("alarm_units", "false");
        TimePicker time = (TimePicker)findViewById(R.id.timePicker1);
        time.setIs24HourView((which.equalsIgnoreCase("false") ? false : true));
    }
    
    public void alarmAdd(View v)
    {
    	CheckBox monday = (CheckBox)findViewById(R.id.checkBox1);
    	CheckBox tuesday = (CheckBox)findViewById(R.id.checkBox2);
    	CheckBox wednesday = (CheckBox)findViewById(R.id.checkBox3);
    	CheckBox thursday = (CheckBox)findViewById(R.id.checkBox4);
    	CheckBox friday = (CheckBox)findViewById(R.id.checkBox5);
    	CheckBox saturday = (CheckBox)findViewById(R.id.checkBox6);
    	CheckBox sunday = (CheckBox)findViewById(R.id.checkBox7);
        
        TimePicker time = (TimePicker)findViewById(R.id.timePicker1);
        
        EditText steps = (EditText)findViewById(R.id.editText1);
    	
    	Intent myIntent = new Intent(AddAlarmActivity.this, AlwaysAlarmActivity.class);
    	myIntent.putExtra("mode", "new_alarm");
    	
    	myIntent.putExtra("monday", monday.isChecked());
    	myIntent.putExtra("tuesday", tuesday.isChecked());
    	myIntent.putExtra("wednesday", wednesday.isChecked());
    	myIntent.putExtra("thursday", thursday.isChecked());
    	myIntent.putExtra("friday", friday.isChecked());
    	myIntent.putExtra("saturday", saturday.isChecked());
    	myIntent.putExtra("sunday", sunday.isChecked());
    	
    	myIntent.putExtra("hour", time.getCurrentHour());
    	myIntent.putExtra("minute", time.getCurrentMinute());
    	
    	try
    	{
    		myIntent.putExtra("steps", Integer.parseInt(steps.getText().toString()));
    	}
    	catch(NumberFormatException e)
    	{
    		myIntent.putExtra("steps", 1);
    	}
    	
    	AddAlarmActivity.this.startActivity(myIntent);
    }
    
    public void cancelAdd(View v)
    {
    	Intent myIntent = new Intent(AddAlarmActivity.this, AlwaysAlarmActivity.class);
    	AddAlarmActivity.this.startActivity(myIntent);
    }
}
