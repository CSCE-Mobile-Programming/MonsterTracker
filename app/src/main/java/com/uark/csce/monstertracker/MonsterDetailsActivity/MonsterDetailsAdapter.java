package com.uark.csce.monstertracker.MonsterDetailsActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uark.csce.monstertracker.R;
import com.uark.csce.monstertracker.models.Monster;

import java.util.List;

public class MonsterDetailsAdapter extends RecyclerView.Adapter<MonsterDetailsAdapter.ViewHolder> {
    private MonsterDetailsContract.Presenter presenter;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvMonsterNumber;
        private final TextView tvMonsterHealth;

        private final Button btnHealthAdd;
        private final Button btnHealthSubtract;

        // Switches for attributes will go here...

        public ViewHolder(@NonNull View view) {
            super(view);

            tvMonsterNumber = (TextView)view.findViewById(R.id.tvMonsterNumber);
            tvMonsterHealth = (TextView)view.findViewById(R.id.tvMonsterHealth);
            btnHealthAdd = (Button)view.findViewById(R.id.btnHealthAdd);
            btnHealthSubtract = (Button)view.findViewById(R.id.btnHealthSubtract);
        }

        public TextView getTvMonsterNumber() {
            return tvMonsterNumber;
        }
        public TextView getTvMonsterHealth() {
            return tvMonsterHealth;
        }
        public Button getBtnHealthAdd() {
            return btnHealthAdd;
        }
        public Button getBtnHealthSubtract() {
            return btnHealthSubtract;
        }
    }

    public void setPresenter(MonsterDetailsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_monster_item, parent, false);
        return new ViewHolder(view);
    }

    @NonNull
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Monster monster = presenter.getMonster(position);

        holder.getTvMonsterHealth().setText(monster.getHealth());
        holder.getTvMonsterNumber().setText(position);
        holder.getBtnHealthAdd().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Add to health
            }
        });
        holder.getBtnHealthSubtract().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Subtract from health
            }
        });
    }

    @Override
    public int getItemCount() {
        return presenter.getMonsterCount();
    }
}
