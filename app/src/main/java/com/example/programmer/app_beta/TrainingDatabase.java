package com.example.programmer.app_beta;


import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

public class TrainingDatabase {

    private static List<Training> trainings = new ArrayList<Training>();

    public static List<Training> getTrainings() {
        return trainings;
    }

    public static void addExpense(Training training) {
        trainings.add(training);
    }

    public static void delateItem(int position){
        trainings.remove(position);
    }

}
