package br.com.opining.mvp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

public interface AppView {
    Context getContext();
    Context getAppContext();
    AppCompatActivity get();
}
