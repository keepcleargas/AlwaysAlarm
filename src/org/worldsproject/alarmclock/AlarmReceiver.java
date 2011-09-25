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
