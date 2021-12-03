package com.uark.csce.monstertracker.MonsterDetailsActivity;

import com.uark.csce.monstertracker.models.Monster;
import com.uark.csce.monstertracker.models.MonsterRepository;
import com.uark.csce.monstertracker.models.info.CardInfo;
import com.uark.csce.monstertracker.models.info.MonsterInfo;

import java.util.List;

public interface MonsterDetailsContract {
    interface View {
        public void setPresenter(Presenter presenter);
        public void monsterListChanged(List<Monster> monsters);
        public void monsterCardChanged(CardInfo currentCard);
    }

    interface Presenter {
        public void setView(View view);
        public void setRepository(MonsterRepository repository);

        public void start();
        public void pause();

        public void setMonsterInfo(String monsterInfoName);
        public MonsterInfo getMonsterInfo();

        public void addLevel();
        public void subtractLevel();
        public int getLevel();

        public void addMonster(boolean isElite);
        public void addHealth(int position);
        public void subtractHealth(int position);
        public void toggleStatus(String statusName, int position);
        public void drawCard();
    }
}
