package com.example.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

import static com.example.recyclerview.R.drawable.border_green;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {
    private ArrayList<ExampleItem> mExampleList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onPlusClickBunked(int position);
        void onPlusClickAttended(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public TextView mPercentage;
        public TextView mTextView1;
        public TextView mMinAttendance;
        public TextView mBunked;
        public TextView mAttended;
        public ImageView mPlusImage;
        public ImageView mPlusImage2;


        public ExampleViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            mPercentage = itemView.findViewById(R.id.percent);
            mTextView1 = itemView.findViewById(R.id.textView);
            mMinAttendance = itemView.findViewById(R.id.minAttendance);
            mBunked = itemView.findViewById(R.id.bunked);
            mAttended = itemView.findViewById(R.id.attended);
            mPlusImage = itemView.findViewById(R.id.image_add);
            mPlusImage2 = itemView.findViewById(R.id.image_add2);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }

                    }
                }
            });

            mPlusImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onPlusClickBunked(position);
                        }
                    }
                }
            });

            mPlusImage2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onPlusClickAttended(position);
                        }
                    }
                }
            });

        }
    }

    public ExampleAdapter(ArrayList<ExampleItem> exampleList) {
        mExampleList = exampleList;
    }


    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item, parent, false );
        ExampleViewHolder evh = new ExampleViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        ExampleItem currentItem = mExampleList.get(position);
        holder.mPercentage.setText(currentItem.getmPercentage());
        holder.mTextView1.setText(currentItem.getmText1());
        holder.mMinAttendance.setText(currentItem.getmAttendance());
        holder.mBunked.setText(currentItem.getmBunked());
        holder.mAttended.setText(currentItem.getmAttended());
        holder.mPercentage.setBackgroundResource(border_green);

    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

}
