package com.example.racinggame.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.racinggame.Adapters.ScoreAdapter;
import com.example.racinggame.Interfaces.CallBack_SendClick;
import com.example.racinggame.Interfaces.OnItemClickListener;
import com.example.racinggame.Logic.GameManager;
import com.example.racinggame.Models.ScoreList;
import com.example.racinggame.R;
import com.example.racinggame.Utilities.MySP;
import com.google.gson.Gson;

public class ListFragment extends Fragment implements OnItemClickListener {
    public ListFragment() {
        // Required empty public constructor
    }

    private CallBack_SendClick callBack_SendClick;
    private RecyclerView main_LST_scores;
    private TextView list_score;
    ScoreList scoreListFromJson;

    public void setCallBack(CallBack_SendClick callBack_SendClick) {
        this.callBack_SendClick = callBack_SendClick;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        findViews(view);
        initData();
        initView();

        return view;
    }

    private void initView() {
        ScoreAdapter scoreAdapter = new ScoreAdapter(getContext(), scoreListFromJson.getScores(), this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        main_LST_scores.setLayoutManager(linearLayoutManager);
        main_LST_scores.setAdapter(scoreAdapter);
    }

    private void initData() {
        String fromSP =  MySP.getInstance().getString(GameManager.SCORE_KEY,"");
        if(fromSP.isEmpty() == true)
            scoreListFromJson = new ScoreList();
        else
            scoreListFromJson = new Gson().fromJson(fromSP, ScoreList.class);
        Log.d("From JSON", scoreListFromJson.toString());
    }


    private void findViews(View view) {
        main_LST_scores = view.findViewById(R.id.main_LST_scores);
        list_score = view.findViewById(R.id.score_LBL);
    }

    @Override
    public void itemClicked(int position) {
        if(callBack_SendClick != null){
            callBack_SendClick.scoreChosen(scoreListFromJson.getScores().get(position));
        }
    }
}