package com.uark.csce.monstertracker.MainActivity;

public interface MainContract {
    interface View {
        public void setPresenter(Presenter presenter);
        public void startDetailsActivity();
    }

    interface Presenter {
        public void setView(View view);
        public void start();
    }
}
