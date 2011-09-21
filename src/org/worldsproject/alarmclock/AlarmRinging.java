package org.worldsproject.alarmclock;

import android.app.Activity;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;
import android.widget.Toast;

public class AlarmRinging extends Activity implements SensorEventListener
{
	private double steps;

	private MediaPlayer mMediaPlayer;
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;

	private TextView tv;

	public void onCreate(Bundle savedInstance)
	{
		super.onCreate(savedInstance);
		setContentView(R.layout.alarm_ringing);
		mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE); 
		mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

		//		Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
		//		if(alert == null)
		//		{
		//			alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
		//		}
		//		mMediaPlayer = new MediaPlayer();
		//		try
		//		{
		//			Log.v("TIM", "MediaPlayer is null: " + (mMediaPlayer == null));
		//			Log.v("TIM", "Alert is null: " + (alert == null));
		//			mMediaPlayer.setDataSource(this, alert);
		//			final AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
		//			if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) 
		//			{
		//				mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
		//				mMediaPlayer.setLooping(true);
		//				mMediaPlayer.prepare();
		//				mMediaPlayer.start();
		//			}
		//		}
		//		catch (IllegalArgumentException e)
		//		{
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}
		//		catch (SecurityException e)
		//		{
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}
		//		catch (IllegalStateException e)
		//		{
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}
		//		catch (IOException e)
		//		{
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}


		tv = (TextView)this.findViewById(R.id.textView3);
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
		steps = Integer.parseInt(pref.getString("steps", "20"));
		tv.setText("" + steps);
	}

	protected void onResume() 
	{
		super.onResume();
		mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
	}

	protected void onPause() 
	{
		super.onPause();
		mSensorManager.unregisterListener(this);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onSensorChanged(SensorEvent event)
	{
			float x = event.values[0];
			float y = event.values[1];
			float z = event.values[2];
			
			double xs = Math.abs(Math.sqrt(x*x));
			double ys = Math.abs(Math.sqrt(y*y));
			double zs = Math.abs(Math.sqrt(z*z));
			double speed = (Math.sqrt(x*x + y*y + z*z) - SensorManager.GRAVITY_EARTH);
			
			int rightDir = 0;
			if(xs > 0.5)
				rightDir++;
			if(ys > 0.5)
				rightDir++;
			if(zs > 0.5)
				rightDir++;
				
			if(Math.abs(speed) > .85 && rightDir > 1)
			{
				steps--;
				
				if(steps <= 0)
					this.finish();
				
				tv.setText("" + steps);
			}
	}

}
