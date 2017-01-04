package br.com.opining.mvp.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

import br.com.opining.R;
import br.com.opining.domain.Room;
import br.com.opining.mvp.login.LoginActivity;
import br.com.opining.mvp.settings.SettingsActivity;
import br.com.opining.mvp.home.create.room.CreateRoomDialogFragment;

public class HomeActivity extends AppCompatActivity implements HomeView {

    private Toolbar tbMain;
    private FloatingActionButton fab;
    private HomePresenter mPresenter;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private LinearLayout contentLayout;
    private LinearLayout loadingLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mPresenter = new HomePresenterImpl(this);

        tbMain = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tbMain);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mRecyclerView = (RecyclerView) findViewById(R.id.lst_debates);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new DebatesAdapter(mPresenter.getRooms());
        mRecyclerView.setAdapter(mAdapter);
        contentLayout = (LinearLayout) findViewById(R.id.content_layout);
        loadingLayout = (LinearLayout) findViewById(R.id.loading_layout); 
        

        fab = (FloatingActionButton) findViewById(R.id.btn_create_room);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CreateRoomDialogFragment().show(getSupportFragmentManager(), "createRoom");
            }
        });
        mPresenter.startListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    public void loadingMode(boolean status){
        loadingLayout.setVisibility(View.VISIBLE);
        contentLayout.setVisibility(View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.act_logout:
                mPresenter.doLogout();
                break;
            case R.id.act_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void logoutSuccessful() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void updateDebatesDataset() {
        mAdapter.notifyDataSetChanged();
        mLayoutManager.scrollToPosition(0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public Context getAppContext() {
        return getApplicationContext();
    }

    @Override
    public AppCompatActivity get() {
        return this;
    }
}
