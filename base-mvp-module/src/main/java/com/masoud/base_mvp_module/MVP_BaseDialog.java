package com.masoud.base_mvp_module;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;

import com.masoud.base_mvp_module.utils.AnimationUtils;
import com.masoud.base_mvp_module.utils.BaseUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import ir.masoud.base.general.AnimationUtils;
import ir.masoud.base.general.BaseUtils;
import ir.masoud.base.general.data.IBaseRepository;
import ir.masoud.base.general.permissionmadeeasy.intefaces.PermissionListener;
import ir.masoud.base.mvp.interfaces.MVP_IApp;
import ir.masoud.base.mvp.interfaces.MVP_IBaseDialogView;
import ir.masoud.base.mvvm.interfaces.IBackPressed;


public abstract class MVP_BaseDialog extends DialogFragment
        implements MVP_IBaseDialogView, MVP_IApp, IBackPressed {

    protected View rooView;
    protected MVP_BaseActivity baseActivity;
    private int heightScreen = -1;
    private String uniqueTag = "";
    private Unbinder unbinder;

    public MVP_BaseDialog() {

    }

    public MVP_BaseDialog(int heightScreen) {
        this.heightScreen = heightScreen;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initPresenter();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MVP_BaseActivity) {
            MVP_BaseActivity mActivity = (MVP_BaseActivity) context;
            this.baseActivity = mActivity;
            mActivity.onFragmentAttached();
        }
    }


    @Override
    public void onStart() {
        super.onStart();

        provideUtils().normalDialogStyle(getDialog(), getWidthDevice(), heightScreen);


    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rooView = inflater.inflate(getRootView(), container, false);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));



        return rooView;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        unbinder = ButterKnife.bind(this, view);
        setTag(getClass().getSimpleName().concat("_").concat(String.valueOf(System.currentTimeMillis())));


    }

    @Override
    public void onResume() {
        super.onResume();

        initViews();

        buttonPressed();

        onClick();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        unbinder.unbind();
    }

    // // // // // IBaseView

    @Override
    public void showLoading() {
        if (baseActivity != null) {
            baseActivity.showLoading();
        }
    }

    @Override
    public void hideLoading() {
        if (baseActivity != null) {
            baseActivity.hideLoading();
        }
    }

    @Override
    public void onError(String message) {
        if (baseActivity != null) {
            baseActivity.onError(message);
        }
    }

    @Override
    public void onError(@StringRes int resId) {
        if (baseActivity != null) {
            baseActivity.onError(resId);
        }
    }

    @Override
    public void showMessage(String message) {
        if (baseActivity != null) {
            baseActivity.showMessage(message);
        }
    }

    @Override
    public void showMessage(@StringRes int resId) {
        if (baseActivity != null) {
            baseActivity.showMessage(resId);
        }
    }

    @Override
    public void onDetach() {
        baseActivity = null;
        super.onDetach();
    }

    @Override
    public void hideKeyboard() {

        InputMethodManager imm = (InputMethodManager)
                getBaseActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(rooView.getWindowToken(), 0);
    }

    @Override
    public String getUniqueTag() {
        return uniqueTag;
    }

    public MVP_BaseActivity getBaseActivity() {
        return baseActivity;
    }

    @Override
    public void dismissDialog(String tag) {
        dismiss();
        getBaseActivity().onFragmentDetached(tag);
    }

    public void transactionFragment(int viewContainerID, MVP_BaseFragment destinationFragment,
                                    boolean isAddToBackStack) {

        getBaseActivity().transactionFragment(viewContainerID, destinationFragment, isAddToBackStack);
    }


    public void showSnackBar(String message) {

        getBaseActivity().showSnackBar(message);
    }

    public void requestPermissions(String[] permissions, int requestCode, PermissionListener permissionListener) throws Exception {

        getBaseActivity().requestPermissions(permissions, requestCode, permissionListener);

    }


    // // // // // IApp

    @Override
    public boolean isNetworkConnected() {
        if (baseActivity != null) {
            return baseActivity.isNetworkConnected();
        }
        return false;
    }

    @Override
    public BaseUtils provideUtils() {
        return getBaseActivity().provideUtils();
    }


    @Override
    public int getWidthDevice() {
        return getBaseActivity().getWidthDevice();
    }

    @Override
    public int getHeightDevice() {
        return getBaseActivity().getHeightDevice();
    }

    @Override
    public LayoutInflater provideLayoutInflater() {
        return getBaseActivity().provideLayoutInflater();
    }

    @Override
    public void log(String nameClass, String methodName, Exception error) {

        getBaseActivity().log(nameClass, methodName, error);
    }

    @Override
    public AnimationUtils animationUtils() {
        return getBaseActivity().animationUtils();
    }

    // // // // // abstract methods

    public abstract int getRootView();

    public abstract void initPresenter();

    public abstract void onClick();

    public abstract void initViews();

    // // // // // others

    public FragmentManager provideFragmentManager() {

        return getBaseActivity().provideFragmentManager();
    }

    private void buttonPressed() {

        try {

            if (getView() != null) {

                getView().setFocusableInTouchMode(true);
                getView().requestFocus();
                getView().setOnKeyListener(new View.OnKeyListener() {

                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {

                        if (event.getAction() == KeyEvent.ACTION_DOWN)

                            if (keyCode == KeyEvent.KEYCODE_BACK) {

                                backPressed();


                                return true;
                            }

                        return false;
                    }
                });


            }


        } catch (Exception e) {

            log(getClass().getSimpleName(), getBaseActivity().getApplicationController().provideUtils()
                    .getMethodName(), e);
        }
    }

    public IBaseRepository repository() {

        return getBaseActivity().repository();
    }

    public void setTag(String tag) {

        this.uniqueTag = tag;

    }

    @Override
    public LifecycleOwner getLifecycleOwner() {
        return this;
    }
}
