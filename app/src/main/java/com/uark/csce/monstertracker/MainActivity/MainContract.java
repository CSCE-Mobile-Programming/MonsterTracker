package com.uark.csce.monstertracker.MainActivity;

public interface MainContract {
    interface View {
        public void setPresenter(Presenter presenter);
        public void startScenarioActivity();
    }

    interface Presenter {
        public void setView(View view);
        public void start();
        public void scenarioActivityButtonClicked();
        public void scenarioActivityResult(String scenario);
    }
}
