package com.uark.csce.monstertracker.MainActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.uark.csce.monstertracker.ConnectActivity.ConnectActivity;
import com.uark.csce.monstertracker.MonsterDetailsActivity.MonsterDetailsActivity;
import com.uark.csce.monstertracker.R;
import com.uark.csce.monstertracker.ScenarioActivity.ScenarioActivity;
import com.uark.csce.monstertracker.models.info.MonsterInfo;

import java.util.List;

public class MainFragment extends Fragment implements MainContract.View {
    MainContract.Presenter presenter;
    private RecyclerView rvMainList;
    private MainAdapter adapter;
    private ActivityResultLauncher<Intent> scenarioResultLauncher;

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

        Button buttonScenario = root.findViewById(R.id.buttonScenario);
        buttonScenario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.scenarioActivityButtonClicked();
            }
        });

        Button buttonConnect = root.findViewById(R.id.buttonConnect);
        buttonConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.connectActivityButtonClicked();
            }
        });

        scenarioResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    Intent data = result.getData();
                    String scenario = data.getStringExtra("scenario");

                    presenter.scenarioActivityResult(scenario);
                });
        adapter = new MainAdapter(getActivity().getAssets());
        rvMainList = root.findViewById(R.id.rvMainList);
        rvMainList.setAdapter(adapter);
        rvMainList.setLayoutManager(new LinearLayoutManager(getActivity()));

        return root;
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        this.presenter = presenter;
        adapter.setPresenter(presenter);
    }

    @Override
    public void startScenarioActivity() {
        Intent scenarioIntent = new Intent();
        scenarioIntent.setClass(getActivity(), ScenarioActivity.class);

        scenarioResultLauncher.launch(scenarioIntent);

    }

    @Override
    public void startConnectActivity() {
        Intent connectIntent = new Intent();
        connectIntent.setClass(getActivity(), ConnectActivity.class);

        startActivity(connectIntent);

    }

    @Override
    public void setupMonsterInfos(List<MonsterInfo> infos) {
        ((MainAdapter)rvMainList.getAdapter()).setLocalDataSet(infos);
        rvMainList.getAdapter().notifyDataSetChanged();
    }
}