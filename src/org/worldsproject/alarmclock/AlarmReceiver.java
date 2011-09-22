package org.worldsproject.alarmclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver
{

	@Override
	public void onReceive(Context context, Intent arg1)
	{
		Intent myIntent = new Intent(context, AlarmRinging.class);
		myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(myIntent);
	}
}
