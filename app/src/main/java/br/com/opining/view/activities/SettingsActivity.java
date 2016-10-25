package br.com.opining.view.activities;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Set;

import br.com.opining.R;
import br.com.opining.view.adapters.ListItem;
import br.com.opining.view.adapters.SettingsListAdapter;

public class SettingsActivity extends AppCompatActivity {

    private Toolbar tbMain;
    private ListView listSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        tbMain = (Toolbar) findViewById(R.id.tb_main);
        TextView lbTitleToolbar = (TextView) findViewById(R.id.lb_title);
        lbTitleToolbar.setText(R.string.settings);

        setSupportActionBar(tbMain);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SettingsListAdapter adapter = new SettingsListAdapter(this, generateItems());
        listSettings = (ListView) findViewById(R.id.lst_settings);
        listSettings.setAdapter(adapter);

        listSettings.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                switch (position) {
                    case 0:
                        break;

                    case 1:
                        Intent intent = new Intent(SettingsActivity.this, SecurityActivity.class);
                        SettingsActivity.this.startActivity(intent);
                        break;

                    case 2:
                        break;

                    case 3:
                        break;

                    case 4:
                        break;
                }
            }
        });
    }

    public ArrayList<ListItem> generateItems() {

        ArrayList<ListItem> items = new ArrayList<>();

        items.add(new ListItem(R.drawable.ic_person_outline_black, R.string.profile));
        items.add(new ListItem(R.drawable.ic_lock_outline_black, R.string.security));
        items.add(new ListItem(R.drawable.ic_clear_black, R.string.remove_account));
        items.add(new ListItem(R.drawable.ic_call_black, R.string.contact));
        items.add(new ListItem(R.drawable.ic_info_outline_black, R.string.about));

        return items;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }


}
