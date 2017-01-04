package br.com.opining.mvp.home.create.room;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.com.opining.R;

public class CreateRoomDialogFragment extends DialogFragment implements CreateRoomView {

    private CreateRoomPresenter mPresenter;
    private EditText edtPost;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        mPresenter = new CreateRoomPresenterImpl(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.modal_new_room, null);
        edtPost = (EditText) view.findViewById(R.id.edt_post);
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
        mPresenter.createRoom(edtPost.getText().toString());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void notifyRoomCreated() {
        CreateRoomDialogFragment.this.getDialog().dismiss();
    }
}
