package br.com.opining.mvp.register;

import br.com.opining.mvp.AppPresenter;

public interface RegisterPresenter extends AppPresenter{
    void doRegister(String name, String email, String password, String repeatPassword);
}
