package com.example.hours;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SkillActivity extends AppCompatActivity {
    public static final String SKILL_POSITION = "com.example.hours.SKILL_POSITION";
    public static final int POSITION_NOT_SET = -1;
    public static final int LAUNCH_SECOND_ACTIVITY = 1;
    public static final String NEW_SKILL = "com.example.hours.NEW_SKILL";
    public static final long NO_AMOUNT = 0;
    private int mSkillPosition;
    private Skills mSkill;
    private TextView mSkillName;
    private TextView mHoursAmount;
    private EditText mEditSkillName;
    private EditText mEditHoursAmount;
    private Button mCommit;
    private Button mForge;
    private String mTime;
    private boolean mIsNewSkill;
    private boolean mIsCancelling;
    private FloatingActionButton mFloatingActionButton;
    private Handler mHandler = new Handler();
    private MenuItem mItemCancel;
    private int mNosCalls = 0;
    private int mNosCallHours = 0;
    private Boolean mEditSkillIsEdited = false;
    private Boolean mEditHoursIsEdited = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill);
        Toolbar toolbar = findViewById(R.id.skillToolbar);
        setSupportActionBar(toolbar);
        readDisplayStateValues();



        mSkillName = (TextView) findViewById(R.id.skill_name);
        mHoursAmount = (TextView) findViewById(R.id.hours_amount);
        mEditSkillName = (EditText) findViewById(R.id.editSkillName);
        mEditHoursAmount = (EditText) findViewById(R.id.editHoursAmount);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        mCommit = (Button) findViewById(R.id.commitChangesButton);
        mForge = (Button) findViewById(R.id.createNewSkillButton);
        mEditSkillName.addTextChangedListener(skillTextWatcher);
        mEditHoursAmount.addTextChangedListener(hoursTextWatcher);

        displaySkill(mSkillName, mHoursAmount);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int ID = item.getItemId();
        if (ID == R.id.action_cancel) {
            mIsCancelling = true;
            finish();
        } else if (ID == R.id.action_edit) {
            mSkillName.setVisibility(View.INVISIBLE);
            mHoursAmount.setVisibility(View.INVISIBLE);
            mEditSkillName.setVisibility(View.VISIBLE);
            mEditHoursAmount.setVisibility(View.VISIBLE);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem itemEdit = menu.findItem(R.id.action_edit);
        MenuItem itemCancel = menu.findItem(R.id.action_cancel);
        itemCancel.setVisible(mEditSkillIsEdited||mEditHoursIsEdited);
        itemEdit.setVisible(mSkillName.getVisibility() == View.VISIBLE);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mIsNewSkill) {
            if (mIsCancelling) {
                Skills.removeNewSkill();
            } else if (TextUtils.isEmpty(mEditSkillName.getText().toString())) {
                Skills.removeNewSkill();
            } else if (!TextUtils.isEmpty(mEditSkillName.getText().toString()) &&
                    TextUtils.isEmpty(mEditHoursAmount.getText().toString())) {
                mSkill.skill = mEditSkillName.getText().toString();
                mSkill.hours = 0;
            } else {
                mSkill.skill = mSkillName.getText().toString();
                mSkill.hours = Long.parseLong(mHoursAmount.getText().toString());
            }
        } else if(mIsCancelling) {
            finish();
        } else {
            mSkill.skill = mSkillName.getText().toString();
            if(!TextUtils.isEmpty(mEditHoursAmount.getText().toString()) || !TextUtils.isEmpty(mHoursAmount.getText().toString())) {
                mSkill.hours = Long.valueOf(mEditHoursAmount.getText().toString());
            }
        }

    }




    @Override
    protected void onRestart() {
         super.onRestart();
        displaySkill(mSkillName, mHoursAmount);
    }


    TextWatcher skillTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mNosCalls++;
            if (mNosCalls > 1) {
                mEditSkillIsEdited= true;
                mHandler.postDelayed(mRunnable, 0);
            }
            invalidateOptionsMenu();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    TextWatcher hoursTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mNosCallHours++;
            if (mNosCallHours > 1) {
                mEditHoursIsEdited = true;
                mHandler.postDelayed(mRunnable, 1);
            }
            invalidateOptionsMenu();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if(mIsNewSkill) {
                if(mForge.getVisibility() == View.INVISIBLE) {
                    mForge.setVisibility(View.VISIBLE);
                }
            } else if(mEditSkillName.getVisibility() == View.VISIBLE &&
                        mCommit.getVisibility() == View.INVISIBLE) {
                    mCommit.setVisibility(View.VISIBLE);
            } else if(mEditSkillName.getVisibility() == View.INVISIBLE &&
                        mCommit.getVisibility() == View.VISIBLE) {
                    mCommit.setVisibility(View.INVISIBLE);
            }
                mHandler.postDelayed(this,0);
        }
    };

    private void displaySkill(TextView skillName, TextView hoursAmount) {
        if(!mIsNewSkill) {
            mSkillName.setText(mSkill.skill);
            mEditSkillName.setText(mSkill.skill);
            mTime = String.format("%02d", mSkill.hours) + " Hours " +
                    String.format("%02d", mSkill.mMinutes) + " Minutes";

            if (mSkill.hours != 0) {
                mHoursAmount.setText(mTime);
                mEditHoursAmount.setText(String.valueOf(mSkill.hours));
            } else {
                mHoursAmount.setText("press the hammer below and log some hours");
                mEditHoursAmount.setText("0");
            }

            mEditSkillName.setVisibility(View.INVISIBLE);
            mEditHoursAmount.setVisibility(View.INVISIBLE);
            mSkillName.setVisibility(View.VISIBLE);
            mHoursAmount.setVisibility(View.VISIBLE);
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
        if (!TextUtils.isEmpty(mEditSkillName.getText()) ||
                !TextUtils.isEmpty(mEditHoursAmount.getText()) ) {
            Intent intent = new Intent(this, AddHoursActivity.class);
            intent.putExtra(AddHoursActivity.SKILL_POSITION, mSkillPosition);
            startActivityForResult(intent, LAUNCH_SECOND_ACTIVITY);
        } else {
            String enterSkill = "What is the name of your Skill?";
            Toast toast = Toast.makeText(this,enterSkill, Toast.LENGTH_LONG);
            toast.show();

        }
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

    public void commitChanges(View view) {
        if(mEditSkillIsEdited) {
            String skillChanges = mEditSkillName.getText().toString();
            mSkillName.setText(skillChanges);
        }
        if (!TextUtils.isEmpty(mEditHoursAmount.getText())) {
            String hourChanges = mEditHoursAmount.getText().toString();
            mHoursAmount.setText(hourChanges);
        }
        mEditSkillName.setVisibility(View.INVISIBLE);
        mEditHoursAmount.setVisibility(View.INVISIBLE);
        mSkillName.setVisibility(View.VISIBLE);
        mHoursAmount.setVisibility(View.VISIBLE);
        mCommit.setVisibility(View.INVISIBLE);
    }

    public void forgeSkill(View view) {
        mSkill.skill = mEditSkillName.getText().toString();
        if (TextUtils.isEmpty(mEditHoursAmount.getText().toString())) {
            mSkill.hours = 0;
        } else {
            mSkill.hours = Long.parseLong(mEditHoursAmount.getText().toString());
        }
        mEditSkillName.setVisibility(View.INVISIBLE);
        mEditHoursAmount.setVisibility(View.INVISIBLE);
        mSkillName.setVisibility(View.VISIBLE);
        mHoursAmount.setVisibility(View.VISIBLE);
        mForge.setVisibility(View.INVISIBLE);
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
    }
}