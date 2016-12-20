package br.com.opining.mvp.home.create.room;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
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

        builder.setView(view)
                .setPositiveButton(R.string.send, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        createRoom();
                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        CreateRoomDialogFragment.this.getDialog().cancel();
                    }
                });

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
