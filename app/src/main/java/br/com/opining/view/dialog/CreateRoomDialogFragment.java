package br.com.opining.view.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.opining.R;

public class CreateRoomDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.modal_new_room, null);
        final EditText post = (EditText) view.findViewById(R.id.edt_post);

        builder.setView(view)

                // Add action buttons
                .setPositiveButton(R.string.send, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
                        myRef.child("posts").push().setValue(post.getText().toString());
                    }
                })

                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        CreateRoomDialogFragment.this.getDialog().cancel();
                    }
                });

        return builder.create();
    }
}
