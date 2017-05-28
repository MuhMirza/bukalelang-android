package id.clorus.bukalelang.presentation.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xwray.passwordview.PasswordView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.devland.esperandro.Esperandro;
import id.clorus.bukalelang.R;
import id.clorus.bukalelang.data.entity.response.auth.AuthData;
import id.clorus.bukalelang.presentation.ui.base.DefaultActivity;
import id.clorus.bukalelang.presentation.ui.home.HomeActivity;
import id.clorus.bukalelang.presentation.utils.AppPreference;

/**
 * Created by mirza on 22/05/17.
 */

public class AuthActivity extends DefaultActivity implements AuthView{

    @BindView(R.id.input_name)
    EditText inputName;

    @BindView(R.id.input_email)
    EditText inputEmail;

    @BindView(R.id.input_password)
    PasswordView inputPass;

    @BindView(R.id.input_username)
    EditText inputUsername;

    @BindView(R.id.btn_submit)
    Button btnSubmit;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.auth_switch)
    TextView Tvswitch;

    private boolean isLogin = true;

    AppPreference appPreference;

    AuthPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        appPreference = Esperandro.getPreferences(AppPreference.class, this);
        ButterKnife.bind(this);

        presenter = new AuthPresenter(this,this);

        if (isLogin){
            loginMode();
        } else registerMode();

//        login();

    }

    private void loginMode(){

        isLogin = true;

        Tvswitch.setText("Sudah punya akun? login");

        title.setText("Login Akun");
        inputName.setVisibility(View.GONE);
        inputEmail.setVisibility(View.GONE);
        btnSubmit.setText("LOGIN");

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.loginRequest(inputUsername.getText().toString(),inputPass.getText().toString());
            }
        });

    }

    private void registerMode(){

        isLogin = false;
        title.setText("Daftar Akun");
        Tvswitch.setText("Belum punya akun? Daftar");

        inputName.setVisibility(View.VISIBLE);
        inputEmail.setVisibility(View.VISIBLE);
        btnSubmit.setText("DAFTAR");

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.registerRequest(inputUsername.getText().toString(),inputPass.getText().toString(),
                        inputName.getText().toString(),inputEmail.getText().toString());
            }
        });
    }


    @OnClick(R.id.auth_switch)
    public void swithAuthMode(){

        if (isLogin){
            registerMode();
        } else loginMode();

    }


    @Override
    public void onRegisterCompleted(AuthData authData) {

        if (authData.getStatus().equals("OK")){
            appPreference.accessToken(authData.getToken());
            appPreference.bukalapakId(authData.getBukalapakId());
            appPreference.id(authData.getId());
            appPreference.saldo(authData.getSaldo());
            appPreference.email(authData.getEmail());
            appPreference.username(authData.getUsername());
            appPreference.loggedIn(true);
            appPreference.basicToken(authData.getBasicToken());
            appPreference.photoUrl(authData.getAvatarUrl());

            if (authData.getUserAddresses().size() >0){
                appPreference.isHaveAddress(true);
            } else appPreference.isHaveAddress(false);

            Intent intent = new Intent(AuthActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();

        }

        showToast(authData.getMessage());

        Log.d("userId",appPreference.id()+"token :"+appPreference.accessToken()+"basicToken : "+appPreference.basicToken());
    }

    @Override
    public void onLoginCompleted(AuthData authData) {

        if (authData.getStatus().equals("OK")){
            appPreference.accessToken(authData.getToken());
            appPreference.bukalapakId(authData.getBukalapakId());
            appPreference.id(authData.getId());
            appPreference.saldo(authData.getSaldo());
            appPreference.email(authData.getEmail());
            appPreference.username(authData.getUsername());
            appPreference.loggedIn(true);
            appPreference.basicToken(authData.getBasicToken());
            appPreference.photoUrl(authData.getAvatarUrl());
            if (authData.getUserAddresses().size() >0){
                appPreference.isHaveAddress(true);
            } else appPreference.isHaveAddress(false);

            Intent intent = new Intent(AuthActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();

        }

        showToast(authData.getMessage());

        Log.d("userId",appPreference.id()+"token :"+appPreference.accessToken()+"basicToken : "+appPreference.basicToken());

    }

    public void login(){
        appPreference.accessToken("QBDCP5bP5wKTdRrJePJ");
        appPreference.bukalapakId(29971746);
        appPreference.id(4);
        appPreference.basicToken("Basic Mjk5NzE3NDY6UUJEQ1A1YlA1d0tUZFJySmVQSg==");
        appPreference.loggedIn(true);

        Log.d("token",appPreference.accessToken());
        Log.d("blId",String.valueOf(appPreference.bukalapakId()));
        Log.d("id",String.valueOf(appPreference.id()));

    }

    @OnClick(R.id.btn_back)
    @Override
    public void onBackPressed() {
//        super.onBackPressed();

        Intent intent = new Intent(AuthActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }



}
