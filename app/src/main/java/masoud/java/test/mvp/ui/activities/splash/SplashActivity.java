package masoud.java.test.mvp.ui.activities.splash;

import android.content.Intent;
import android.view.View;

import com.masoud.base_mvp_module.MVP_BaseActivity;
import com.masoud.base_mvp_module.utils.AnimationUtils;

import masoud.java.test.mvp.R;
import masoud.java.test.mvp.ui.activities.main.MainActivity;

public class SplashActivity extends MVP_BaseActivity implements SplashContract.View {

    private SplashPresenter<SplashContract.View> presenter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
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

        presenter = new SplashPresenter<>(repository());
        presenter.onAttach(this);
    }

    @Override
    public void initView() {

        presenter.request_check_version();

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
    public void onSuccess() {

        Intent intent = new Intent(SplashActivity.this , MainActivity.class);
        startActivity(intent);

        finish();
    }

    @Override
    public View getRootView() {
        return getView();
    }
}
