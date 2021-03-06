package com.uark.csce.monstertracker.MainActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.uark.csce.monstertracker.ConnectActivity.ConnectActivity;
import com.uark.csce.monstertracker.MonsterDetailsActivity.MonsterDetailsActivity;
import com.uark.csce.monstertracker.R;
import com.uark.csce.monstertracker.ScenarioActivity.ScenarioActivity;
import com.uark.csce.monstertracker.models.info.MonsterInfo;

import java.util.List;

public class MainFragment extends Fragment implements MainContract.View, MonsterPickerDialog.MonsterPickerListener {
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

        FloatingActionButton buttonAddMonster = root.findViewById(R.id.buttonAddMonster);
        buttonAddMonster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.addMonsterButtonClicked();
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

        // Attach the swipe callback to the RV
        ItemTouchHelper helper = new ItemTouchHelper(swipeCallback);
        helper.attachToRecyclerView(rvMainList);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        this.presenter = presenter;
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
    public void onMonsterPicked(String monsterName) {
        presenter.monsterPickerReturned(monsterName);
    }

    @Override
    public void showMonsterPicker() {
        List<MonsterInfo> monsters = presenter.getAllMonsterInfos();

        MonsterPickerDialog dialog = new MonsterPickerDialog(monsters, this);
        dialog.show(getParentFragmentManager(), "MonsterPickerDialog");
    }

    @Override
    public void monsterInfoChanged(List<MainAdapterModel> models) {
        ((MainAdapter)rvMainList.getAdapter()).setLocalDataSet(models);
        rvMainList.getAdapter().notifyDataSetChanged();
    }

    private ItemTouchHelper.SimpleCallback swipeCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            MainAdapter.ViewHolder vh = (MainAdapter.ViewHolder)viewHolder;
            String monsterInfoName = vh.getTvMonsterName().getText().toString();
            presenter.removeMonster(monsterInfoName);
        }
    };
}