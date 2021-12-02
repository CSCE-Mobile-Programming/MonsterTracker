package com.uark.csce.monstertracker.models.FirebaseModels;

import com.uark.csce.monstertracker.models.Monster;
import com.uark.csce.monstertracker.models.info.CardInfo;
import com.uark.csce.monstertracker.models.info.MonsterInfo;

import java.util.List;
import java.util.Map;

public interface FirebaseContract {
    interface FirebaseCallback {
        void notifyGameStateChanged(Map<String, MonsterState> gameState);
        void notifyMonsterStateChanged(List<Monster> monsters);
    }
}
