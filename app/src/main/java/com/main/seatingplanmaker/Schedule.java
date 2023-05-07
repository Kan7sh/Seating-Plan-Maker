package com.main.seatingplanmaker;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;


public class Schedule extends AppCompatActivity implements AdapterSchedule.OnItemClickListener {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private int classes,rows,columns;
    private int[][][] schedule;
    private ArrayList subjects;
    FirebaseUser firebaseUser;
    ImageView pp;
    LoginPref session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new LoginPref(this);

        setContentView(R.layout.activity_schedule);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        recyclerView = findViewById(R.id.scheduleRV);
        layoutManager = new LinearLayoutManager(this);
        session = new LoginPref(this);
        recyclerView.setLayoutManager(layoutManager);
        classes = getIntent().getIntExtra("classes",0);
        rows = getIntent().getIntExtra("rows",0);
        columns = getIntent().getIntExtra("columns",0);
        subjects = new ArrayList();
        mAdapter = new AdapterSchedule(classes);
        recyclerView.setAdapter(mAdapter);
        String ppUrl= String.valueOf(firebaseUser.getPhotoUrl());
        pp = findViewById(R.id.profilePicture);
        pp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(Schedule.this);
                builder.setTitle("Logout")
                        .setMessage("Are you sure you want to Logout?")
                        .setPositiveButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                session.logoutUser();
                                startActivity(new Intent(Schedule.this,MainActivity.class));
                                finish();
                            }
                        }).show();
            }
        });
        Glide.with(this)
                .load(ppUrl)
                .transform(new CircleCrop())
                .into(pp);
        pp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(Schedule.this);
                builder.setTitle("Logout")
                        .setMessage("Are you sure you want to Logout?")
                        .setPositiveButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                session.logoutUser();
                                startActivity(new Intent(Schedule.this,MainActivity.class));
                                finish();
                            }
                        }).show();
            }
        });


        ((AdapterSchedule)mAdapter).setOnItemClickListener(this);
        schedule = (int[][][]) getIntent().getSerializableExtra("schedule");
        subjects = (ArrayList) getIntent().getSerializableExtra("subjects");
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, ClassSchedule.class);
        intent.putExtra("schedule",schedule);
        intent.putExtra("position",position);
        intent.putExtra("rows",rows);
        intent.putExtra("columns",columns);
        intent.putExtra("subjects",subjects);

        startActivity(intent);
    }
}