package br.com.opining.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserInfo;

import br.com.opining.R;
import br.com.opining.helpers.AndroidHelper;

public class HomeActivity extends AppCompatActivity {

    private Toolbar tbMain;
    private FloatingActionButton fab;
    private FirebaseAuth firebase;
    private UserInfo user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        firebase = FirebaseAuth.getInstance();
        user = firebase.getCurrentUser();

        FirebaseAuth.AuthStateListener listener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() == null){
                    Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };

        firebase.addAuthStateListener(listener);

        tbMain = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tbMain);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        fab = (FloatingActionButton) findViewById(R.id.btn_create_room);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                AndroidHelper.showSnackbar(HomeActivity.this, "Briiinca :v");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.act_logout){
            doLogout();
        } else if (id == R.id.act_settings) {
            Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
            HomeActivity.this.startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void doLogout() {
        firebase.signOut();
    }
}
