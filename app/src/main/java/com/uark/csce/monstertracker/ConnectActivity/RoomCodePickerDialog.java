package com.uark.csce.monstertracker.ConnectActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.uark.csce.monstertracker.R;

public class RoomCodePickerDialog extends DialogFragment {

    public interface RoomCodePickerListener {
        public void onRoomCodePicked(String roomCode);
    }

    RoomCodePickerListener listener;

    public RoomCodePickerDialog(RoomCodePickerListener listener)
    {
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();


        builder.setTitle("Room Code");

        builder.setView(inflater.inflate(R.layout.dialog_roompicker, null));

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        builder.setPositiveButton("Create Room", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                String roomCode = ((TextView)getDialog().findViewById(R.id.editTextRoomCodeSelect)).getText().toString();
                listener.onRoomCodePicked(roomCode);

            }
        });

        // Create the AlertDialog object and return it
        return builder.create();
    }

}
