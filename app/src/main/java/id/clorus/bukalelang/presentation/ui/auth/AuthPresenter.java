package id.clorus.bukalelang.presentation.ui.auth;

import android.content.Context;
import android.util.Log;

import id.clorus.bukalelang.data.entity.response.StoreFCMTokenResultData;
import id.clorus.bukalelang.data.entity.response.auth.AuthData;
import id.clorus.bukalelang.data.net.RestService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mirza on 22/05/17.
 */

public class AuthPresenter {

    AuthView view;
    Context context;

    public AuthPresenter(AuthView view,Context context){
        this.context = context;
        this.view = view;
    }

    public void registerRequest(String username, String password, String name, String email){

        RestService.Factory.getInstance().register(name,email,username,password).enqueue(new Callback<AuthData>() {
            @Override
            public void onResponse(Call<AuthData> call, Response<AuthData> response) {
                view.onRegisterCompleted(response.body());
            }

            @Override
            public void onFailure(Call<AuthData> call, Throwable t) {

            }
        });

    }

    public void loginRequest(String username, String password){

        RestService.Factory.getInstance().login(username,password).enqueue(new Callback<AuthData>() {
            @Override
            public void onResponse(Call<AuthData> call, Response<AuthData> response) {
                view.onLoginCompleted(response.body());
            }

            @Override
            public void onFailure(Call<AuthData> call, Throwable t) {

            }
        });

    }

    public void storeFcmToken(int bukalapakId,String token){
        RestService.Factory.getInstance().storeFCMToken(bukalapakId,token).enqueue(new Callback<StoreFCMTokenResultData>() {
            @Override
            public void onResponse(Call<StoreFCMTokenResultData> call, Response<StoreFCMTokenResultData> response) {
                try {
                    Log.d("result",response.body().getMessage());

                } catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<StoreFCMTokenResultData> call, Throwable t) {

            }
        });
    }




}
