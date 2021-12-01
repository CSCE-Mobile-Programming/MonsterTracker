package com.uark.csce.monstertracker.MonsterDetailsActivity;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.uark.csce.monstertracker.R;
import com.uark.csce.monstertracker.models.Monster;
import com.uark.csce.monstertracker.models.MonsterType;
import com.uark.csce.monstertracker.models.info.AttributesInfo;
import com.uark.csce.monstertracker.models.info.MonsterStatsInfo;

import java.io.IOException;
import java.io.InputStream;

public class MonsterDetailsAdapter extends RecyclerView.Adapter<MonsterDetailsAdapter.ViewHolder> {
    private MonsterDetailsContract.Presenter presenter;
    private AssetManager assetManager;
    private static final float disabledAlpha = 0.25f;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvMonsterNumber;
        private final TextView tvMonsterHealth;

        private final Button btnHealthAdd;
        private final Button btnHealthSubtract;

        private final ImageView iconDisarm;
        private final ImageView iconImmobilize;
        private final ImageView iconInvisible;
        private final ImageView iconMuddle;
        private final ImageView iconPoison;
        private final ImageView iconStrengthen;
        private final ImageView iconStun;
        private final ImageView iconWound;

        // Switches for attributes will go here...

        public ViewHolder(@NonNull View view) {
            super(view);

            tvMonsterNumber = (TextView)view.findViewById(R.id.tvMonsterNumber);
            tvMonsterHealth = (TextView)view.findViewById(R.id.tvMonsterHealth);
            btnHealthAdd = (Button)view.findViewById(R.id.btnHealthAdd);
            btnHealthSubtract = (Button)view.findViewById(R.id.btnHealthSubtract);
            iconDisarm = view.findViewById(R.id.iconDisarm);
            iconImmobilize = view.findViewById(R.id.iconImmobilize);
            iconInvisible = view.findViewById(R.id.iconInvisible);
            iconMuddle = view.findViewById(R.id.iconMuddle);
            iconPoison = view.findViewById(R.id.iconPoison);
            iconStrengthen = view.findViewById(R.id.iconStrengthen);
            iconStun = view.findViewById(R.id.iconStun);
            iconWound = view.findViewById(R.id.iconWound);
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
        public ImageView getIconDisarm() {
            return iconDisarm;
        }
        public ImageView getIconImmobilize() {
            return iconImmobilize;
        }
        public ImageView getIconInvisible() {
            return iconInvisible;
        }
        public ImageView getIconMuddle() {
            return iconMuddle;
        }
        public ImageView getIconPoison() {
            return iconPoison;
        }
        public ImageView getIconStrengthen() {
            return iconStrengthen;
        }
        public ImageView getIconStun() {
            return iconStun;
        }
        public ImageView getIconWound() {
            return iconWound;
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
        assetManager = view.getContext().getAssets();
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
                setStatusEffects(holder, MonsterType.Normal, monster, position);
            }
            else {
                holder.getTvMonsterNumber().setTextColor(ContextCompat.getColor(holder.getTvMonsterNumber().getContext(), R.color.black));
                holder.getTvMonsterHealth().setTextColor(ContextCompat.getColor(holder.getTvMonsterHealth().getContext(), R.color.black));
                setStatusEffects(holder, MonsterType.Elite, monster, position);
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

    public void setStatusEffects(ViewHolder holder, MonsterType type, Monster monster, int position) {
        // First, fetch the stats for this level of monster and then get attributes based on type
        MonsterStatsInfo monsterStatsInfo = presenter.getMonsterInfo().getStats().get(presenter.getLevel());
        AttributesInfo attributesInfo = null;
        if (type == MonsterType.Elite) {
            attributesInfo = monsterStatsInfo.getElite().getAttributesInfo();
        }
        else {
            attributesInfo = monsterStatsInfo.getNormal().getAttributesInfo();
        }

        // For each of the status effects we're going to set, we need to check for immunities. If
        // immune, don't show the associated icon.
        // Disarm
        holder.getIconDisarm().setTag("disarm");
        if (attributesInfo.isImmuneToDisarm()) {
            holder.getIconDisarm().setVisibility(View.INVISIBLE);
        }
        else {
            holder.getIconDisarm().setAlpha(monster.getAttributes().isDisarmed() ? 1.0f : disabledAlpha);
            holder.getIconDisarm().setOnClickListener(buildIconClickListener(position));
        }

        // Immobilize
        holder.getIconImmobilize().setTag("immobilize");
        if (attributesInfo.isImmuneToImmobilize()) {
            holder.getIconImmobilize().setVisibility(View.INVISIBLE);
        }
        else {
            holder.getIconImmobilize().setAlpha(monster.getAttributes().isImmobilized() ? 1.0f : disabledAlpha);
            holder.getIconImmobilize().setOnClickListener(buildIconClickListener(position));
        }

        // Invisible. Nothing has immunity to invisible since it's a positive effect.
        holder.getIconInvisible().setTag("invisible");
        holder.getIconInvisible().setAlpha(monster.getAttributes().isInvisible() ? 1.0f : disabledAlpha);
        holder.getIconInvisible().setOnClickListener(buildIconClickListener(position));

        // Muddle
        holder.getIconMuddle().setTag("muddle");
        if (attributesInfo.isImmuneToMuddle()) {
            holder.getIconMuddle().setVisibility(View.INVISIBLE);
        }
        else {
            holder.getIconMuddle().setAlpha(monster.getAttributes().isMuddled() ? 1.0f : disabledAlpha);
            holder.getIconMuddle().setOnClickListener(buildIconClickListener(position));
        }

        // Poison
        holder.getIconPoison().setTag("poison");
        if (attributesInfo.isImmuneToPoison()) {
            holder.getIconPoison().setVisibility(View.INVISIBLE);
        }
        else {
            holder.getIconPoison().setAlpha(monster.getAttributes().isPoisoned() ? 1.0f : disabledAlpha);
            holder.getIconPoison().setOnClickListener(buildIconClickListener(position));
        }

        // Strengthen. Nothing has immunity to Strengthen since it's a positive effect.
        holder.getIconStrengthen().setTag("strengthen");
        holder.getIconStrengthen().setAlpha(monster.getAttributes().isStrengthened() ? 1.0f : disabledAlpha);
        holder.getIconStrengthen().setOnClickListener(buildIconClickListener(position));

        // Stun.
        holder.getIconStun().setTag("stun");
        if (attributesInfo.isImmuneToStun()) {
            holder.getIconStun().setVisibility(View.INVISIBLE);
        }
        else {
            holder.getIconStun().setAlpha(monster.getAttributes().isStunned() ? 1.0f : disabledAlpha);
            holder.getIconStun().setOnClickListener(buildIconClickListener(position));
        }

        // Wound
        holder.getIconWound().setTag("wound");
        if (attributesInfo.isImmuneToWound()) {
            holder.getIconWound().setVisibility(View.INVISIBLE);
        }
        else {
            holder.getIconWound().setAlpha(monster.getAttributes().isWounded() ? 1.0f : disabledAlpha);
            holder.getIconWound().setOnClickListener(buildIconClickListener(position));
        }
    }

    private View.OnClickListener buildIconClickListener(int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String statusName = view.getTag().toString();
                presenter.toggleStatus(statusName, position, new MonsterDetailsContract.ToggleStatusCallback() {
                    @Override
                    public void onToggleStatus(boolean currentState) {
                        view.setAlpha(currentState ? 1.0f : disabledAlpha);
                    }
                });
            }
        };
    }
}
