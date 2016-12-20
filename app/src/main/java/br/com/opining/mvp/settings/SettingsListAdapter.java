package br.com.opining.mvp.settings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.opining.R;
import br.com.opining.domain.ListItem;

public class SettingsListAdapter extends ArrayAdapter<ListItem> {

    private final Context context;
    private final ArrayList<ListItem> items;

    public SettingsListAdapter(Context context, ArrayList<ListItem> items) {

        super(context, R.layout.settings_item, items);

        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = null;

        rowView = inflater.inflate(R.layout.settings_item, parent, false);

        ImageView itemIcon = (ImageView) rowView.findViewById(R.id.item_icon);
        TextView itemTitle = (TextView) rowView.findViewById(R.id.item_title);

        itemIcon.setImageResource(items.get(position).getIcon());
        itemTitle.setText(items.get(position).getTitle());

        return rowView;
    }
}
