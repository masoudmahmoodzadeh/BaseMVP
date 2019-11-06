package masoud.java.test.mvp.ui.activities.main;

import android.view.View;

import com.masoud.base_mvp_module.MVP_BaseActivity;
import com.masoud.base_mvp_module.utils.AnimationUtils;

import masoud.java.test.mvp.R;
import masoud.java.test.mvp.models.User;

public class MainActivity extends MVP_BaseActivity implements MainContract.View {

    private MainPresenter<MainContract.View> presenter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public AnimationUtils getAnimationTransition() {
        return null;
    }

    @Override
    public void createActionbar() {

    }

    @Override
    public void initPresenter() {

        presenter = new MainPresenter<>(repository());
        presenter.onAttach(this);
    }

    @Override
    public void initView() {

        presenter.request_getUserInfo();
    }

    @Override
    public void setSlideMenu() {

    }

    @Override
    public void onClick() {

    }

    @Override
    public MVP_BaseActivity getBaseActivity() {
        return this;
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();


    }

    @Override
    public void onSuccess(User user) {

        showMessage("Hello " + user.getName());
    }

    @Override
    public View getRootView() {
        return getView();
    }
}
