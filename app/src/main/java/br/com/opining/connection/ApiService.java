package br.com.opining.connection;

import br.com.opining.model.Client;
import br.com.opining.model.Token;
import br.com.opining.model.User;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {

    String BASE_URL = "http://localhost:8080/Opining_SERVICE";

    @FormUrlEncoded
    @POST("/access/login")//TODO: indentificar endereços no serviço
    Call<Token> login(@Field("login") String login,
                      @Field("password") String password);

    @POST("/user/register")
    Call<User> registerUser(Client user);

    @POST("/user/edit/profile")
    Call<User> updateProfile(User user);

    @FormUrlEncoded
    @POST("/edit/password")
    Call<Token> changePassword(@Field("old_password") String oldPassword,
                               @Field("new_password") String newPassword,
                               @Field("invalidate_token") Boolean invalidateToken);

    @FormUrlEncoded
    @POST("/user/invalidate")
    Call<Void> invalidate(@Field("password") String password);
}
