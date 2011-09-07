package org.worldsproject.alarmclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver
{

	@Override
	public void onReceive(Context context, Intent arg1)
	{
		Toast.makeText(context, "Here!", Toast.LENGTH_SHORT).show();
		int dis = arg1.getIntExtra("steps", 0);
		Intent myIntent = new Intent(context, AlarmRinging.class);
		myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		myIntent.putExtra("steps", dis);
		context.startActivity(myIntent);
		Toast.makeText(context, "Alarm!", Toast.LENGTH_SHORT).show();
	}
}
