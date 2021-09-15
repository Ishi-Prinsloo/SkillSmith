package com.example.hours;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>  {

    private final Context context;
    private final LayoutInflater layoutInflater;
    private final List<Skills> skills;

    RecyclerViewAdapter(Context context, List<Skills> skills) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.skills = skills;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = layoutInflater.inflate(R.layout.card_skill, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Skills skill = skills.get(i);
        viewHolder.skillTitle.setText(skill.skill);
        String timeRounded = String.valueOf(skill.hours + skill.mMinutes / 30);
        viewHolder.skillHours.setText(timeRounded);
        viewHolder.currentPosition = viewHolder.getAdapterPosition();
    }



    @Override
    public int getItemCount() {

        return skills.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView skillTitle;
        public final TextView skillHours;
        public int currentPosition;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            skillTitle = (TextView) itemView.findViewById(R.id.title_skill);
            skillHours = (TextView) itemView.findViewById(R.id.skill_hours);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, SkillActivity.class);
                    intent.putExtra(SkillActivity.SKILL_POSITION, currentPosition);
                    context.startActivity(intent);
                }
            });
        }
    }
}
