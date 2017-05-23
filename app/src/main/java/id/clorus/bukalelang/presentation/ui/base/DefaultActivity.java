package id.clorus.bukalelang.presentation.ui.base;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import id.clorus.bukalelang.R;


public class DefaultActivity extends AppCompatActivity {

    public static final String ACTIVITY_ANIMATION_TYPE = "activity_animation_type";
    public static final int ACTIVITY_ANIMATION_NEXT = 1;
    public static final int ACTIVITY_ANIMATION_BACK = -1;

    public void changeView(int resourceId, Fragment fragment, Bundle bundle) {
        if (bundle != null) {
            fragment.setArguments(bundle);
        }

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(resourceId, fragment);
        fragmentTransaction.commit();
    }

    public void changeView(int resourceId, Fragment fragment) {
        changeView(resourceId, fragment, null);
    }

    public void changeView(Fragment fragment) {
//        changeView(R.id.container, fragment, null);
    }

    public void openActivity(Class c) {
        openActivity(c, null);
    }

    public void openActivityNewTask(Class c) {
        Intent intent = new Intent(this, c);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK&Intent.FLAG_ACTIVITY_CLEAR_TASK&Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    public void openActivity(Class c, Bundle bundle) {
        Intent intent = new Intent(this, c);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK&Intent.FLAG_ACTIVITY_CLEAR_TASK&Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        overridePendingTransition(R.anim.activity_slide_in, R.anim.activity_slide_out);
    }

    public void openActivityAnimation(Class c, int animationType) {
        openActivityAnimation(c, new Bundle(), animationType);
    }

    public void openActivityAnimation(Class c, Bundle bundle, int animationType) {
        bundle.putInt(ACTIVITY_ANIMATION_TYPE, animationType);
        openActivity(c, bundle);
    }

    public void openActivityForResult(Class c, int requestCode) {
        openActivityForResult(c, requestCode, null);
    }

    public void openActivityForResult(Class c, int requestCode, int animationType) {
        Bundle bundle = new Bundle();
        bundle.putInt(ACTIVITY_ANIMATION_TYPE, animationType);
        openActivityForResult(c, requestCode, bundle);
    }

    public void openActivityForResult(Class c, int requestCode, Bundle bundle, int animationType) {
        bundle.putInt(ACTIVITY_ANIMATION_TYPE, animationType);
        openActivityForResult(c, requestCode, bundle);
    }

    public void openActivityForResult(Class c, int requestCode, Bundle bundle) {
        Intent intent = new Intent(this, c);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    public void changeActivity(Class c) {
        changeActivity(c, null);
    }

    public void changeActivity(Class c, Bundle bundle) {
        //finish();
        openActivity(c, bundle);
    }

    public void changeActivity(Class c, Bundle bundle, int animationType) {
        //finish();
        openActivityAnimation(c, bundle, animationType);
    }

    public void changeActivity(Class c, int animationType) {
        //finish();
        openActivityAnimation(c, new Bundle(), animationType);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_back_slide_in, R.anim.activity_back_slide_out);
    }

    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        int animationType = getIntent().getIntExtra(ACTIVITY_ANIMATION_TYPE, 0);
        Log.i("onCreateAnimation", animationType + "");
        switch (animationType) {
            case ACTIVITY_ANIMATION_NEXT:
                overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
                break;
            case ACTIVITY_ANIMATION_BACK:
                overridePendingTransition(R.anim.activity_back_slide_in, R.anim.activity_back_slide_out);
                break;
        }
    }

    public void setFont(TextView tvObject) {
        Typeface helvetica = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Helvetica.otf");
        tvObject.setTypeface(helvetica);
    }

}
