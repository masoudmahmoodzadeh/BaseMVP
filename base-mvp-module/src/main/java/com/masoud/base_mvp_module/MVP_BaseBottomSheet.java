package com.masoud.base_mvp_module;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.masoud.base_mvp_module.interfaces.IAppContract;
import com.masoud.base_mvp_module.interfaces.IBackPressedButton;
import com.masoud.base_mvp_module.interfaces.IBaseContract;
import com.masoud.base_mvp_module.interfaces.IBaseRepository;
import com.masoud.base_mvp_module.permission.enums.SheriffPermission;
import com.masoud.base_mvp_module.permission.interfaces.PermissionListener;
import com.masoud.base_mvp_module.utils.AnimationUtils;
import com.masoud.base_mvp_module.utils.BaseUtils;


public abstract class MVP_BaseBottomSheet extends BottomSheetDialogFragment implements
        IBaseContract.Dialog, IAppContract, IBackPressedButton {

    public static final String TAG = "MVP_BaseBottomSheet";

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
        setTag(TAG.concat("_").concat(String.valueOf(System.currentTimeMillis())));


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
                mBottomSheetBehavior.setPeekHeight(getHeightDevice() / 2);
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

        normalDialogStyle(getBaseActivity().getApplicationController().provideUtils().getScreenSize(true), -1);


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

    public void requestPermissions(int requestCode,
                                   PermissionListener permissionListener,
                                   SheriffPermission... permission) {

        getBaseActivity().requestPermission(requestCode, permissionListener, permission);

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

    public void log(String nameClass, String methodName, Exception error) {

        getBaseActivity().log(nameClass, methodName, error);
    }

    public void log(String nameClass, String methodName, String error) {

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

                                backPressedButton(null);

                                dismissDialog(getUniqueTag());

                                return true;
                            }

                        return false;
                    }
                });


            }


        } catch (Exception e) {

            log(TAG, provideUtils().getMethodName(), e);
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


    public void normalDialogStyle(int width, int height) {

        try {

            Dialog dialog = getDialog();

            if (dialog != null) {

                Window window = dialog.getWindow();

                if (window != null) {

                    WindowManager.LayoutParams wlp = window.getAttributes();
                    wlp.alpha = 0.9f;
                    wlp.gravity = Gravity.BOTTOM;
                    wlp.y = 15;
                    wlp.width = width - 30;
                    if (height != -1)
                        wlp.height = height;

                    window.getAttributes().windowAnimations = R.style.animation_dialog_normal;

                    window.setSoftInputMode(
                            WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

                    window.setAttributes(wlp);
                }
            }
        } catch (Exception e) {

            log(TAG, provideUtils().getMethodName(), e);
        }

    }


}
