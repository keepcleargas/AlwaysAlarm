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
		Intent myIntent = new Intent(context, AlarmRinging.class);
		myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(myIntent);
	}
}
