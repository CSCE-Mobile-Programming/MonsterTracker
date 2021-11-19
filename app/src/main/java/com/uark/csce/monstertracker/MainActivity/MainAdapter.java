package com.uark.csce.monstertracker.MainActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uark.csce.monstertracker.MonsterDetailsActivity.MonsterDetailsActivity;
import com.uark.csce.monstertracker.R;
import com.uark.csce.monstertracker.models.info.MonsterInfo;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private List<MonsterInfo> localDataSet;
    private MainContract.Presenter presenter;
    AssetManager assetManager;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvMonsterName;
        private final TextView tvCount;
        private final TextView tvInitiative;
        private final ImageView ivPortrait;

        private final Button buttonDetails;

        public ViewHolder(@NonNull View view) {
            super(view);

            tvMonsterName = (TextView) view.findViewById(R.id.tvMonsterName);
            tvCount = (TextView) view.findViewById(R.id.tvCount);
            tvInitiative = (TextView) view.findViewById(R.id.tvInitiative);
            ivPortrait = (ImageView) view.findViewById(R.id.ivPortrait);
            buttonDetails = (Button) view.findViewById(R.id.buttonDetails);
        }

        public TextView getTvMonsterName() { return tvMonsterName; }
        public TextView getTvCount() { return tvCount; }
        public TextView getTvInitiative() { return tvInitiative; }
        public ImageView getIvPortrait() { return ivPortrait; }
        public Button getButtonDetails() { return buttonDetails; }
    }

    public MainAdapter(AssetManager manager) {
        localDataSet = new ArrayList<>();
        assetManager = manager;
    }

    public void setPresenter(MainContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_main_item, parent, false);
        return new ViewHolder(view);
    }

    @NonNull
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MonsterInfo info = localDataSet.get(position);

        holder.getTvMonsterName().setText(info.getName());
        holder.getTvCount().setText(presenter.getMonsterCount(info.getName()) + " / " + info.getMaxCount()); // dummy string. The RV will need some way to get this data
        holder.getTvInitiative().setText("Init. -"); // dummy string. The RV will need some way to get this data

        try {
            holder.getIvPortrait().setImageBitmap(getBitmapFromAssets("portraits/" + info.getName() + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        holder.getButtonDetails().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // start the associated monster details activity.
                Intent detailsIntent = new Intent(view.getContext(), MonsterDetailsActivity.class);
                detailsIntent.putExtra("monsterName", holder.getTvMonsterName().getText());
                view.getContext().startActivity(detailsIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    public void setLocalDataSet(List<MonsterInfo> localDataSet) {
        this.localDataSet = localDataSet;
    }

    public Bitmap getBitmapFromAssets(String fileName) throws IOException {
        InputStream istr = assetManager.open(fileName);
        Bitmap bitmap = BitmapFactory.decodeStream(istr);
        istr.close();

        return bitmap;
    }
}
