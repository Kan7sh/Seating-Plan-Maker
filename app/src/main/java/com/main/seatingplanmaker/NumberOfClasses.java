package com.main.seatingplanmaker;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class NumberOfClasses extends AppCompatActivity {
    private int classes,columns,rows,subjects,totalseats;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList editTextValues,subjectID,editTextNames;
    FirebaseUser firebaseUser;
    Button createSchBtn;
    TextView numSubError;
    ImageView pp;
    LoginPref session;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new LoginPref(this);
        setContentView(R.layout.activity_number_of_classes);
        recyclerView = findViewById(R.id.classesRV);
        layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        classes = getIntent().getIntExtra("classes",0);
        columns = getIntent().getIntExtra("columns",0);
        rows = getIntent().getIntExtra("rows",0);
        subjects = getIntent().getIntExtra("subjects",0);
        mAdapter = new AdapterNumberOfClasses(subjects);
        recyclerView.setAdapter(mAdapter);
        totalseats = columns*rows*classes;
        createSchBtn = findViewById(R.id.createSchBtn);
        numSubError = findViewById(R.id.numSubError);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        String ppUrl= String.valueOf(firebaseUser.getPhotoUrl());
        pp = findViewById(R.id.profilePicture);
        pp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(NumberOfClasses.this);
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
                                startActivity(new Intent(NumberOfClasses.this,MainActivity.class));
                                finish();
                            }
                        }).show();
            }
        });

        Glide.with(this)
                .load(ppUrl)
                .transform(new CircleCrop())
                .into(pp);

        createSchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkFields();
            }
        });
    }

    private void checkFields() {
        int flag = 0,sum=0,id=1;

        editTextValues = new ArrayList();
        subjectID = new ArrayList();
        editTextNames = new ArrayList();
        for(int i = 0;i<mAdapter.getItemCount();i++){
            RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForPosition(i);
            if(viewHolder instanceof AdapterNumberOfClasses.ViewHolder){
                EditText editText1 = ((AdapterNumberOfClasses.ViewHolder) viewHolder).editText1;
                EditText editText2 = ((AdapterNumberOfClasses.ViewHolder) viewHolder).editText2;

                String editTextValue = editText1.getText().toString();
                String editTextName = editText2.getText().toString();
                if(editTextValue.equals("")||editTextName.equals("")){
                    flag=1;
                }else{
                    editTextValues.add(editTextValue);
                    editTextNames.add(editTextName);
                    sum = sum+Integer.parseInt(editTextValue);
            }}
            String subjectIDValue = String.valueOf(id);
            if(subjectIDValue.matches("\\d+")) {
                subjectID.add(subjectIDValue);

            }

        id=id+1;

        }
        if(flag==1){
            numSubError.setText("Please fill all the fields");
        }else if((columns*rows*classes)<sum){
            numSubError.setText("Total number of students are more than total number of seats");
        }else{
            createScheldule();
        }


    }

    private void createScheldule() {
        int[][][] seats = new int[classes][columns][rows];
        for(int i =0;i<classes;i++){
            for(int j=0;j<columns;j++){
                for(int k=0;k<rows;k++){
                    seats[i][j][k]=0;
                }
            }
        }
        int subpointer=0,flag2=0;
        for(int i =0;i<classes;i++){
            for(int j=0;j<columns;j++){
                for(int k=0;k<rows;k++){
                    if(editTextValues.isEmpty()==false&&Integer.parseInt((String) editTextValues.get(subpointer))!=0){
                        seats[i][j][k]=Integer.parseInt((String) subjectID.get(subpointer));
                        editTextValues.set(subpointer,String.valueOf(Integer.parseInt((String)editTextValues.get(subpointer))-1));
                    }else{
                        subjectID.remove(subpointer);
                        editTextValues.remove(subpointer);
                        subjects = subjects-1;
                        if(subpointer==subjectID.size()){
                            subpointer=0;
                        }
                        if(subjects!=0){

                            seats[i][j][k]=Integer.parseInt((String) subjectID.get(subpointer));
                        editTextValues.set(subpointer,String.valueOf(Integer.parseInt((String)editTextValues.get(subpointer))-1));
                            if(Integer.parseInt((String) editTextValues.get(subpointer))==-1){

                                editTextValues.set(subpointer,String.valueOf(0));

                            }
                            if(Integer.parseInt((String) editTextValues.get(subpointer))==0){
                                subjects = subjects-1;
                                subjectID.remove(subpointer);
                                editTextValues.remove(subpointer);

                            }
                    }
                    }
                        if(editTextValues.isEmpty()){

                            flag2=1;
                            break;
                        }
                }if(flag2==0) {
                    if(subjects!=0){
                    subpointer = (subpointer + 1) % subjects;
                }}else{
                    break;
                }
            }
            if(flag2==1){

                break;

            }

        }

        Intent intent = new Intent(this, Schedule.class);
        intent.putExtra("schedule",seats);
        intent.putExtra("classes",classes);
        intent.putExtra("rows",rows);
        intent.putExtra("columns",columns);
        intent.putExtra("subjects",editTextNames);
        startActivity(intent);
    }
}