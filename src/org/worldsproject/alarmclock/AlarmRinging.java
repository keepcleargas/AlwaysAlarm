package org.worldsproject.alarmclock;

import android.app.Activity;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;
import android.widget.Toast;

public class AlarmRinging extends Activity implements SensorEventListener
{
	private float   mLimit = 10;
	private float   mLastValues[] = new float[3*2];
	private float   mScale[] = new float[2];
	private float   mYOffset;

	private float   mLastDirections[] = new float[3*2];
	private float   mLastExtremes[][] = { new float[3*2], new float[3*2] };
	private float   mLastDiff[] = new float[3*2];
	private int     mLastMatch = -1;

	private double steps;

	private MediaPlayer mMediaPlayer;

	public void onCreate(Bundle savedInstance)
	{
		super.onCreate(savedInstance);
		setContentView(R.layout.alarm_ringing);

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
		
		TextView tv = (TextView)this.findViewById(R.id.textView3);
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
		steps = pref.getInt("steps", 20);
		tv.setText("" + steps);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onSensorChanged(SensorEvent event)
	{
		Sensor sensor = event.sensor; 
		synchronized (this) 
		{
			if (sensor.getType() == Sensor.TYPE_ORIENTATION) 
			{

			}
			else
			{
				int j = (sensor.getType() == Sensor.TYPE_ACCELEROMETER) ? 1 : 0;
				if (j == 1) 
				{
					float vSum = 0;
					for (int i=0 ; i<3 ; i++) 
					{
						final float v = mYOffset + event.values[i] * mScale[j];
						vSum += v;
					}

					int k = 0;
					float v = vSum / 3;

					float direction = (v > mLastValues[k] ? 1 : (v < mLastValues[k] ? -1 : 0));

					if (direction == - mLastDirections[k]) 
					{
						// Direction changed
						int extType = (direction > 0 ? 0 : 1); // minumum or maximum?
						mLastExtremes[extType][k] = mLastValues[k];
						float diff = Math.abs(mLastExtremes[extType][k] - mLastExtremes[1 - extType][k]);

						if (diff > mLimit) 
						{
							boolean isAlmostAsLargeAsPrevious = diff > (mLastDiff[k]*2/3);
							boolean isPreviousLargeEnough = mLastDiff[k] > (diff/3);
							boolean isNotContra = (mLastMatch != 1 - extType);

							if (isAlmostAsLargeAsPrevious && isPreviousLargeEnough && isNotContra) 
							{
								steps -= 0.5;

								if(steps <= 0)
								{
									//Stop alarm.
									Toast.makeText(getBaseContext(), "Step Taken", Toast.LENGTH_SHORT).show();
								}
								mLastMatch = extType;
							}
							else 
							{
								mLastMatch = -1;
							}
						}
						mLastDiff[k] = diff;
					}
					mLastDirections[k] = direction;
					mLastValues[k] = v;
				}
			}
		}
	}

}
