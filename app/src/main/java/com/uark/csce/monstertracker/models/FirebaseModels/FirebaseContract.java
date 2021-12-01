package com.uark.csce.monstertracker.models.FirebaseModels;

import com.uark.csce.monstertracker.models.Monster;
import com.uark.csce.monstertracker.models.info.CardInfo;
import com.uark.csce.monstertracker.models.info.MonsterInfo;

import java.util.List;

public interface FirebaseContract {
    interface FirebaseCallback {
        void notifyMonsterInfoChanged(List<MainActivityInfo> mainActivityInfos);
        void notifyMonsterInstanceChanged(List<Monster> monsters);
        void notifyMonsterCardsChanged(CardInfo currentCard);
    }
}
