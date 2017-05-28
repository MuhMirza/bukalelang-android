package id.clorus.bukalelang.presentation.utils;

import de.devland.esperandro.annotations.Default;
import de.devland.esperandro.annotations.SharedPreferences;

@SharedPreferences
public interface AppPreference {

    boolean loggedIn();
    boolean loggedIn(boolean loggedIn);
    @Default(ofBoolean = true)
    boolean firstTime();
    boolean firstTime(boolean firstTime);
    boolean isHaveAddress();
    boolean isHaveAddress(boolean isHaveAddress);
    String accessToken();
    void accessToken(String accessToken);
    String basicToken();
    void basicToken(String basicToken);
    String roles();
    void roles(String roles);
    String username();
    void username(String username);
    String name();
    void name(String name);
    int saldo();
    void saldo(int saldo);
    String password();
    void password(String password);
    float progress();
    String email();
    void email(String email);

    String photoUrl();
    void photoUrl(String avatarUrl);
    int id();
    void id(int id);
    int bukalapakId();
    void bukalapakId(int bukalapakId);
    @Default(ofString = "-6.2297465")
    String latitude();
    void latitude(String latitude);
    @Default(ofString = "106.829518")
    String longitude();
    void longitude(String longitude);


}
