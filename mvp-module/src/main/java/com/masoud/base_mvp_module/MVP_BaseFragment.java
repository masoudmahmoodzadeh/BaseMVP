package com.masoud.base_mvp_module;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;

import com.masoud.base_mvp_module.interfaces.IAppContract;
import com.masoud.base_mvp_module.interfaces.IBaseContract;
import com.masoud.base_mvp_module.interfaces.IBackPressedButton;
import com.masoud.base_mvp_module.interfaces.IBaseRepository;
import com.masoud.base_mvp_module.permission.enums.SheriffPermission;
import com.masoud.base_mvp_module.permission.interfaces.PermissionListener;
import com.masoud.base_mvp_module.utils.AnimationUtils;
import com.masoud.base_mvp_module.utils.BaseUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by Masoud pc on 8/5/2018.
 */
public abstract class MVP_BaseFragment extends Fragment
        implements IBaseContract.View, IBackPressedButton {

    public static final String TAG = "MVP_BaseFragment";

    protected MVP_BaseActivity baseActivity;
    private View rootView;
    public boolean isBackPressed = false;
    private String uniqueTag = "";
    private MVP_BaseFragment topFragment;
    private Unbinder unbinder;


    public MVP_BaseFragment() {

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, rootView);

        return rootView;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initPresenter();

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        onClick();

    }

    @Override
    public void onResume() {
        super.onResume();

        initViews();

        buttonPressed();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        unbinder.unbind();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MVP_BaseActivity) {
            MVP_BaseActivity activity = (MVP_BaseActivity) context;
            this.baseActivity = activity;
            activity.onFragmentAttached();
        }
    }

    // // // // // IBaseView

    @Override
    public void showLoading() {

        if (baseActivity != null)
            baseActivity.showLoading();
    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onError(int resId) {
        if (baseActivity != null) {
            baseActivity.onError(resId);
        }
    }

    @Override
    public void onError(String message) {
        if (baseActivity != null) {
            baseActivity.onError(message);
        }
    }

    @Override
    public void showMessage(String message) {
        if (baseActivity != null) {
            baseActivity.showMessage(message);
        }
    }

    @Override
    public void showMessage(int resId) {
        if (baseActivity != null) {
            baseActivity.showMessage(resId);
        }
    }

    public void showSnackBar(String message) {

        baseActivity.showSnackBar(message);
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


    // // // // // IApp
    @Override
    public boolean isNetworkConnected() {
        if (baseActivity != null) {
            return baseActivity.isNetworkConnected();
        }
        return false;
    }

    public BaseUtils provideUtils() {
        return getBaseActivity().provideUtils();
    }

    public int getWidthDevice() {

        return getBaseActivity().getWidthDevice();
    }
    public int getHeightDevice() {

        return getBaseActivity().getHeightDevice();

    }
    public LayoutInflater provideLayoutInflater() {
        return getBaseActivity().provideLayoutInflater();
    }

    public void log(String nameClass, String methodName, Exception error) {

        getBaseActivity().log(nameClass, methodName, error);
    }

    public AnimationUtils animationUtils() {
        return getBaseActivity().animationUtils();
    }

    // // // // // abstract methods

    public abstract int getLayoutId();

    public abstract void initPresenter();

    public abstract void onClick();

    public abstract void initViews();

    // // // // // others

    public IBaseRepository repository() {

        return baseActivity.repository();
    }

    public FragmentManager provideFragmentManager() {
        return baseActivity.provideFragmentManager();

    }

    private void buttonPressed() {

        try {

            if (getView() != null /*&& iBackPressed != null*/) {

                getView().setFocusableInTouchMode(true);
                getView().requestFocus();
                getView().setOnKeyListener(new View.OnKeyListener() {

                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {

                        if (event.getAction() == KeyEvent.ACTION_DOWN)

                            if (keyCode == KeyEvent.KEYCODE_BACK) {

                                MVP_BaseFragment mvp_baseFragment = manageBackButton();
                                backPressedButton(mvp_baseFragment);

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

    public View getFragmentView() {

        return rootView;
    }

    public interface Callback {

        void onFragmentAttached();

        void onFragmentDetached(String tag);

        void transactionFragment(int viewContainerID, MVP_BaseFragment destinationFragment,
                                 boolean isAddToBackStack);


    }


    public void transactionFragment(int viewContainerID, MVP_BaseFragment destinationFragment,
                                    boolean isAddToBackStack) {

        getBaseActivity().transactionFragment(viewContainerID, destinationFragment, isAddToBackStack);

    }


    public MVP_BaseFragment manageBackButton() {

        if (provideFragmentManager().getBackStackEntryCount() > 1) {

            FragmentManager.BackStackEntry backStackEntry = provideFragmentManager().getBackStackEntryAt(provideFragmentManager().getBackStackEntryCount() - 2);
            topFragment = (MVP_BaseFragment) provideFragmentManager().findFragmentByTag(backStackEntry.getName());
            topFragment.isBackPressed = true;


        }


        return topFragment;
    }

    public MVP_BaseFragment getTopFragment() {
        return topFragment;
    }

    public MVP_BaseFragment getCurrentFragment() {

        MVP_BaseFragment currentFragment = null;

        if (provideFragmentManager().getBackStackEntryCount() > 0) {

            FragmentManager.BackStackEntry backStackEntry = provideFragmentManager().getBackStackEntryAt(provideFragmentManager().getBackStackEntryCount() - 1);
            currentFragment = (MVP_BaseFragment) provideFragmentManager().findFragmentByTag(backStackEntry.getName());

            setUniqueTag(backStackEntry.getName());
        }

        return currentFragment;
    }


    public String getUniqueTag() {

        if (TextUtils.isEmpty(uniqueTag))
            getCurrentFragment();

        return uniqueTag;
    }

    public void setUniqueTag(String tag) {
        this.uniqueTag = tag;
    }


    @Override
    public LifecycleOwner getLifecycleOwner() {
        return this;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermission(String permission) {

        return baseActivity.hasPermission(permission);
    }

    public void requestPermission(int requestCode,
                                  PermissionListener permissionListener,
                                  SheriffPermission... permission) {
        baseActivity.requestPermission(requestCode, permissionListener, permission);
    }

    public void share_intent(String link, String title) {

        baseActivity.share_intent(link, title);
    }
}
