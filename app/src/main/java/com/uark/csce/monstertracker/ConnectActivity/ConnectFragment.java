package com.uark.csce.monstertracker.ConnectActivity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uark.csce.monstertracker.R;

public class ConnectFragment extends Fragment implements ConnectContract.View {
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
        return inflater.inflate(R.layout.fragment_connect, container, false);
    }

    @Override
    public void setPresenter(ConnectContract.Presenter presenter) {
        this.presenter = presenter;
    }
}