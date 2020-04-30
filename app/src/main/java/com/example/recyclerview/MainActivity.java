package com.example.recyclerview;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {
    private ArrayList<ExampleItem> mExampleList;
    private RecyclerView mRecyclerView;
    private ExampleAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Button buttonInsert;
    private EditText editTextMinAttendance;
    private EditText editTextSubject;
    private TextView mNoBunked;
    private TextView mPercentage;
    private int no_of_bunked;
    private int no_of_attended;
    private float mPercentageFloat;
    private int mTotal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        createExampleList();
        buildExampleList();
        setButton();

        editTextMinAttendance = findViewById(R.id.edittext_minAttendance);
        editTextSubject = findViewById(R.id.edittext_subject);
        mPercentage = findViewById(R.id.percent);



        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

    }

    ExampleItem deletedMovie = null;


    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            final int position = viewHolder.getAdapterPosition();


            switch(direction) {
                default:
                    deletedMovie = mExampleList.get(position);
                    removeItem(position);
                    Snackbar.make(mRecyclerView, "Deleted!", Snackbar.LENGTH_LONG)
                            .setAction("Undo", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mExampleList.add(position, deletedMovie);
                                    mAdapter.notifyItemInserted(position);
                                    saveData();
                                }
                            }).show();
                    break;
            }

        }
    };

    public void saveData() {

        SharedPreferences sharedPreferences = getSharedPreferences("Shared Preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mExampleList);
        editor.putString("task List", json);
        editor.apply();
    }

    public void loadData() {

        SharedPreferences sharedPreferences = getSharedPreferences("Shared Preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task List", null);
        Type type = new TypeToken<ArrayList<ExampleItem>>() {}.getType();
        mExampleList = gson.fromJson(json, type);

        if (mExampleList == null) {
            mExampleList = new ArrayList<>();
        }
    }

    public void plusBunked(int position) {
        no_of_bunked = no_of_bunked + 1;
        String no_of_bunked_string = String.valueOf(no_of_bunked);
        mExampleList.get(position).setmBunked(no_of_bunked_string);
        no_of_bunked = Integer.valueOf(no_of_bunked_string);
        mAdapter.notifyItemChanged(position);
        saveData();

    }

    public void plusAttended(int position) {
        no_of_attended = no_of_attended + 1;
        String no_of_attended_string = String.valueOf(no_of_attended);
        mExampleList.get(position).setmAttended(no_of_attended_string);
        no_of_attended = Integer.valueOf(no_of_attended_string);
        mAdapter.notifyItemChanged(position);
        saveData();

    }

    public void countPercentage(int position) {
        mTotal = no_of_attended + no_of_bunked;
        if(mTotal == 0) {
            mPercentageFloat = 0f;
        }else {
            mPercentageFloat = (no_of_attended * 100) / mTotal;
        }
        String mPercentage_string = String.valueOf(mPercentageFloat);
        mExampleList.get(position).setmPercentage(mPercentage_string);
        mPercentageFloat = Float.valueOf(mPercentage_string);
        mAdapter.notifyItemChanged(position);
        saveData();
    }

    public void insertItem(int position, int minimum, String subject) {
        no_of_bunked = 0;
        no_of_attended = 0;
        mPercentageFloat = 0;
        mExampleList.add(position,new ExampleItem(""+mPercentageFloat, subject, "Minimum Attendance: "+minimum+"%", ""+no_of_bunked, ""+no_of_attended));
        mAdapter.notifyItemInserted(position);
        saveData();

    }
    public void removeItem(int position) {
        mExampleList.remove(position);
        mAdapter.notifyItemRemoved(position);
        saveData();

    }

    public void createExampleList() {
        loadData();
    }

    public void buildExampleList() {

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ExampleAdapter(mExampleList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new ExampleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //changeItem(position,"Clicked");

            }
            @Override
            public void onPlusClickBunked(int position) {
                plusBunked(position);
                countPercentage(position);
            }

            @Override
            public void onPlusClickAttended(int position) {
                plusAttended(position);
                countPercentage(position);

            }
        });

    }

    public void setButton() {

        buttonInsert = findViewById(R.id.button_insert);

        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String MinAtt = editTextMinAttendance.getText().toString();
                String Subject = editTextSubject.getText().toString();
                if (MinAtt.matches("") || Subject.matches("")) {
                    Toast.makeText(getApplicationContext(), "Insert Minimum Attendance And Subject",Toast.LENGTH_SHORT).show();
                }
                else {
                    int minimumAttendance = Integer.parseInt(editTextMinAttendance.getText().toString());
                    int position = mExampleList.size();
                    insertItem(position, minimumAttendance, Subject);
                }

            }
        });
    }
}
