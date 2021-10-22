package com.uark.csce.monstertracker.ScenarioActivity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uark.csce.monstertracker.R;
import com.uark.csce.monstertracker.models.info.Scenario;

import java.util.List;

public class ScenarioFragment extends Fragment implements ScenarioContract.View {
    ScenarioContract.Presenter presenter;
    private RecyclerView rvScenarioList;

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
        View root =  inflater.inflate(R.layout.fragment_scenario, container, false);

        rvScenarioList = root.findViewById(R.id.rvScenarioList);
        rvScenarioList.setAdapter(new ScenarioAdapter());
        rvScenarioList.setLayoutManager(new LinearLayoutManager(getActivity()));

        return root;
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void setPresenter(ScenarioContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void notifyScenariosLoaded() {
        List<Scenario> scenarios = presenter.getScenarios();
        ((ScenarioAdapter)rvScenarioList.getAdapter()).setLocalDataSet(scenarios);
        rvScenarioList.getAdapter().notifyDataSetChanged();
    }
}