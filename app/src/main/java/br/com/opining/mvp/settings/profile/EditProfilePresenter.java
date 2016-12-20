package br.com.opining.mvp.settings.profile;

import br.com.opining.mvp.AppPresenter;

public interface EditProfilePresenter extends AppPresenter {
    void doUpdate(String newEmail, String newName);
}
