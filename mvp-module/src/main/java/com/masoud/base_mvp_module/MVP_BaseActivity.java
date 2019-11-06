package com.masoud.base_mvp_module;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;

import com.google.android.material.snackbar.Snackbar;
import com.masoud.base_mvp_module.interfaces.IBaseContract;
import com.masoud.base_mvp_module.interfaces.IBaseRepository;
import com.masoud.base_mvp_module.permission.enums.SheriffPermission;
import com.masoud.base_mvp_module.permission.helper.Sheriff;
import com.masoud.base_mvp_module.permission.interfaces.PermissionListener;
import com.masoud.base_mvp_module.utils.AnimationUtils;
import com.masoud.base_mvp_module.utils.BaseUtils;
import com.masoud.base_mvp_module.utils.MVP_FragmentUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class MVP_BaseActivity extends AppCompatActivity
        implements IBaseContract.View, MVP_BaseFragment.Callback {

    private FragmentManager fragmentManager;
    private String uniqueTag = "";
    private Unbinder unbinder;
    private Sheriff sheriffPermission;


    @Override
    public void onFragmentAttached() {

    }

    @Override
    public void onFragmentDetached(String tag) {

    }

    private void setConfiguration() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPresenter();

        setConfiguration();

        fragmentManager = getSupportFragmentManager();

        setAnimationTransition();

        setContentView(getLayoutId());

        unbinder = ButterKnife.bind(this);

        setTag(getClass().getSimpleName().concat("_").concat(String.valueOf(System.currentTimeMillis())));

        createActionbar();

        initView();

    }

    @Override
    protected void onResume() {
        super.onResume();

        onClick();

        setSlideMenu();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unbinder.unbind();

    }


    // // // // // IBaseView
    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onError(String message) {
        if (message != null) {
            showSnackBar(message);
        }
    }

    @Override
    public void onError(@StringRes int resId) {
        onError(getString(resId));
    }


    @Override
    public void showMessage(String message) {
        if (message != null) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showMessage(int resId) {

        showMessage(getString(resId));
    }


    @Override
    public void hideKeyboard() {
        try {

            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }

        } catch (Exception e) {

            log(getClass().getSimpleName(), provideUtils().getMethodName(), e);
        }

    }

    @Override
    public String getUniqueTag() {
        return uniqueTag;
    }


    @Override
    public void transactionFragment(int viewContainerID, MVP_BaseFragment destinationFragment,
                                    boolean isAddToBackStack) {

        new MVP_FragmentUtils.Builder()
                .setDestinationFragment(destinationFragment)
                .setFragmentManager(provideFragmentManager())
                .setAddToBackStack(isAddToBackStack)
                .setContainerViewID(viewContainerID)
                .create()
                .transaction();

    }


    // // // // // IApp
    @Override
    public boolean isNetworkConnected() {
        return getApplicationController().isNetworkConnected();
    }

    public BaseUtils provideUtils() {
        return getApplicationController().provideUtils();
    }

    public int getWidthDevice() {
        return getApplicationController().getWidthDevice();
    }

    public int getHeightDevice() {
        return getApplicationController().getHeightDevice();
    }

    public LayoutInflater provideLayoutInflater() {
        return getApplicationController().provideLayoutInflater();
    }

    public void log(String nameClass, String methodName, Exception error) {
        provideUtils().log(nameClass, methodName, error);
    }

    public void log(String nameClass, String methodName, String error) {
        provideUtils().log(nameClass, methodName, error);
    }

    public AnimationUtils animationUtils() {
        return getApplicationController().animationUtils();
    }


    // abstract methods

    public abstract @LayoutRes
    int getLayoutId();

    public abstract AnimationUtils getAnimationTransition();

    public abstract void createActionbar();

    public abstract void initPresenter();

    public abstract void initView();

    public abstract void setSlideMenu();

    public abstract void onClick();

    // others

    public MVP_AppController getApplicationController() {
        return (MVP_AppController) getApplication();
    }


    public IBaseRepository repository() {

        return getApplicationController().provideRepository();
    }

    public View getView() {

        return ((ViewGroup) this
                .findViewById(android.R.id.content)).getChildAt(0);
    }

    public FragmentManager provideFragmentManager() {
        return fragmentManager;
    }

    private void setAnimationTransition() {

        AnimationUtils MVVMAnimationUtils = getAnimationTransition();

        if (MVVMAnimationUtils != null)
            overridePendingTransition(MVVMAnimationUtils.getEnterAnimation(), MVVMAnimationUtils.getExitAnimation());

    }

    public void showSnackBar(String message) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                message, Snackbar.LENGTH_SHORT);
        View sbView = snackbar.getView();
        TextView textView = sbView
                .findViewById(R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        snackbar.show();
    }


    public void setTag(String tag) {

        this.uniqueTag = tag;

    }


    @Override
    public LifecycleOwner getLifecycleOwner() {
        return this;
    }

    public void share_intent(String link, String title) {

        provideUtils().share_intent(this, link, title);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermission(String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    public void requestPermission(int requestCode,
                                  PermissionListener permissionListener,
                                  SheriffPermission... permission) {

        sheriffPermission = Sheriff.Builder()
                .with(this)
                .requestCode(requestCode)
                .setPermissionResultCallback(permissionListener)
                .askFor(permission)
                .rationalMessage("These Permissions are required to work app with all functions.")
                .build();
        sheriffPermission.requestPermissions();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        sheriffPermission.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


}
