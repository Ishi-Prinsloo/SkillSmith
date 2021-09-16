package com.example.hours;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String SKILL_POSITION = "com.example.hours.SKILL_POSITION";
    private RecyclerViewAdapter mRecyclerViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              startActivity(new Intent(MainActivity.this, SkillActivity.class));
            }
        });

        initialiseDisplayContent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mRecyclerViewAdapter.notifyDataSetChanged();

    }

    private void initialiseDisplayContent() {
        final RecyclerView recyclerSkills = (RecyclerView) findViewById(R.id.skills_recycler);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerSkills.setLayoutManager(linearLayoutManager);

        List<Skills> skills = Skills.skillList;
        mRecyclerViewAdapter = new RecyclerViewAdapter(this, skills);
        recyclerSkills.setAdapter(mRecyclerViewAdapter);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//
//        if (id == R.id.action_cancel) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }



}