package com.feedme.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.feedme.R;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * User: steve quick
 * Date: 1/24/12
 * Time: 6:52 PM
*/

public class TimerActivity extends BaseActivity
{
    private Timer timer;
    private long startTime;
    private long stopTime;
    private long duration;
    private Calendar calendar;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
    boolean paused = false;
    
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.timer);

        //get baby id
        Bundle b = getIntent().getExtras();
        final String babyGender = b.getString("babyGender");

        styleActivity(babyGender);

        final Button startTimer = (Button) findViewById(R.id.startTimer);
        final Button stopTimer = (Button) findViewById(R.id.stopTimer);
        final Button pauseTimer = (Button) findViewById(R.id.pauseTimer);
        final Button resetTimer = (Button) findViewById(R.id.resetTimer);
        final Button restartTimer = (Button) findViewById(R.id.restartTimer);

        startTimer.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                startTimer.setVisibility(View.INVISIBLE);
                stopTimer.setVisibility(View.VISIBLE);
                pauseTimer.setVisibility(View.VISIBLE);
                resetTimer.setVisibility(View.VISIBLE);
                startTimer();
            }
        });

        pauseTimer.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                restartTimer.setVisibility(View.VISIBLE);
                pauseTimer.setVisibility(View.INVISIBLE);
                stopTimer();
                paused = true;
            }
        });

        restartTimer.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                restartTimer.setVisibility(View.INVISIBLE);
                pauseTimer.setVisibility(View.VISIBLE);
                startTimer();
                paused = false;
            }
        });

        resetTimer.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                startTimer.setVisibility(View.VISIBLE);
                stopTimer.setVisibility(View.INVISIBLE);
                pauseTimer.setVisibility(View.INVISIBLE);
                resetTimer.setVisibility(View.INVISIBLE);
                restartTimer.setVisibility(View.INVISIBLE);
                paused = false;
                resetTimer();
            }
        });

        stopTimer.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                startTimer.setVisibility(View.VISIBLE);
                stopTimer.setVisibility(View.INVISIBLE);
                pauseTimer.setVisibility(View.INVISIBLE);
                resetTimer.setVisibility(View.INVISIBLE);
                stopTimer();

                Intent intent = new Intent(v.getContext(), AddBreastFeedActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("babyGender", babyGender);
                bundle.putLong("timerStart", startTime);
                bundle.putLong("timerStop", stopTime);
                bundle.putLong("duration", duration);
                intent.putExtras(bundle);
                startActivityForResult(intent, 3);
            }
        });

    }

    private void startTimer()
    {
        timer = new Timer();
        startTime = System.currentTimeMillis();
        if (paused == false)
        {
            calendar = new GregorianCalendar(0,0,0,0,0,0);
        }

        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                TimerMethod();
            }
        }, 0, 1000);
    }

    private void stopTimer()
    {
        timer.cancel();
        duration = calendar.getTimeInMillis();
        stopTime = System.currentTimeMillis();
    }

    private void resetTimer()
    {
        timer.cancel();
        duration = 0L;
        stopTime = 0L;
        TextView timerLabel = (TextView) findViewById(R.id.timerLabel);
        timerLabel.setText("00:00:00");
    }

	private void TimerMethod()
	{
		this.runOnUiThread(Timer_Tick);
	}

	private Runnable Timer_Tick = new Runnable()
    {
		public void run()
        {
            TextView timerLabel = (TextView) findViewById(R.id.timerLabel);

            String strTimer = simpleDateFormat.format(calendar.getTime());
            timerLabel.setText(strTimer);
            calendar.add(Calendar.SECOND, 1);
		}
	};
}