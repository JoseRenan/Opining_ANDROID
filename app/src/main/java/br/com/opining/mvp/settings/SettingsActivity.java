package br.com.opining.mvp.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.opining.R;
import br.com.opining.helpers.AndroidHelper;
import br.com.opining.helpers.FirebaseUserHelper;
import br.com.opining.mvp.settings.profile.EditProfileActivity;
import br.com.opining.mvp.settings.security.SecurityActivity;
import br.com.opining.domain.ListItem;

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

                Intent intent;

                if (!FirebaseUserHelper.isLoggedByOtherProviders() && position == 0) {
                    intent = new Intent(SettingsActivity.this, EditProfileActivity.class);
                    SettingsActivity.this.startActivity(intent);
                }

                if (!FirebaseUserHelper.isLoggedByOtherProviders() && position == 1) {
                    intent = new Intent(SettingsActivity.this, SecurityActivity.class);
                    SettingsActivity.this.startActivity(intent);
                }

                if ((!FirebaseUserHelper.isLoggedByOtherProviders() && position == 2)
                    ||  (FirebaseUserHelper.isLoggedByOtherProviders() && position == 0)) {
                    AndroidHelper.showToast(SettingsActivity.this, "Esta opção do menu ainda não foi implementada");
                }

                if ((!FirebaseUserHelper.isLoggedByOtherProviders() && position == 3)
                        ||  (FirebaseUserHelper.isLoggedByOtherProviders() && position == 1)) {
                    AndroidHelper.showToast(SettingsActivity.this, "Esta opção do menu ainda não foi implementada");
                }

                if ((!FirebaseUserHelper.isLoggedByOtherProviders() && position == 4)
                        ||  (FirebaseUserHelper.isLoggedByOtherProviders() && position == 2)) {
                    intent = new Intent(SettingsActivity.this, AboutActivity.class);
                    SettingsActivity.this.startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (FirebaseUserHelper.isLoggedByOtherProviders())
            AndroidHelper.showToast(SettingsActivity.this, "As opções de edição de perfil e de senha" +
                    " estão temporariamente indisponíveis para usuários logados via rede social");
    }

    public ArrayList<ListItem> generateItems() {

        ArrayList<ListItem> items = new ArrayList<>();

        if (!FirebaseUserHelper.isLoggedByOtherProviders()) {
            items.add(new ListItem(R.drawable.ic_person_outline_black, R.string.profile));
            items.add(new ListItem(R.drawable.ic_lock_outline_black, R.string.security));
        }

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
