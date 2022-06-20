package com.ellah.befit.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ellah.befit.R;
import com.ellah.befit.adapters.ExerciseListAdapter;
import com.ellah.befit.model.ExerciseDbResponse;
import com.ellah.befit.network.ExerciseDBApi;
import com.ellah.befit.network.ExerciseDbClient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExerciseActivity extends AppCompatActivity {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.exerciseName)
    TextView mExerciseName;

    private ExerciseListAdapter mAdapter;
    private List<ExerciseDbResponse> exercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        ButterKnife.bind(this);


        Intent intent = getIntent();
       // String name = intent.getStringExtra("name");

    ExerciseDBApi client = ExerciseDbClient.getClient();
    Call<List<ExerciseDbResponse>> call = client.getExercises();

    call.enqueue(new Callback<List<ExerciseDbResponse>>() {
        @Override
        public void onResponse(Call<List<ExerciseDbResponse>> call, Response<List<ExerciseDbResponse>> response) {

            if (response.isSuccessful()) {
               exercises = response.body();
                mAdapter = new ExerciseListAdapter(ExerciseActivity.this, exercises);
                mRecyclerView.setAdapter(mAdapter);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ExerciseActivity.this);
                mRecyclerView.setLayoutManager(layoutManager);
                mRecyclerView.setHasFixedSize(true);
            } else {
                Toast.makeText(ExerciseActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<List<ExerciseDbResponse>> call, Throwable t) {

        }


    });
    }
}