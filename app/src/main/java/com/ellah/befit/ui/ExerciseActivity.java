package com.ellah.befit.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ellah.befit.R;
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
    @BindView(R.id.listView)
    ListView mListView;
    @BindView(R.id.exerciseName)
    TextView mExerciseName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        ButterKnife.bind(this);


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String exercise = ((TextView) view).getText().toString();
                Toast.makeText(ExerciseActivity.this, exercise, Toast.LENGTH_LONG).show();
            }
        });

        Intent intent = getIntent();
       // String name = intent.getStringExtra("name");

    ExerciseDBApi client = ExerciseDbClient.getClient();
    Call<List<ExerciseDbResponse>> call = client.getExercises();

    call.enqueue(new Callback<List<ExerciseDbResponse>>() {
        @Override
        public void onResponse(Call<List<ExerciseDbResponse>> call, Response<List<ExerciseDbResponse>> response) {

            if (response.isSuccessful()) {
                List<ExerciseDbResponse> exercises = response.body();
                String[] exercise = new String[exercises.size()];

                for (int i = 0; i < exercise.length; i++) {
                    exercise[i] = exercises.get(i).getName();
                }
                ArrayAdapter adapter = new ArrayAdapter<>(ExerciseActivity.this, android.R.layout.simple_list_item_1, exercise);
                mListView.setAdapter(adapter);
            }
        }

        @Override
        public void onFailure(Call<List<ExerciseDbResponse>> call, Throwable t) {

        }


    });
    }
}