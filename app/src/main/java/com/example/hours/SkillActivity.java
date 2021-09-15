package com.example.hours;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SkillActivity extends AppCompatActivity {
    public static final String SKILL_POSITION = "com.example.hours.SKILL_POSITION";
    public static final int POSITION_NOT_SET = -1;
    public static final int LAUNCH_SECOND_ACTIVITY = 1;
    public static final String NEW_SKILL = "com.example.hours.NEW_SKILL";
    public static final long NO_AMOUNT = 0;
    private int mSkillPosition;
    private Skills mSkill;
    private EditText mSkillName;
    private EditText mHoursAmount;
    private String mTime;
    private boolean mIsNewSkill;
    private boolean mIsCancelling;
    private FloatingActionButton mFloatingActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill);
        readDisplayStateValues();



        mSkillName = (EditText) findViewById(R.id.skill_name);
        mHoursAmount = (EditText) findViewById(R.id.hours_amount);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);


        displaySkill(mSkillName, mHoursAmount);
    }
//options menu does not appear maybe has something to do with android manifest
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int iD = item.getItemId();
        if (iD == R.id.action_cancel) {
            mIsCancelling = true;
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mIsNewSkill) {
            if (mIsCancelling) {
                Skills.removeNewSkill();
            } else if (TextUtils.isEmpty(mSkillName.getText().toString()) ||  TextUtils.isEmpty(mHoursAmount.getText().toString())) {
                Skills.removeNewSkill();
            } else {
                mSkill.skill = mSkillName.getText().toString();
                mSkill.hours = Long.parseLong(mHoursAmount.getText().toString());
            }
        }
    }



    @Override
    protected void onRestart() {
         super.onRestart();
//        mHoursAmount.setText((int) mSkill.hours);
        displaySkill(mSkillName, mHoursAmount);
    }

    //    @Override
//    protected void onPause() {
//        super.onPause();
//          saveProgress();
//    }


    private void displaySkill(TextView skillName, TextView hoursAmount) {
        skillName.setText(mSkill.skill);
        mTime = String.format("%02d", mSkill.hours) + " Hours " + String.format("%02d", mSkill.mMinutes) + " Minutes";
        if (mSkill.hours != 0) {
            hoursAmount.setText(mTime);
        }

    }

    private void readDisplayStateValues() {

        Intent intent = getIntent();
        mSkillPosition =  intent.getIntExtra(SKILL_POSITION, POSITION_NOT_SET);
        mIsNewSkill = mSkillPosition == POSITION_NOT_SET;
        if(mIsNewSkill) {
            mSkillPosition = createNewSkill();
            mIsNewSkill = true;

        }

        mSkill = Skills.skillList.get(mSkillPosition);
        }


    public static int createNewSkill() {
        Skills.createNewSkill();
        return Skills.skillList.size() - 1;
    }


    public void craft(View view) {
        Intent intent = new Intent(this, AddHoursActivity.class);
        intent.putExtra(AddHoursActivity.SKILL_POSITION, mSkillPosition);
        startActivityForResult(intent, LAUNCH_SECOND_ACTIVITY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == LAUNCH_SECOND_ACTIVITY) {
            if(resultCode == RESULT_OK) {
                int extraHours = data.getIntExtra("hours result", 0);
                int extraMinutes = data.getIntExtra("minutes result", 0);
                String thoughts = data.getStringExtra("notes result");
                mSkill.hours += extraHours;
                mSkill.mMinutes += extraMinutes;
                mSkill = Skills.skillList.get(mSkillPosition);
                mIsNewSkill = false;
            }
        }

    }
}