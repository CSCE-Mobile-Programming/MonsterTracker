package com.uark.csce.monstertracker.ConnectActivity;

public interface ConnectContract {
    interface View {
        public void setPresenter(Presenter presenter);
        public void showRoomCodePickerDialog();
    }

    interface Presenter {
        public void setView(View view);
        public void start();
        public void createButtonClicked();
        public void joinButtonClicked();
        public void roomCodeSelected(String roomCode);
    }
}
