package br.com.opining.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.com.opining.R;

public class LoginActivity extends Activity {

    private Button btnRedirectRegister;
    private Button btnRedirectEnter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnRedirectRegister = (Button) findViewById(R.id.btn_register_redirect);
        btnRedirectRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(intent);
            }
        });

        EditText edt_login = (EditText) findViewById(R.id.edt_login);
        EditText edt_password = (EditText) findViewById(R.id.edt_password);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            if(!bundle.getString("login").isEmpty() && !bundle.getString("password").isEmpty()) {
                edt_login.setText(bundle.getString("login"));
                edt_password.setText(bundle.getString("password"));
                doLogin(null);
            }
        }
    }

    public void doLogin(View v){
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        LoginActivity.this.startActivity(intent);
    }

}
