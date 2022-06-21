package com.ellah.befit.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.ellah.befit.R;
import com.ellah.befit.model.ExerciseDbResponse;
import com.ellah.befit.ui.WorkoutsActivity;


import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExerciseListAdapter extends RecyclerView.Adapter<ExerciseListAdapter.ExerciseViewHolder> {
    private List<ExerciseDbResponse> mExercises;
    private Context mContext;

    public ExerciseListAdapter(Context context, List<ExerciseDbResponse> exercises) {
        mContext = context;
        mExercises = exercises;
    }

    @NonNull
    @Override
    public ExerciseListAdapter.ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_list_item, parent, false);
        ExerciseViewHolder viewHolder = new ExerciseViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseListAdapter.ExerciseViewHolder holder, int position) {
        holder.bind(mExercises.get(position));
//
    }

    @Override
    public int getItemCount() {
        return mExercises.size();
    }

    public class ExerciseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.exerciseImageView)
        ImageView mExerciseImageView;
        @BindView(R.id.exerciseNameTextView)
        TextView mExerciseNameTextView;
        @BindView(R.id.equipmentTextView)
        TextView mEquipmentTextView;
        @BindView(R.id.bodyPart)
        TextView mBodyPart;
        @BindView(R.id.target) TextView mTarget;

        private Context mContext;

        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();

            itemView.setOnClickListener(this);
        }
//
        public void bind(ExerciseDbResponse exercise) {
            mExerciseNameTextView.setText(exercise.getName());
            mEquipmentTextView.setText(exercise.getEquipment());
            mBodyPart.setText(exercise.getBodyPart());
            mTarget.setText(exercise.getTarget());
            String gif= exercise.getGifUrl();
            Glide.with(mContext)
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
    }

        @Override
        public void onClick(View v) {
            int itemPosition = getLayoutPosition();
            Intent intent = new Intent(mContext, WorkoutsActivity.class);
            intent.putExtra("position", itemPosition);
            intent.putExtra("exercises", Parcels.wrap(mExercises));
            mContext.startActivity(intent);

        }
    }
}
