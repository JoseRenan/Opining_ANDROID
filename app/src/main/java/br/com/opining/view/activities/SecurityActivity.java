package br.com.opining.view.activities;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import br.com.opining.R;

public class SecurityActivity extends AppCompatActivity {

    private Toolbar tbMain;
    private EditText edtCurrentPassword;
    private EditText edtNewPassword;
    private EditText edtRepeatPassword;
    private Button btnUpdatePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);

        tbMain = (Toolbar) findViewById(R.id.tb_main);
        TextView lbTitleToolbar = (TextView) findViewById(R.id.lb_title);
        lbTitleToolbar.setText(R.string.security);

        setSupportActionBar(tbMain);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtCurrentPassword = (EditText) findViewById(R.id.edt_current_password);
        edtNewPassword = (EditText) findViewById(R.id.edt_new_password);
        edtRepeatPassword = (EditText) findViewById(R.id.edt_repeat_new_password);
        btnUpdatePassword = (Button) findViewById(R.id.btn_update);
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
