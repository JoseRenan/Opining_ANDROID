package br.com.opining.mvp.settings.profile;

import br.com.opining.mvp.AppView;

public interface EditProfileView extends AppView {
    void notifySuccessfulEdit();
    void notifyErrorEdit(String msg);
    void showInvalidNameError(String msg);
    void showInvalidEmailError(String msg);
}
