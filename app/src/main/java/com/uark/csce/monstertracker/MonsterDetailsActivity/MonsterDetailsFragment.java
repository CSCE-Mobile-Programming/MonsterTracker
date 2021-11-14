package com.uark.csce.monstertracker.MonsterDetailsActivity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uark.csce.monstertracker.R;
import com.uark.csce.monstertracker.models.Attributes;
import com.uark.csce.monstertracker.models.info.AttributesInfo;
import com.uark.csce.monstertracker.models.info.MonsterInfo;
import com.uark.csce.monstertracker.models.info.MonsterStatsInfo;

public class MonsterDetailsFragment extends Fragment implements MonsterDetailsContract.View {
    MonsterDetailsContract.Presenter presenter;

    public MonsterDetailsFragment() {
        // Required empty public constructor
    }

    public static MonsterDetailsFragment newInstance() {
        MonsterDetailsFragment fragment = new MonsterDetailsFragment();
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
        View root = inflater.inflate(R.layout.fragment_monster_details, container, false);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        MonsterInfo info = presenter.getMonsterInfo();
        populateInfo(this.getView(), info);
        populateAttributes(this.getView(), info);
    }

    @Override
    public void setPresenter(MonsterDetailsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    private void populateInfo(View view, MonsterInfo info) {
        // Set up normal and elite info
        MonsterStatsInfo stats = info.getStats().get(0);
        ((TextView)view.findViewById(R.id.tvNormalHealth)).setText(Integer.toString(stats.getNormal().getHealth()));
        ((TextView)view.findViewById(R.id.tvNormalMove)).setText(Integer.toString(stats.getNormal().getMove()));
        ((TextView)view.findViewById(R.id.tvNormalAttack)).setText(Integer.toString(stats.getNormal().getAttack()));
        ((TextView)view.findViewById(R.id.tvNormalRange)).setText(Integer.toString(stats.getNormal().getRange()));
        ((TextView)view.findViewById(R.id.tvEliteHealth)).setText(Integer.toString(stats.getElite().getHealth()));
        ((TextView)view.findViewById(R.id.tvEliteMove)).setText(Integer.toString(stats.getElite().getMove()));
        ((TextView)view.findViewById(R.id.tvEliteAttack)).setText(Integer.toString(stats.getElite().getAttack()));
        ((TextView)view.findViewById(R.id.tvEliteRange)).setText(Integer.toString(stats.getElite().getRange()));
    }

    private void populateAttributes(View view, MonsterInfo info) {
        // Reverse case! Time to parse all. of. the. attributes. :(
        AttributesInfo normalAttr = info.getStats().get(0).getNormal().getAttributesInfo();
        AttributesInfo eliteAttr = info.getStats().get(0).getElite().getAttributesInfo();

        AttributesInfo[] infos = new AttributesInfo[] {normalAttr, eliteAttr};
        StringBuilder[] attributeTexts = new StringBuilder[] {new StringBuilder(), new StringBuilder()};
        for (int i = 0; i < 2; i++) {
            StringBuilder sb = attributeTexts[i];
            if (infos[i].isAttackersGainDisadvantage()) {
                sb.append("Attackers gain Disadvantage\n");
            }
            if (infos[i].isFlying()) {
                sb.append("Flying\n");
            }
            if (infos[i].isImmuneToCurse()) {
                sb.append("Curse\n");
            }
            if (infos[i].isImmuneToDisarm()) {
                sb.append("Disarm\n");
            }
            if (infos[i].isImmuneToImmobilize()) {
                sb.append("Immobilize\n");
            }
            if (infos[i].isImmuneToMuddle()) {
                sb.append("Muddle\n");
            }
            if (infos[i].isImmuneToPoison()) {
                sb.append("Poison\n");
            }
            if (infos[i].isImmuneToStun()) {
                sb.append("Stun\n");
            }
            if (infos[i].isImmuneToWound()) {
                sb.append("Wound\n");
            }
            if (infos[i].getRetaliate() > 0) {
                if (infos[i].getRetaliateRange() > 1) {
                    sb.append("Retaliate: " + infos[i].getRetaliate() + ", Range: " + infos[i].getRetaliateRange() + "\n");
                }
                else {
                    sb.append("Retaliate: " + infos[i].getRetaliate() + "\n");
                }
            }
            if (infos[i].getShield() > 0) {
                sb.append("Shield: " + infos[i].getShield() + "\n");
            }
            if (infos[i].getTarget() > 0) {
                sb.append("Target: " + infos[i].getTarget() + "\n");
            }
            if (infos[i].getPierce() > 0) {
                sb.append("Pierce: " + infos[i].getPierce() + "\n");
            }
        }

        ((TextView)view.findViewById(R.id.tvNormalAttributesList)).setText(attributeTexts[0].toString());
        ((TextView)view.findViewById(R.id.tvEliteAttributesList)).setText(attributeTexts[1].toString());
    }
}