package com.uark.csce.monstertracker.ScenarioActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uark.csce.monstertracker.R;
import com.uark.csce.monstertracker.models.info.Scenario;

import java.util.ArrayList;
import java.util.List;


public class ScenarioAdapter extends RecyclerView.Adapter<ScenarioAdapter.ViewHolder>{

    private List<Scenario> localDataSet;
    private ScenarioContract.Presenter presenter;

    public static class ViewHolder extends RecyclerView.ViewHolder
    {

        private final TextView tvScenarioName;

        public ViewHolder(@NonNull View view) {
            super(view);

            tvScenarioName = (TextView) view.findViewById(R.id.tvSubjectName);

        }

        public TextView getTvScenarioName() {
            return tvScenarioName;
        }
    }

    public ScenarioAdapter()
    {
        localDataSet = new ArrayList<>();
    }

    public void setPresenter(ScenarioContract.Presenter presenter)
    {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_scenario_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Scenario scenario = localDataSet.get(position);

        holder.getTvScenarioName().setText(scenario.getName());
        holder.itemView.setTag(scenario.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String scenario = (String)view.getTag();
                presenter.scenarioClicked(scenario);
            }
        });

    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    public void setLocalDataSet(List<Scenario> localDataSet)
    {
        this.localDataSet = localDataSet;
    }

}
