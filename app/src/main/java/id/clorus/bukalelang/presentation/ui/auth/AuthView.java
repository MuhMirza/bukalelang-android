package id.clorus.bukalelang.presentation.ui.auth;

import id.clorus.bukalelang.data.entity.response.auth.AuthData;

/**
 * Created by mirza on 22/05/17.
 */

public interface AuthView {

    void onRegisterCompleted(AuthData authData);
    void onLoginCompleted(AuthData authData);
}
