package com.example.hours;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Skills {

    public String skill;
    public long hours;
    public int mMinutes;

    public Skills(String skill, long hours, int mMinutes) {
        this.skill = skill;
        this.hours = hours;
        this.mMinutes = mMinutes;

    }

    public static void createNewSkill() {
        Skills skill = new Skills(null,0,0);
        skillList.add(skill);
    }

    public static void removeNewSkill() {
        int lastAddedSkill = skillList.size()-1;
        skillList.remove(lastAddedSkill);
    }



   static Skills guitar = new Skills("Guitar", 375, 37);
   static Skills drums = new Skills("Drums", 1057, 55);
   static Skills android = new Skills("Android", 78, 23);
   static Skills bass = new Skills("Bass", 305, 6);
   static Skills writing = new Skills("Writing", 12, 8);
   static Skills cooking = new Skills("Cooking", 76,34);

    static List<Skills> skillList = new ArrayList<Skills>() {{
        add(guitar);
        add(drums);
        add(android);
        add(bass);
        add(writing);
        add(cooking);
    }};


}
