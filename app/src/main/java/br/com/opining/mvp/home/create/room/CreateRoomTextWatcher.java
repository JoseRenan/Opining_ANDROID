package br.com.opining.mvp.home.create.room;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;


/**
 * Created by Juan on 14/01/2017.
 */

public class CreateRoomTextWatcher implements TextWatcher {
        private CreateRoomView view;
        public static final int quant_letters = 90;

        public CreateRoomTextWatcher(CreateRoomView view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence,  int start, int before, int count) {}

        @Override
        public void onTextChanged(CharSequence charSequence,  int start, int before, int count) {
            view.changeCharacterCount(quant_letters - charSequence.length());
        }

        @Override
        public void afterTextChanged(Editable editable) {}
}

