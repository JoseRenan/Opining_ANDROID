package br.com.opining.mvp.home;

import br.com.opining.domain.Room;
import br.com.opining.mvp.AppView;

public interface HomeView extends AppView {
    void logoutSuccessful();
    void updateDebatesDataset();
}
