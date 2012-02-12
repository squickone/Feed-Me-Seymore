package com.feedme.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.feedme.R;
import com.feedme.model.Baby;
import com.feedme.util.DateUtil;

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
    boolean paused = false;
    
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer);

        googleAnalyticsTracker.startNewSession(TRACKING_ID, this);
        googleAnalyticsTracker.trackPageView("/Timer");

        final Baby baby = (Baby) getIntent().getSerializableExtra("baby");

        final Bundle bundle = new Bundle();
        bundle.putSerializable("baby", baby);
        bundle.putInt("entrySide", getIntent().getExtras().getInt("entrySide"));

        styleActivity(baby.getSex());

        final Button startTimer = (Button) findViewById(R.id.startTimer);
        final Button stopTimer = (Button) findViewById(R.id.stopTimer);
        final Button pauseTimer = (Button) findViewById(R.id.pauseTimer);
        final Button resetTimer = (Button) findViewById(R.id.resetTimer);
        final Button restartTimer = (Button) findViewById(R.id.restartTimer);
        final Button addBreast = (Button) findViewById(R.id.addBreast);

        startTimer.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                startTimer.setVisibility(View.INVISIBLE);
                addBreast.setVisibility(View.INVISIBLE);
                stopTimer.setVisibility(View.VISIBLE);
                pauseTimer.setVisibility(View.VISIBLE);
                resetTimer.setVisibility(View.VISIBLE);
                startTimer();
            }
        });

        addBreast.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(v.getContext(), AddBreastFeedActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, 3);
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
                addBreast.setVisibility(View.VISIBLE);
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
        duration = calendar.getTimeInMillis() - 1000L;
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

            DateUtil dateUtil = new DateUtil();
            String strTimer = dateUtil.convertDateLongToTimeString(calendar.getTime().getTime());
            timerLabel.setText(strTimer);
            calendar.add(Calendar.SECOND, 1);
		}
	};
}