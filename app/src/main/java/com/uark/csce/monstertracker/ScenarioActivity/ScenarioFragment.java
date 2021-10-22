package com.uark.csce.monstertracker.ScenarioActivity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uark.csce.monstertracker.R;

public class ScenarioFragment extends Fragment implements ScenarioContract.View {
    ScenarioContract.Presenter presenter;

    public ScenarioFragment() {
        // Required empty public constructor
    }

    public static ScenarioFragment newInstance() {
        ScenarioFragment fragment = new ScenarioFragment();
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
        return inflater.inflate(R.layout.fragment_scenario, container, false);
    }

    @Override
    public void setPresenter(ScenarioContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void notifyScenariosLoaded() {

    }
}