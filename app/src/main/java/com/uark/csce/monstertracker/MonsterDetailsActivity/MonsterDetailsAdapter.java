package com.uark.csce.monstertracker.MonsterDetailsActivity;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.uark.csce.monstertracker.R;
import com.uark.csce.monstertracker.models.Monster;
import com.uark.csce.monstertracker.models.MonsterType;

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
        if (monster != null) {
            // Set the view to visible, since it might have previously been set invisible
            holder.itemView.setVisibility(View.VISIBLE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            holder.getTvMonsterHealth().setText(Integer.toString(monster.getHealth()));
            holder.getTvMonsterNumber().setText(Integer.toString(position + 1));

            if (monster.getType() == MonsterType.Elite) {
                holder.getTvMonsterNumber().setTextColor(ContextCompat.getColor(holder.getTvMonsterNumber().getContext(), R.color.elite_gold));
                holder.getTvMonsterHealth().setTextColor(ContextCompat.getColor(holder.getTvMonsterHealth().getContext(), R.color.elite_gold));
            }
            else {
                holder.getTvMonsterNumber().setTextColor(ContextCompat.getColor(holder.getTvMonsterNumber().getContext(), R.color.black));
                holder.getTvMonsterHealth().setTextColor(ContextCompat.getColor(holder.getTvMonsterHealth().getContext(), R.color.black));
            }

            holder.getBtnHealthAdd().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.addHealth(holder.getAdapterPosition());
                    notifyDataSetChanged();
                }
            });
            holder.getBtnHealthSubtract().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.subtractHealth(holder.getAdapterPosition());
                    notifyDataSetChanged();
                }
            });
        }
        else {
            // Set the view to invisible. We don't want table cells for monster data that's not initted
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
        }
    }

    @Override
    public int getItemCount() {
        if (presenter != null) {
            return presenter.getMonsterCount();
        }
        return 0;
    }
}
