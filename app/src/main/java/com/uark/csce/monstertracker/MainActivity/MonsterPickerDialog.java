package com.uark.csce.monstertracker.MainActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.DialogFragment;

import com.uark.csce.monstertracker.R;
import com.uark.csce.monstertracker.models.info.MonsterInfo;

import java.util.List;

public class MonsterPickerDialog extends DialogFragment {

    public interface MonsterPickerListener
    {
        public void onMonsterPicked(String monsterName);
    }

    List<MonsterInfo> monsters;
    MonsterPickerListener listener;

    public MonsterPickerDialog(List<MonsterInfo> monsters, MonsterPickerListener listener)
    {
        this.monsters = monsters;
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Monster Picker");

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        String[] monsterNames = new String[monsters.size()];
        for(int i = 0; i< monsters.size(); i++)
        {
            monsterNames[i] = monsters.get(i).getName();
        }

        builder.setItems(monsterNames, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.onMonsterPicked(monsters.get(i).getName());
            }
        });

        // Create the AlertDialog object and return it
        return builder.create();
    }


}

