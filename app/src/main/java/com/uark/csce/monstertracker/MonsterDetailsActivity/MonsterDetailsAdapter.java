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
import androidx.fragment.app.FragmentManager;
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
                setStatusEffects(holder, MonsterType.Normal);
            }
            else {
                holder.getTvMonsterNumber().setTextColor(ContextCompat.getColor(holder.getTvMonsterNumber().getContext(), R.color.black));
                holder.getTvMonsterHealth().setTextColor(ContextCompat.getColor(holder.getTvMonsterHealth().getContext(), R.color.black));
                setStatusEffects(holder, MonsterType.Elite);
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

    public void setStatusEffects(ViewHolder holder, MonsterType type) {
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
        try {
            // Disarm
            holder.getIconDisarm().setTag("disarm");
            if (attributesInfo.isImmuneToDisarm()) {
                holder.getIconDisarm().setVisibility(View.INVISIBLE);
            }
            else {
                holder.getIconDisarm().setImageBitmap(getBitmapFromAssets("icons/disarm.PNG"));
                holder.getIconDisarm().setAlpha(0.4f);
                holder.getIconDisarm().setOnClickListener(iconOnClickListener);
            }

            // Immobilize
            holder.getIconImmobilize().setTag("immobilize");
            if (attributesInfo.isImmuneToImmobilize()) {
                holder.getIconImmobilize().setVisibility(View.INVISIBLE);
            }
            else {
                holder.getIconImmobilize().setImageBitmap(getBitmapFromAssets("icons/immobilize.PNG"));
                holder.getIconImmobilize().setAlpha(0.4f);
                holder.getIconImmobilize().setOnClickListener(iconOnClickListener);
            }

            // Invisible. Nothing has immunity to invisible since it's a positive effect.
            holder.getIconInvisible().setTag("invisible");
            holder.getIconInvisible().setImageBitmap(getBitmapFromAssets("icons/invisible.PNG"));
            holder.getIconInvisible().setAlpha(0.4f);
            holder.getIconInvisible().setOnClickListener(iconOnClickListener);

            // Muddle
            holder.getIconMuddle().setTag("muddle");
            if (attributesInfo.isImmuneToMuddle()) {
                holder.getIconMuddle().setVisibility(View.INVISIBLE);
            }
            else {
                holder.getIconMuddle().setImageBitmap(getBitmapFromAssets("icons/muddle.PNG"));
                holder.getIconMuddle().setAlpha(0.4f);
                holder.getIconMuddle().setOnClickListener(iconOnClickListener);
            }

            // Poison
            holder.getIconPoison().setTag("poison");
            if (attributesInfo.isImmuneToPoison()) {
                holder.getIconPoison().setVisibility(View.INVISIBLE);
            }
            else {
                holder.getIconPoison().setImageBitmap(getBitmapFromAssets("icons/poison.PNG"));
                holder.getIconPoison().setAlpha(0.4f);
                holder.getIconPoison().setOnClickListener(iconOnClickListener);
            }

            // Strengthen. Nothing has immunity to Strengthen since it's a positive effect.
            holder.getIconStrengthen().setTag("strengthen");
            holder.getIconStrengthen().setImageBitmap(getBitmapFromAssets("icons/strengthen.PNG"));
            holder.getIconStrengthen().setAlpha(0.4f);
            holder.getIconStrengthen().setOnClickListener(iconOnClickListener);

            // Stun.
            holder.getIconStun().setTag("stun");
            if (attributesInfo.isImmuneToStun()) {
                holder.getIconStun().setVisibility(View.INVISIBLE);
            }
            else {
                holder.getIconStun().setImageBitmap(getBitmapFromAssets("icons/stun.PNG"));
                holder.getIconStun().setAlpha(0.4f);
                holder.getIconStun().setOnClickListener(iconOnClickListener);
            }

            // Wound
            holder.getIconWound().setTag("wound");
            if (attributesInfo.isImmuneToWound()) {
                holder.getIconWound().setVisibility(View.INVISIBLE);
            }
            else {
                holder.getIconWound().setImageBitmap(getBitmapFromAssets("icons/wound.PNG"));
                holder.getIconWound().setAlpha(0.4f);
                holder.getIconWound().setOnClickListener(iconOnClickListener);
            }
        }
        catch (IOException e) {
            Log.e("MonsterDetailsAdapter", "Failed to load asset");
        }
    }

    private View.OnClickListener iconOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String statusName = view.getTag().toString();
            Log.d("MonsterDetailsAdapter", statusName);
        }
    };

    public Bitmap getBitmapFromAssets(String fileName) throws IOException {
        InputStream istr = assetManager.open(fileName);
        Bitmap bitmap = BitmapFactory.decodeStream(istr);
        istr.close();

        return bitmap;
    }
}
