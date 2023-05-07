package com.main.seatingplanmaker;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.dialog.MaterialDialogs;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home extends AppCompatActivity {
    FirebaseUser firebaseUser;
    Button nextBtn;
    EditText numClasses,numSubject,numRows,numColumn;
    TextView classError,columnError,rowError,subjectError;
    ImageView pp;
    LoginPref session;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        String ppUrl= String.valueOf(firebaseUser.getPhotoUrl());
        classError = findViewById(R.id.classError);
        columnError = findViewById(R.id.columnError);
        rowError = findViewById(R.id.rowError);
        subjectError = findViewById(R.id.subjectError);
        nextBtn = findViewById(R.id.homeNextBtn);
        numClasses = findViewById(R.id.numClassesEd);
        numColumn = findViewById(R.id.numColumnEd);
        numRows = findViewById(R.id.numRowEd);
        numSubject = findViewById(R.id.numSubjectEd);
        pp = findViewById(R.id.profilePicture);
        Glide.with(this)
                .load(ppUrl)
                .transform(new CircleCrop())
                .into(pp);


        pp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(Home.this);
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
                                startActivity(new Intent(Home.this,MainActivity.class));
                                finish();
                            }
                        }).show();
            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToNextActivity();
            }
        });
    }

    private void goToNextActivity() {
        int flag = 0;
        String classes = numClasses.getText().toString();
        String columns = numColumn.getText().toString();
        String rows = numRows.getText().toString();
        String subjects = numSubject.getText().toString();
        if(classes.isEmpty()){
            classError.setText("Please fill the Value");
            flag = 1;
        }
        if(columns.isEmpty()){
            columnError.setText("Please fill the Value");
            flag = 1;
        }
        if(rows.isEmpty()){
            rowError.setText("Please fill the Value");
            flag = 1;
        }
        if(subjects.isEmpty()){
            subjectError.setText("Please fill the Value");
            flag = 1;
        }
        if(flag==0){
            Intent intent = new Intent(this, NumberOfClasses.class);
            intent.putExtra("classes",Integer.parseInt(classes));
            intent.putExtra("rows",Integer.parseInt(rows));
            intent.putExtra("columns",Integer.parseInt(columns));
            intent.putExtra("subjects",Integer.parseInt(subjects));
            startActivity(intent);
        }

    }
}