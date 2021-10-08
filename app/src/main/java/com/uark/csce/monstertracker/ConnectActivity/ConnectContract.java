package com.uark.csce.monstertracker.ConnectActivity;

public interface ConnectContract {
    interface View {
        public void setPresenter(Presenter presenter);
    }

    interface Presenter {
        public void setView(View view);
        public void start();
    }
}
