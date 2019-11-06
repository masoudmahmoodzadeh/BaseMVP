package base.mvp;

import com.masoud.base_mvp_module.MVP_BaseActivity;
import com.masoud.base_mvp_module.utils.AnimationUtils;

public class MainActivity extends MVP_BaseActivity {

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

    }

    @Override
    public void initView() {

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
}
