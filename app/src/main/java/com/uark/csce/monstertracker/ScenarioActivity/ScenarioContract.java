package com.uark.csce.monstertracker.ScenarioActivity;

import com.uark.csce.monstertracker.models.MonsterRepository;
import com.uark.csce.monstertracker.models.info.Scenario;

import java.util.List;

public interface ScenarioContract {
    interface View {
        public void setPresenter(Presenter presenter);
        public void notifyScenariosLoaded();
        public void setResultValue(String scenario);
        public void closeActivity();
    }

    interface Presenter {
        public void setView (View view);
        public void start();

        public void setRepository(MonsterRepository repository);
        public List<Scenario> getScenarios();
        public void getScenariosFromRepository();

        public void scenarioClicked(String scenario);
    }
}
