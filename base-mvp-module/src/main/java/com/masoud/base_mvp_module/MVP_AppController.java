package com.masoud.base_mvp_module;

import android.content.Context;
import android.view.LayoutInflater;

import androidx.multidex.MultiDexApplication;

import com.masoud.base_mvp_module.interfaces.AppContract;
import com.masoud.base_mvp_module.interfaces.IBaseRepository;
import com.masoud.base_mvp_module.utils.AnimationUtils;
import com.masoud.base_mvp_module.utils.BaseUtils;


public abstract class MVP_AppController extends MultiDexApplication
        implements AppContract, IBaseRepository {

    private int widthScreen = -1, heightScreen = -1;
    private LayoutInflater layoutInflater;
    protected boolean isNetworkAvailable;

    @Override
    public BaseUtils provideUtils() {

        return BaseUtils.getInstance(this);

    }

    @Override
    public AnimationUtils animationUtils() {
        return AnimationUtils.getInstance() ;
    }

    @Override
    public int getWidthDevice() {

        if (widthScreen == -1)
            widthScreen = provideUtils().getScreenSize(true);

        return widthScreen;
    }

    @Override
    public int getHeightDevice() {

        if (heightScreen == -1)
            heightScreen = provideUtils().getScreenSize(false);

        return heightScreen;
    }

    @Override
    public LayoutInflater provideLayoutInflater() {

        if (layoutInflater == null)
            layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        return layoutInflater;
    }

    public void setNetworkAvailable(boolean isNetworkAvailable) {
        this.isNetworkAvailable = isNetworkAvailable;
    }



}
