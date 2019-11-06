package com.masoud.base_mvp_module.interfaces;

import android.view.LayoutInflater;

import com.masoud.base_mvp_module.utils.AnimationUtils;
import com.masoud.base_mvp_module.utils.BaseUtils;

public interface IAppContract {

    boolean isNetworkConnected();

    BaseUtils provideUtils();

    int getWidthDevice();

    int getHeightDevice();

    LayoutInflater provideLayoutInflater();

    AnimationUtils animationUtils();

    IBaseRepository provideRepository();
}
