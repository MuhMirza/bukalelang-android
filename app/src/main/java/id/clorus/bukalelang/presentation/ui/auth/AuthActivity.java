package id.clorus.bukalelang.presentation.ui.auth;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.xwray.passwordview.PasswordView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.devland.esperandro.Esperandro;
import id.clorus.bukalelang.R;
import id.clorus.bukalelang.data.entity.response.AuthData;
import id.clorus.bukalelang.presentation.ui.base.DefaultActivity;
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


    }

    private void loginMode(){

        isLogin = true;

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
        appPreference.accessToken(authData.getToken());
        appPreference.bukalapakId(authData.getBukalapakId());
        appPreference.id(authData.getId());
        appPreference.saldo(authData.getSaldo());
        appPreference.email(authData.getEmail());
        appPreference.username(authData.getUsername());
        appPreference.loggedIn(true);

        finish();
    }

    @Override
    public void onLoginCompleted(AuthData authData) {

        appPreference.accessToken(authData.getToken());
        appPreference.bukalapakId(authData.getBukalapakId());
        appPreference.id(authData.getId());
        appPreference.saldo(authData.getSaldo());
        appPreference.email(authData.getEmail());
        appPreference.username(authData.getUsername());
        appPreference.loggedIn(true);

        Log.d("userId",appPreference.id()+"token :"+appPreference.accessToken());

    }



}
