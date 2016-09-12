package br.com.opining.activities;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import br.com.opining.R;
import br.com.opining.connection.ApiService;
import br.com.opining.helpers.AndroidHelper;
import br.com.opining.helpers.ErrorHelper;
import br.com.opining.helpers.RetrofitHelper;
import br.com.opining.model.Client;
import br.com.opining.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private Toolbar tbMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tbMain = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tbMain);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }


    public void doRegister(View v){
        EditText edt_name = (EditText) findViewById(R.id.edt_name);
        EditText edt_login = (EditText) findViewById(R.id.edt_login);
        EditText edt_email = (EditText) findViewById(R.id.edt_email);
        EditText edt_password = (EditText) findViewById(R.id.edt_password);

        final Client client = new Client();
        User user = new User();

        user.setName(edt_name.getText().toString());
        user.setLogin(edt_login.getText().toString());
        client.setUser(user);
        client.setPassword(edt_password.getText().toString());

        ApiService apiService = RetrofitHelper.createApiService();
        Call<User> call = apiService.registerUser(client);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    Bundle bundle = new Bundle();

                    bundle.putString("login", response.body().getLogin());
                    bundle.putString("password", client.getPassword());

                    startActivity(intent);
                    RegisterActivity.this.finish();
                }
                else{
                    String errorMessage = ErrorHelper.parseError(RegisterActivity.this, response);
                    AndroidHelper.showSnackbar(RegisterActivity.this, errorMessage);
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                AndroidHelper.showSnackbar(RegisterActivity.this, R.string.error);
            }
        });
    }
}
