package com.ellah.befit.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ellah.befit.model.ExerciseDbResponse;
import com.ellah.befit.ui.WorkoutFragment;

import java.util.List;

public class PagerAdapter extends FragmentPagerAdapter {
    private List<ExerciseDbResponse> mExercises;

    public PagerAdapter(@NonNull FragmentManager fm, int behavior, List<ExerciseDbResponse> exercises) {
        super(fm, behavior);
        mExercises = exercises;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return WorkoutFragment.newInstance(mExercises.get(position));
    }

    @Override
    public int getCount() {
        return mExercises.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return mExercises.get(position).getName();
    }
}
