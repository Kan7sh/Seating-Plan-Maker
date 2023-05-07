package com.main.seatingplanmaker;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class ClassSchedule extends AppCompatActivity {
    int[][][] schedule;
    int position,columns,rows;
    private TableLayout tableLayout;
    private ArrayList subjects;
    FirebaseUser firebaseUser;
    ImageView pp;
    LoginPref session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new LoginPref(this);
        setContentView(R.layout.activity_class_schedule);
        subjects = new ArrayList();
        schedule = (int[][][]) getIntent().getSerializableExtra("schedule");
        position = getIntent().getIntExtra("position",0);
        rows = getIntent().getIntExtra("rows",0);
        columns = getIntent().getIntExtra("columns",0);
        subjects = (ArrayList)getIntent().getSerializableExtra("subjects");
        tableLayout = findViewById(R.id.tableL);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        String ppUrl= String.valueOf(firebaseUser.getPhotoUrl());
        pp = findViewById(R.id.profilePicture);
        pp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(ClassSchedule.this);
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
                                startActivity(new Intent(ClassSchedule.this,MainActivity.class));
                                finish();
                            }
                        }).show();
            }
        });

        Glide.with(this)
                .load(ppUrl)
                .transform(new CircleCrop())
                .into(pp);

        TableRow tableRow = new TableRow(this);
        TextView textView3 = new TextView(this);
        textView3.setBackgroundResource(R.drawable.cell_border);
        textView3.setPaddingRelative(20,35,20,35);
        textView3.setTextColor(Color.WHITE);
        textView3.setText("R/C");
        tableRow.addView(textView3);
        for(int i = 1;i<=columns;i++){
            TextView textView4 = new TextView(this);
            textView4.setBackgroundResource(R.drawable.cell_border);
            textView4.setPaddingRelative(20,35,20,35);
            textView4.setTextColor(Color.WHITE);
            textView4.setText(String.valueOf(i));
            tableRow.addView(textView4);
        }
        tableLayout.addView(tableRow);

        int rowNum=1;
        for (int i = 0; i < rows; i++) {
            TableRow tableRow2 = new TableRow(this);
            TextView textView = new TextView(this);
            textView.setText(String.valueOf(rowNum));
            rowNum = rowNum+1;
            textView.setBackgroundResource(R.drawable.cell_border);
            textView.setPaddingRelative(20,35,20,35);
            textView.setTextColor(Color.WHITE);
            tableRow2.addView(textView);
            for (int j = 0; j < columns; j++) {
                TextView textView2 = new TextView(this);
                if(schedule[position][j][i]==0){
                    textView2.setText("Null");
                    textView2.setBackgroundResource(R.drawable.cell_border);
                    textView2.setPaddingRelative(20,35,20,35);
                    textView2.setTextColor(Color.WHITE);
                    tableRow2.addView(textView2);
                }else{
                    textView2.setText(String.valueOf(subjects.get(schedule[position][j][i] - 1)));
                    textView2.setBackgroundResource(R.drawable.cell_border);
                    textView2.setTextColor(Color.WHITE);
                textView2.setPaddingRelative(20,35,20,35);
                tableRow2.addView(textView2);
            }}
            tableLayout.addView(tableRow2);
        }

    }
}