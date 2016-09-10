package br.com.opining.helpers;

import android.content.Context;

import java.io.IOException;

import br.com.opining.connection.ApiService;
import okhttp3.Authenticator;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by Juan on 16/06/2016.
 */
public class RetrofitHelper {

    public static ApiService createApiServiceWithToken(Context context) {

        final String token = AndroidHelper.loadToken(context);

        OkHttpClient okttp = new OkHttpClient.Builder()
                .authenticator(new Authenticator() {
                    @Override
                    public Request authenticate(Route route, Response response) throws IOException {
                        return response.request().newBuilder()
                                .header("Authorization", token ).build();
                    }
                }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okttp)
                .build();
        return retrofit.create(ApiService.class);
    }

    public static ApiService createApiService() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(ApiService.class);
    }

}
