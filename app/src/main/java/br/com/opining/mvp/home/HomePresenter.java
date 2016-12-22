package br.com.opining.mvp.home;

import java.util.List;

import br.com.opining.domain.Room;
import br.com.opining.mvp.AppPresenter;

public interface HomePresenter extends AppPresenter {
    void doLogout();
    List<Room> getRooms();
    void startListening();
}
