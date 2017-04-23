package br.com.opining.mvp.room;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.opining.R;
import br.com.opining.domain.Message;
import br.com.opining.domain.User;

public class RoomActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        mRecyclerView = (RecyclerView) findViewById(R.id.lst_chat);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ChatAdapter(generateChat());
        mRecyclerView.setAdapter(mAdapter);
    }

    public List<Message> generateChat() {
        Message message = new Message("Lorem ipsun dolor Lorem ipsun dolor Lorem ipsun dolor Lorem ipsun dolor " +
                "Lorem ipsun dolor Lorem ipsun dolor Lorem ipsun dolor Lorem ipsun dolor", new User("Teste", null, null));

        ArrayList<Message> messages = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            messages.add(message);
        }
        return messages;
    }
}
