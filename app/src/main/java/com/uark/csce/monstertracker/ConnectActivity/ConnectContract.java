package com.uark.csce.monstertracker.ConnectActivity;

import com.uark.csce.monstertracker.models.MonsterRepository;

public interface ConnectContract {
    interface View {
        public void setPresenter(Presenter presenter);
        public void showRoomCodePickerDialog();
    }

    interface Presenter {
        public void setView(View view);
        public void start();
        public void joinButtonClicked();
        public void roomCodeSelected(String roomCode);
        public void setRepository(MonsterRepository repository);
    }
}
