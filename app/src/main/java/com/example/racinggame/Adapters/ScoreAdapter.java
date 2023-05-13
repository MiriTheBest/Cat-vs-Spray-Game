package com.example.racinggame.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.racinggame.Interfaces.OnItemClickListener;
import com.example.racinggame.Models.Score;
import com.example.racinggame.R;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder> {

    private Context context;
    private ArrayList<Score> scores;

    private OnItemClickListener listener;

    public ScoreAdapter(Context context, ArrayList<Score> scores, OnItemClickListener listener) {
        this.context = context;
        this.scores = scores;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("passed VT:", "" + viewType);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.score_item, parent, false);
        ScoreViewHolder scoreViewHolder = new ScoreViewHolder(view);
        return scoreViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreViewHolder holder, int position) {
        Score score = getItem(position);
        holder.score_LBL.setText("Score: "+ score.getScore());
    }

    @Override
    public int getItemCount() {
        return this.scores == null ? 0 : scores.size();
    }

    private Score getItem(int position) {
        return this.scores.get(position);
    }

    public class ScoreViewHolder extends RecyclerView.ViewHolder {
        private MaterialTextView score_LBL;

        public ScoreViewHolder(@NonNull View itemView) {
            super(itemView);
            score_LBL = itemView.findViewById(R.id.score_LBL);

            itemView.setOnClickListener(v -> {
                if(listener != null)
                    listener.itemClicked(getAdapterPosition());
            });
        }
    }
}