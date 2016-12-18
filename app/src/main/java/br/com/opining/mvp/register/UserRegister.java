package br.com.opining.mvp.register;

import br.com.opining.model.User;

public interface UserRegister {

    interface OnRegisterListener {
        void onRegisterError(Exception e);
        void onRegisterSuccessful();
    }

    void doRegister(User user);
}
