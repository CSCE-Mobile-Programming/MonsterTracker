package com.uark.csce.monstertracker.MonsterDetailsActivity;

public interface MonsterDetailsContract {
    interface View {
        public void setPresenter(Presenter presenter);
    }

    interface Presenter {
        public void setView(View view);
        public void start();
    }
}
