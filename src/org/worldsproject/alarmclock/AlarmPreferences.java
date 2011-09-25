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
