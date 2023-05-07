package com.main.seatingplanmaker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

public class AdapterSchedule extends RecyclerView.Adapter<AdapterSchedule.ViewHolder> {
    int numClasses;
    public AdapterSchedule(int numClasses){
        this.numClasses = numClasses;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_classes,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.textView.setText("Class "+(position+1));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return numClasses;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
            TextView textView;
            MaterialCardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.classNumberTV);
            cardView = itemView.findViewById(R.id.classesCard);

        }
    }
}
