package com.example.hours;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.widget.EditText;

import static com.example.hours.SkillActivity.NEW_SKILL;

public class FirstFragment extends Fragment {


    private EditText mNewSkillName;
    private EditText mNewHoursAmount;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        mNewSkillName = (EditText) getView().findViewById(R.id.nameNewSkill);
        mNewHoursAmount = (EditText) getView().findViewById(R.id.hoursAlreadyInvested);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);



    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.add_new_skill).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), SkillActivity.class);
                String newSkill = mNewSkillName.getText().toString();
                String newHours = mNewHoursAmount.getText().toString();
                int newHoursToInt = Integer.valueOf(newHours);
                intent.putExtra(NEW_SKILL, newSkill);
                intent.putExtra(NEW_SKILL, newHoursToInt);
                startActivity(intent);

            }
        });
    }
}