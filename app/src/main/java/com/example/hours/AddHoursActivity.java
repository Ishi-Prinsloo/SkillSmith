package com.example.hours;

import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class AddHoursActivity extends AppCompatActivity {

    public static final String SKILL_POSITION = "com.example.hours.SKILL_POSITION_1";
    private TextView mTimer;
    private ImageButton mTimeButton;
    private Button mStopTime;
    private EditText mNotes;
    public long mMilliSecondTime, mTimeBuff, mStartTime, mUpdateTime = 0L;
    private Handler mHandler;
    int mSeconds, mMinutes, mHours;
    private int mSkillPosition;
    private Skills mSkill;
    private TextView skillName;
    private TextView hoursAmount;
    private TextView mMinutesAmount;
    Button commit;
    Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hours);

        mTimer = (TextView) findViewById(R.id.timer);
        mTimeButton = (ImageButton) findViewById(R.id.time_button);
        mStopTime = (Button) findViewById(R.id.stop_time_button);
        mNotes = (EditText) findViewById(R.id.notes);
        mHandler = new Handler();
        commit = (Button) findViewById(R.id.commit_button);
        cancel = (Button) findViewById(R.id.cancel_button);

        readDisplayStateValues();

        skillName = (TextView) findViewById(R.id.craft_skill_name);

        displaySkill();

    }

    private void displaySkill() {
        skillName.setText(mSkill.skill);
    }

    private void readDisplayStateValues() {
        Intent intent = getIntent();
        mSkillPosition = intent.getIntExtra(SKILL_POSITION, SkillActivity.POSITION_NOT_SET);
        mSkill = Skills.skillList.get(mSkillPosition);
    }

    public void time(View view) {
        if (mNotes.getVisibility() == View.VISIBLE) {
            mNotes.setVisibility(View.INVISIBLE);
            commit.setVisibility(View.INVISIBLE);
            cancel.setVisibility(View.INVISIBLE);
        }
        mTimer.setVisibility(View.VISIBLE);
        mStartTime = SystemClock.uptimeMillis();
        mTimeButton.setVisibility(View.INVISIBLE);
        mStopTime.setVisibility(View.VISIBLE);
        mHandler.postDelayed(mRunnable, 0);
    }


    public void stopTime(View view) {
        if (mNotes.getVisibility() == View.INVISIBLE) {
            mNotes.setVisibility(View.VISIBLE);
            commit.setVisibility(View.VISIBLE);
            cancel.setVisibility(View.VISIBLE);
        }
        mTimeBuff += mMilliSecondTime;
        mHandler.removeCallbacks(mRunnable);
        mStopTime.setVisibility(View.INVISIBLE);
        mTimeButton.setVisibility(View.VISIBLE);
        mNotes.setVisibility(View.VISIBLE);
    }


    public Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            mMilliSecondTime = SystemClock.uptimeMillis() - mStartTime;
            mUpdateTime = mTimeBuff + mMilliSecondTime;
            mSeconds = (int) (mUpdateTime / 1000);
            mMinutes = mSeconds / 60;
            mSeconds = mSeconds % 60;
            mHours = mMinutes / 60;
            mMinutes = mMinutes % 60;
            mTimer.setText("" + mHours + ":" + String.format("%02d", mMinutes) + ":" + String.format("%02d", mSeconds));

            mHandler.postDelayed(this, 0);
        }
    };


    public void commitHours(View view) {
        Intent returnIntent = new Intent(AddHoursActivity.this, SkillActivity.class); //Hours still doesn't register in SkillActivity...Debug values in SkillActivity
        returnIntent.putExtra("hours result", mHours);
        returnIntent.putExtra("minutes result", mMinutes);
        String thoughts = mNotes.getText().toString();
        returnIntent.putExtra("notes result", thoughts);
        setResult(SkillActivity.RESULT_OK, returnIntent);
        finish();
    }

    public void cancelSession(View view) {
        Intent returnIntent = new Intent(AddHoursActivity.this, SkillActivity.class);
        setResult(SkillActivity.RESULT_CANCELED, returnIntent);
        finish();
    }
}