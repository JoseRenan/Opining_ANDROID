package br.com.opining.connection;

import br.com.opining.model.Client;
import br.com.opining.model.Token;
import br.com.opining.model.User;
import retrofit2.Call;
import retrofit2.http.POST;

/**
 * Created by Juan on 16/06/2016.
 */
public interface ApiService {
    String BASE_URL = "http://localhost:8080/Opining_SERVICE";

    @POST("/")//TODO: indentificar endereços no serviço
    Call<Token> login(String login, String password);

    @POST("/")
    Call<User> registerUser(Client user);

    @POST("/")
    Call<User> updateProfile(User user);

    @POST("/")
    Call<Token> changePassword(String oldPassword, String newPassword, Boolean invalidateToken);

    @POST("/")
    Call<Void> invalidate(String password);

}
