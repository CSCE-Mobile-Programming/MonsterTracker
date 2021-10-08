package com.uark.csce.monstertracker.MonsterDetailsActivity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uark.csce.monstertracker.R;

public class MonsterDetailsFragment extends Fragment implements MonsterDetailsContract.View {
    MonsterDetailsContract.Presenter presenter;

    public MonsterDetailsFragment() {
        // Required empty public constructor
    }

    public static MonsterDetailsFragment newInstance() {
        MonsterDetailsFragment fragment = new MonsterDetailsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_monster_details, container, false);
    }

    @Override
    public void setPresenter(MonsterDetailsContract.Presenter presenter) {
        this.presenter = presenter;
    }
}