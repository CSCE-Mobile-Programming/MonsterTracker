package com.uark.csce.monstertracker.ScenarioActivity;

public interface ScenarioContract {
    interface View {
        public void setPresenter(Presenter presenter);
    }

    interface Presenter {
        public void setView (View view);
        public void start();
    }
}
