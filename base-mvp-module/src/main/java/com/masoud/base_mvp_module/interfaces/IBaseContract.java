package com.masoud.base_mvp_module.interfaces;

import androidx.annotation.StringRes;
import androidx.lifecycle.LifecycleOwner;

import com.masoud.base_mvp_module.utils.BaseUtils;
import com.masoud.base_mvp_module.MVP_BaseActivity;

public interface IBaseContract {

    interface View {

        void showLoading();

        void hideLoading();

        void onError(@StringRes int resId);

        void onError(String message);

        void showMessage(String message);

        void showMessage(@StringRes int resId);

        boolean isNetworkConnected();

        void hideKeyboard();

        MVP_BaseActivity getBaseActivity();

        LifecycleOwner getLifecycleOwner();

        String getUniqueTag();
    }

    interface Presenter<V extends IBaseContract.View> {

        void onAttach(V mvpView);

        void onDetach();

        void log(String nameClass, String methodName, Exception error);

        void log(String nameClass, String methodName, String error);

        BaseUtils getUtils();
    }

    interface Dialog extends View {

        void dismissDialog(String tag);

    }


}
