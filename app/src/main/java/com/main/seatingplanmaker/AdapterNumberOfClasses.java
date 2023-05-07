package com.main.seatingplanmaker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class AdapterNumberOfClasses extends RecyclerView.Adapter<AdapterNumberOfClasses.ViewHolder>{

    int numClasses;

    public AdapterNumberOfClasses(int numClasses){
        this.numClasses = numClasses;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.number_of_classes_edit_text,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String Title  = "Number of Students for Subjects "+(position+1);
        holder.textView.setText(Title);

    }

    @Override
    public int getItemCount() {
        return numClasses;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        EditText editText1,editText2;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.classesTV);
            editText1 = itemView.findViewById(R.id.classesED);
            editText2 = itemView.findViewById(R.id.nameED);
        }
    }
}
