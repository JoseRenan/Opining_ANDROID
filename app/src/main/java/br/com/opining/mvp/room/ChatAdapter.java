package br.com.opining.mvp.room;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import br.com.opining.R;
import br.com.opining.domain.Message;
import br.com.opining.domain.Room;

public class ChatAdapter extends RecyclerView.Adapter {

    private List<Message> messages;
    private int last;

    public ChatAdapter(List<Message> items) {
        this.messages = items;
        this.last = 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if (last % 2 == 0)
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.talk_baloon_someone, parent, false);
        else
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.talk_baloon_you, parent, false);
        last++;
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        ViewHolder holder = (ViewHolder) viewHolder;
        Message item  = messages.get(position) ;
        Calendar calendar = item.getTimestamp();

        holder.author.setText(item.getAuthor().getName());
        holder.content.setText("\"" + item.getContent() + "\"");
        holder.timestamp.setText(String.format("%02d/%02d/%04d, %02d:%02d",
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE)));
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        private TextView author;
        private TextView content;
        private TextView timestamp;

        private ViewHolder(View view) {
            super(view);
            author = (TextView) view.findViewById(R.id.lbl_author);
            content = (TextView) view.findViewById(R.id.lbl_content);
            timestamp = (TextView) view.findViewById(R.id.lbl_timestamp);
        }
    }
}