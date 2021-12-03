package com.uark.csce.monstertracker.ConnectActivity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.uark.csce.monstertracker.R;

public class ConnectFragment extends Fragment implements ConnectContract.View, RoomCodePickerDialog.RoomCodePickerListener {
    ConnectContract.Presenter presenter;

    public ConnectFragment() {
        // Required empty public constructor
    }

    public static ConnectFragment newInstance() {
        ConnectFragment fragment = new ConnectFragment();
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
        View root = inflater.inflate(R.layout.fragment_connect, container, false);

        // Set up the actions for the buttons
        Button joinButton = (Button)root.findViewById(R.id.joinRoomButton);
        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.joinButtonClicked();;
            }
        });

        return root;
    }

    @Override
    public void setPresenter(ConnectContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showRoomCodePickerDialog() {
        RoomCodePickerDialog dialog = new RoomCodePickerDialog(this);
        dialog.show(getParentFragmentManager(), "roomCode");
    }

    @Override
    public void closeActivity() {
        getActivity().finish();
    }

    @Override
    public void onRoomCodePicked(String roomCode) {
        presenter.roomCodeSelected(roomCode);
    }
}