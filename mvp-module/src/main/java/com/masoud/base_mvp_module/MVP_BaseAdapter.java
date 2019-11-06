package com.masoud.base_mvp_module;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.masoud.base_mvp_module.interfaces.IBaseContract;
import com.masoud.base_mvp_module.interfaces.IBaseRepository;
import com.masoud.base_mvp_module.interfaces.IClickItemRecyclerView;


public abstract class MVP_BaseAdapter extends
        RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements IBaseContract.View {

    protected IBaseContract.View mvp_iBaseView;
    protected IBaseRepository iBaseRepository;
    protected View rowView;

    private IClickItemRecyclerView iClickItemRecyclerView;

    public MVP_BaseAdapter(IBaseContract.View mvp_iBaseView,
                           IBaseRepository iBaseRepository) {
        this.mvp_iBaseView = mvp_iBaseView;
        this.iBaseRepository = iBaseRepository;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {

        initPresenter();

        rowView = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(),
                parent, false);

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (iClickItemRecyclerView != null) {

                    RecyclerView recyclerView = (RecyclerView) parent;
                    int position = recyclerView.getChildAdapterPosition(view);

                    iClickItemRecyclerView.onItemClick(view, position);
                }


            }
        });

        return createViewHolder();
    }

    @Override
    public void showLoading() {

        mvp_iBaseView.showLoading();
    }

    @Override
    public void hideLoading() {

        mvp_iBaseView.hideLoading();
    }

    @Override
    public void onError(int resId) {

        mvp_iBaseView.onError(resId);
    }

    @Override
    public void onError(String message) {

        mvp_iBaseView.onError(message);
    }

    @Override
    public void showMessage(String message) {

        mvp_iBaseView.showMessage(message);
    }

    @Override
    public void showMessage(int resId) {

        mvp_iBaseView.showMessage(resId);
    }

    @Override
    public boolean isNetworkConnected() {
        return mvp_iBaseView.isNetworkConnected();
    }

    @Override
    public void hideKeyboard() {

        mvp_iBaseView.hideKeyboard();
    }

    @Override
    public MVP_BaseActivity getBaseActivity() {
        return mvp_iBaseView.getBaseActivity();
    }

    @Override
    public String getUniqueTag() {
        return mvp_iBaseView.getUniqueTag();
    }

    // // // // // abstract methods

    public abstract int getLayoutId();

    public abstract void initPresenter();

    public abstract BaseViewHolder createViewHolder();

    // // // // // Others

    public MVP_BaseAdapter setClickListener(IClickItemRecyclerView iClickItemRecyclerView) {
        this.iClickItemRecyclerView = iClickItemRecyclerView;
        return this;
    }



    // // // // // Classes

    public static class BaseViewHolder extends RecyclerView.ViewHolder{

        public BaseViewHolder(View itemView) {
            super(itemView);
        }
    }


    @Override
    public LifecycleOwner getLifecycleOwner() {
        return mvp_iBaseView.getLifecycleOwner();
    }
}
