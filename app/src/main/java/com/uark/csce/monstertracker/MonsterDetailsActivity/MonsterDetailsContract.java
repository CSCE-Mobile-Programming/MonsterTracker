package com.uark.csce.monstertracker.MonsterDetailsActivity;

import com.uark.csce.monstertracker.models.MonsterRepository;
import com.uark.csce.monstertracker.models.info.MonsterInfo;

public interface MonsterDetailsContract {
    interface View {
        public void setPresenter(Presenter presenter);
    }

    interface Presenter {
        public void setView(View view);
        public void setRepository(MonsterRepository repository);
        public void start();
        public void setMonsterInfo(String monsterInfoName);
        public MonsterInfo getMonsterInfo();
    }
}
