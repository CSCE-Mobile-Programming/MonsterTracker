package com.uark.csce.monstertracker.MainActivity;

import com.uark.csce.monstertracker.models.MonsterRepository;
import com.uark.csce.monstertracker.models.info.MonsterInfo;
import java.util.List;

public interface MainContract {
    interface View {
        public void setPresenter(Presenter presenter);
        public void startScenarioActivity();
        public void startConnectActivity();
        public void setupMonsterInfos(List<MonsterInfo> infos);
        public void showMonsterPicker(List<MonsterInfo> monsters);
        public void addMonsterInfoToList(MonsterInfo monster);
    }

    interface Presenter {
        public void setView(View view);
        public void setRepository(MonsterRepository repository);
        public void start();
        public void scenarioActivityButtonClicked();
        public void scenarioActivityResult(String scenarioName);
        public void connectActivityButtonClicked();
        public int getMonsterCount(String monsterInfoName);
        public void addMonsterButtonClicked();
        public void monsterPickerReturned(String monsterName);
    }

}
