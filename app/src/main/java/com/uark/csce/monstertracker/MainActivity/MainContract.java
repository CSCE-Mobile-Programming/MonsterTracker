package com.uark.csce.monstertracker.MainActivity;

import com.uark.csce.monstertracker.models.MonsterRepository;
import com.uark.csce.monstertracker.models.info.MonsterInfo;
import java.util.List;

public interface MainContract {
    interface View {
        public void setPresenter(Presenter presenter);

        public void startScenarioActivity();
        public void startConnectActivity();

        public void showMonsterPicker();

        public void monsterInfoChanged(List<MainAdapterModel> models);
    }

    interface Presenter {
        public void setView(View view);
        public void setRepository(MonsterRepository repository);
        public void start();
        public void pause();

        public void scenarioActivityButtonClicked();
        public void connectActivityButtonClicked();
        public void addMonsterButtonClicked();
        public void drawAllButtonClicked();

        public void scenarioActivityResult(String scenarioName);

        public void monsterPickerReturned(String monsterName);

        public List<MonsterInfo> getAllMonsterInfos();
        public void removeMonster(String monsterInfoName);
    }

}
