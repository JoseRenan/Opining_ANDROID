package br.com.opining.mvp.home;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import br.com.opining.R;
import br.com.opining.domain.Room;

public class DebatesAdapter extends RecyclerView.Adapter {

    private List<Room> rooms;

    public DebatesAdapter(List<Room> items) {
        this.rooms = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.debate_card, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        ViewHolder holder = (ViewHolder) viewHolder;
        Room item  = rooms.get(position) ;

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getDefault());
        calendar.setTimeInMillis(item.getTimestampLong());

        holder.lblName.setText(item.getAuthorId());
        holder.lblPost.setText("\"" + item.getContent() + "\"");
        holder.lblDate.setText(String.format("%02d/%02d/%04d, %02d:%02d",
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE)));
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView lblName;
        public TextView lblPost;
        public TextView lblDate;
        public TextView lblParticipants;

        public ViewHolder(View view) {
            super(view);
            lblName = (TextView) view.findViewById(R.id.lbl_user_name);
            lblPost = (TextView) view.findViewById(R.id.lbl_post);
            lblDate = (TextView) view.findViewById(R.id.lbl_date);
            lblParticipants = (TextView) view.findViewById(R.id.lbl_count_participants);
        }
    }
}