package masoud.java.test.mvp.ui.activities.splash;

import com.masoud.base_mvp_module.interfaces.IBaseContract;

public interface SplashContract {

    interface View extends IBaseContract.View{

        void onSuccess();

        android.view.View getRootView();
    }

    interface Presenter <V extends IBaseContract.View> extends IBaseContract.Presenter<V>{

        void request_check_version();

    }
}
