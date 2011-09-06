package org.worldsproject.alarmclock;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class AlarmPreferences extends PreferenceActivity
{
	public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.layout.preferences);
    }  
}
