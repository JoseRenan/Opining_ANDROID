package br.com.opining.mvp.home.create.room;

import android.content.Context;

import br.com.opining.mvp.AppView;

public interface CreateRoomView {
    void notifyRoomCreated();
    Context getContext();
    void changeCharacterCount(int count);
}
