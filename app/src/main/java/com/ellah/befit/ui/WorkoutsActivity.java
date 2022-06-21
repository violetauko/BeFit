package com.ellah.befit.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ellah.befit.R;
import com.ellah.befit.adapters.PagerAdapter;
import com.ellah.befit.model.ExerciseDbResponse;

import org.parceler.Parcels;

import java.util.List;
import java.util.Timer;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WorkoutsActivity extends AppCompatActivity {
    Button startButton;
    private CountDownTimer mTimer;
    TextView mTextview;
    private boolean mTimeRunning;
    private long mTimeleftmills;


    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    private PagerAdapter adapterViewPager;
    List<ExerciseDbResponse> mExercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workouts);
        ButterKnife.bind(this);

        mExercises = Parcels.unwrap(getIntent().getParcelableExtra("exercises"));
        int startingPosition = getIntent().getIntExtra("position", 0);

        adapterViewPager = new PagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, mExercises);
        mViewPager.setAdapter(adapterViewPager);
        mViewPager.setCurrentItem(startingPosition);

        startButton = findViewById(R.id.startButton);
        mTextview = findViewById(R.id.time);



        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimeRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });
    }

    private void pauseTimer() {
        mTimer.cancel();
        mTimeRunning = false;
        startButton.setText("Start");
    }

    private void startTimer() {
        final CharSequence text = mTextview.getText();
        String num1 = text.toString();
        String num2 = num1.substring(0,2);
        String num3 = num1.substring(3,5);

        final int number = Integer.valueOf(num2) * 60 + Integer.valueOf(num3);
        mTimeleftmills = number * 1000;

        mTimer = new CountDownTimer(mTimeleftmills, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeleftmills = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimeRunning = false;
                mTextview.setText("Done!");
            }
        }.start();
        mTimeRunning = true;
        startButton.setText("Pause");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void updateCountDownText() {
        int minutes = (int) (mTimeleftmills / 1000) / 60;
        int seconds = (int) (mTimeleftmills / 1000) % 60;

        String timeLeftText="";
        if (minutes < 10) {
            timeLeftText ="0";
            timeLeftText = timeLeftText + minutes + ":";

            if(seconds < 10){
                timeLeftText += "0";
                timeLeftText += seconds;
                mTextview.setText(timeLeftText);
            }


        }
    }
}