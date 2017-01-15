package br.com.opining.mvp.home.create.room;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import br.com.opining.R;

public class CreateRoomDialogFragment extends DialogFragment implements CreateRoomView {

    private CreateRoomPresenter mPresenter;
    private EditText edtPost;
    private TextView labelQuant;
    private TextInputLayout inputLayout;

    @Override
    public void onStart() {
        super.onStart();
        changeCharacterCount(getContext().getResources().getInteger(R.integer.max_theme_name));
        edtPost.requestFocus();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        mPresenter = new CreateRoomPresenterImpl(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.modal_new_room, null);
        labelQuant = (TextView) view.findViewById(R.id.quant_char_text);
        edtPost = (EditText) view.findViewById(R.id.edt_post);
        inputLayout = (TextInputLayout) view.findViewById(R.id.textInputLayout);
        edtPost.addTextChangedListener(new CreateRoomTextWatcher(this));

        Button btn_create_room = (Button) view.findViewById(R.id.btn_create_room);

        btn_create_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createRoom();
            }
        });

        Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        builder.setView(view);
        return builder.create();
    }

    private void createRoom () {
        if (edtPost.getText().length() <= 0){
            inputLayout.setError(getString(R.string.error_lenght_theme));
        }
        else {
            mPresenter.createRoom(edtPost.getText().toString());
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void notifyRoomCreated() {
        CreateRoomDialogFragment.this.getDialog().dismiss();
    }

    @Override
    public void changeCharacterCount(int count) {
        labelQuant.setText(count + " " + getString(R.string.caracter_count));
    }
}
