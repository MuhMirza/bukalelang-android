package id.clorus.bukalelang.presentation.ui.base;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import id.clorus.bukalelang.R;


public class DefaultFragment extends Fragment {


    public void changeView(Fragment fragment) {
        changeView(fragment, null);
    }

    public void changeView(int resouce, Fragment fragment, Bundle bundle) {
        changeView(resouce, fragment, bundle, true);
    }

    public void changeView(Fragment fragment, Bundle bundle) {
//        changeView(R.id.container, fragment, bundle, true);
    }

    public void changeView(int resourceId, Fragment fragment) {
        changeView(resourceId, fragment, null, true);
    }

    public void changeView(int resourceId, Fragment fragment, boolean anim) {
        changeView(resourceId, fragment, null, anim);
    }

    public void changeView(int resourceId, Fragment fragment, Bundle bundle, boolean anim) {
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        if (anim) {
            getFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(
                            R.anim.slide_in,
                            R.anim.slide_out,
                            R.anim.pop_slide_in,
                            R.anim.pop_slide_out)
                    .replace(resourceId, fragment)
                    .addToBackStack(null)
                    .commit();
        } else {
            getFragmentManager()
                    .beginTransaction()
                    .replace(resourceId, fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }


    public void finishLoading(final View showView, final View hideView) {
        try {
            int mShortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);
            showView.setAlpha(0f);
            showView.setVisibility(View.VISIBLE);

            showView.animate()
                    .alpha(1f)
                    .setDuration(mShortAnimationDuration)
                    .setListener(null);

            hideView.animate()
                    .alpha(0f)
                    .setDuration(mShortAnimationDuration)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            hideView.setVisibility(View.GONE);
                        }
                    });
        } catch (Exception e) {
            Log.e("finish loading", e.getMessage());
        }

    }

    public void startLoading(final View showView, final View hideView) {
        try {
            int mShortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);
            showView.setAlpha(0f);
            showView.setVisibility(View.VISIBLE);

            showView.animate()
                    .alpha(1f)
                    .setDuration(mShortAnimationDuration)
                    .setListener(null);

            hideView.animate()
                    .alpha(0f)
                    .setDuration(mShortAnimationDuration)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            hideView.setVisibility(View.GONE);
                        }
                    });
        } catch (Exception e) {
            Log.e("start loading", e.getMessage());
        }
    }

    public void openActivity(Class c) {
        openActivity(c, null);
    }

    public void openActivity(Class c, Bundle bundle) {
        Intent intent = new Intent(getActivity(), c);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        getActivity().startActivity(intent);
    }

    public void changeActivity(Class c) {
        changeActivity(c, null);
    }

    public void changeActivity(Class c, Bundle bundle) {
        getActivity().finish();
        openActivity(c, bundle);
    }

    public void changeActivity(Class c, Bundle bundle, int animationType) {
        getActivity().finish();
        openActivityAnimation(c, bundle, animationType);
    }

    public void changeActivity(Class c, int animationType) {
        getActivity().finish();
        openActivityAnimation(c, new Bundle(), animationType);
    }

    public void openActivityAnimation(Class c, int animationType) {
        openActivityAnimation(c, new Bundle(), animationType);
    }

    public void openActivityAnimation(Class c, Bundle bundle, int animationType) {
        bundle.putInt(DefaultActivity.ACTIVITY_ANIMATION_TYPE, animationType);
        openActivity(c, bundle);
    }

    public void openActivityForResult(Class c, int requestCode, Bundle bundle) {
        Intent intent = new Intent(getActivity(), c);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    public void openActivityForResult(Class c, int requestCode) {
        openActivityForResult(c, requestCode, null);
    }

    public void openActivityForResult(Class c, int requestCode, int animationType) {
        Bundle bundle = new Bundle();
        bundle.putInt(DefaultActivity.ACTIVITY_ANIMATION_TYPE, animationType);
        openActivityForResult(c, requestCode, bundle);
    }

    public void openActivityForResult(Class c, int requestCode, Bundle bundle, int animationType) {
        bundle.putInt(DefaultActivity.ACTIVITY_ANIMATION_TYPE, animationType);
        openActivityForResult(c, requestCode, bundle);
    }

    public void showToast(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }

    public void hideKeywboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void setFont(TextView tvObject) {
        Typeface helvetica = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Helvetica.otf");
        tvObject.setTypeface(helvetica);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

    }


}
