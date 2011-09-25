package org.worldsproject.alarmclock;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.preference.PreferenceManager;
import android.view.WindowManager;
import android.widget.TextView;

public class AlarmRinging extends Activity implements SensorEventListener
{
	private int steps;

	private MediaPlayer mMediaPlayer;
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	private WakeLock wl;

	private TextView tv;

	public void onCreate(Bundle savedInstance)
	{
		super.onCreate(savedInstance);
		
		setContentView(R.layout.alarm_ringing);
		mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE); 
		mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON|WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);

		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
		
		Uri alert = Uri.parse(pref.getString("alarm_tone", "DEFAULT_TONE"));
		
		if(alert == null)
		{
			alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
		}
		mMediaPlayer = new MediaPlayer();
		try
		{
			mMediaPlayer.setDataSource(this, alert);
			final AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
			if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) 
			{
				mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
				mMediaPlayer.setLooping(true);
				mMediaPlayer.prepare();
				mMediaPlayer.start();
			}
		}
		catch (IllegalArgumentException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (SecurityException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IllegalStateException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		tv = (TextView)this.findViewById(R.id.textView3);
		
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
	
	protected void onStop()
	{
		super.onStop();
		this.mMediaPlayer.stop();
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy){}
	
	protected void onSaveInstanceState(Bundle b)
	{
		super.onSaveInstanceState(b);
		b.putInt("steps", steps);
	}
	
	protected void onRestoreInstanceState(Bundle b)
	{
		super.onRestoreInstanceState(b);
		steps = b.getInt("steps");
		tv.setText("" + steps);
	}
	
	protected void onStart()
	{
		super.onStart();
		
		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK|PowerManager.ON_AFTER_RELEASE, "Alarm Ringing");
		wl.acquire();
	}

	@Override
	public void onSensorChanged(SensorEvent event)
	{
		float x = event.values[0];
		float y = event.values[1];
		float z = event.values[2];

		double speed = (Math.sqrt(x*x + y*y + z*z) - SensorManager.GRAVITY_EARTH);

		speed = Math.abs(speed);
		
		if(speed > .75 && speed < 2.1)
		{
			steps--;

			if(steps <= 0)
			{
				this.mMediaPlayer.stop();
				wl.release();
				this.finish();
			}

			tv.setText("" + steps);
		}
	}

}
