package com.uark.csce.monstertracker.MainActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.uark.csce.monstertracker.MonsterDetailsActivity.MonsterDetailsActivity;
import com.uark.csce.monstertracker.R;
import com.uark.csce.monstertracker.ScenarioActivity.ScenarioActivity;

public class MainFragment extends Fragment implements MainContract.View {
    MainContract.Presenter presenter;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        Button button = root.findViewById(R.id.detailsActivityButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.detailsActivityButtonClicked();
            }
        });
        return root;
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void startDetailsActivity() {
        Intent detailsIntent = new Intent();
        detailsIntent.setClass(getActivity(), ScenarioActivity.class);
        startActivity(detailsIntent);
    }
}