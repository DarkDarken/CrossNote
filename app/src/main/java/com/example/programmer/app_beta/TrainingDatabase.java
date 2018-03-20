package com.example.programmer.app_beta;


import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TrainingDatabase {

    private static List<Training> trainings = new ArrayList<Training>();

    public static List<Training> getTrainings() {
        return trainings;
    }

    public static void addTraining(Training training) {
        Collections.reverse(trainings);
        trainings.add(training);
        Collections.reverse(trainings);
    }

    public static void delateItem(int position){
        trainings.remove(position);
    }

}
