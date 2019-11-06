package com.masoud.base_mvp_module;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.masoud.base_mvp_module.utils.AnimationUtils;
import com.masoud.base_mvp_module.utils.BaseUtils;

import ir.masoud.base.general.AnimationUtils;
import ir.masoud.base.general.BaseUtils;
import ir.masoud.base.general.data.IBaseRepository;
import ir.masoud.base.general.permissionmadeeasy.intefaces.PermissionListener;
import ir.masoud.base.mvp.interfaces.MVP_IApp;
import ir.masoud.base.mvp.interfaces.MVP_IBaseDialogView;
import ir.masoud.base.mvvm.interfaces.IBackPressed;

public abstract class MVP_BaseBottomSheet extends BottomSheetDialogFragment implements
        MVP_IBaseDialogView, MVP_IApp, IBackPressed {

    protected MVP_BaseActivity baseActivity;
    private int heightScreen = -1;
    protected View rooView;
    private String uniqueTag = "";

    public MVP_BaseBottomSheet(int heightScreen) {
        this.heightScreen = heightScreen;
    }


    public MVP_BaseBottomSheet() {

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

    public IBaseRepository initDataManager() {

        return baseActivity.repository();
    }


    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        rooView = View.inflate(getActivity(), getRootView(), null);
        dialog.setContentView(rooView);
        setTag(getClass().getSimpleName().concat("_").concat(String.valueOf(System.currentTimeMillis())));


        BottomSheetBehavior mBottomSheetBehavior = BottomSheetBehavior.from(((View) rooView.getParent()));
        if (mBottomSheetBehavior != null) {
            mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                    if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                        dismiss();
                    }

                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                }
            });

            if (heightScreen == -1)
                mBottomSheetBehavior.setPeekHeight(getHeightDevice()/2);
            else
                mBottomSheetBehavior.setPeekHeight(heightScreen);

            rooView.requestLayout();
        }

        initPresenter();

        findView(rooView);

        initViews();


    }

    @Override
    public void onResume() {
        super.onResume();

        getBaseActivity().getApplicationController().provideUtils().normalDialogStyle(getDialog()
                , getBaseActivity().getApplicationController().provideUtils().getScreenSize(true), -1);


        onClick();

        buttonPressed();
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
        if (baseActivity != null) {
            baseActivity.hideKeyboard();
        }
    }

    public MVP_BaseActivity getBaseActivity() {
        return baseActivity;
    }

    @Override
    public String getUniqueTag() {
        return uniqueTag;
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

    public abstract void findView(View view);

    public abstract void onClick();

    public abstract void initViews();

    public abstract void setBackPressed();

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

                                dismissDialog(getUniqueTag());

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
