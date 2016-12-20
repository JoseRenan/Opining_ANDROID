package br.com.opining.mvp.register;

import br.com.opining.mvp.AppView;

public interface RegisterView  extends AppView {
    void notifyRegisterError(String msg);
    void notifyRegisterSuccessful();
    void showInvalidPasswordError(String msg);
    void showInvalidEmailError(String msg);
    void showInvalidPasswordMatchError();
    void showInvalidNameError(String msg);
}
