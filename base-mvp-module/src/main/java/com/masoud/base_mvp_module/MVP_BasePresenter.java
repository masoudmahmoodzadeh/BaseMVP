/*
 * Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://mindorks.com/license/apache-v2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.masoud.base_mvp_module;


import com.masoud.base_mvp_module.interfaces.IBaseContract;
import com.masoud.base_mvp_module.interfaces.IBaseRepository;
import com.masoud.base_mvp_module.utils.BaseUtils;


public class MVP_BasePresenter<V extends IBaseContract.View> implements IBaseContract.Presenter<V> {


    private final IBaseRepository appDataManager;
    private V mMvpView;

    public MVP_BasePresenter(IBaseRepository appDataManager) {
        this.appDataManager = appDataManager;
    }

    @Override
    public void onAttach(V mvpView) {
        mMvpView = mvpView;
    }

    @Override
    public void onDetach() {
        mMvpView = null;
    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }

    public V getMvpView() {
        return mMvpView;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    public IBaseRepository getRepository() {
        return appDataManager;
    }


    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.onAttach(IBaseView) before" +
                    " requesting data to the Presenter");
        }
    }


    @Override
    public void log(String nameClass, String methodName, Exception error) {

        if (mMvpView != null && mMvpView.getBaseActivity() != null)
            getUtils().log(nameClass, methodName, error);
    }

    @Override
    public BaseUtils getUtils() {
        return mMvpView.getBaseActivity().provideUtils();
    }


}
