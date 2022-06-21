package com.ellah.befit.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.ellah.befit.R;
import com.ellah.befit.model.ExerciseDbResponse;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;


public class WorkoutFragment extends Fragment {
    @BindView(R.id.exerciseImageView)
    ImageView mExerciseImageView;
    @BindView(R.id.exerciseNameTextView)
    TextView mExerciseNameTextView;
    @BindView(R.id.descriptionTextView) TextView mDescriptionTextView;

    private ExerciseDbResponse mExerciseDbResponse;

    public WorkoutFragment() {
        // Required empty public constructor
    }

    public static WorkoutFragment newInstance(ExerciseDbResponse exercise) {
        WorkoutFragment fragment = new WorkoutFragment();
        Bundle args = new Bundle();
        args.putParcelable("exercise", Parcels.wrap(exercise));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        mExerciseDbResponse = Parcels.unwrap(getArguments().getParcelable("exercise"));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_workout, container, false);
        ButterKnife.bind(this, view);
        String gif= mExerciseDbResponse.getGifUrl();
        Glide.with(getContext())
                .load(gif)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                }).into(mExerciseImageView);

        mExerciseNameTextView.setText(mExerciseDbResponse.getName());
        mDescriptionTextView.setText("This exercise targets " +mExerciseDbResponse.getTarget()+ "and for it to be effective, you should use a " +mExerciseDbResponse.getEquipment()+ ".");

        return view;
        }
}