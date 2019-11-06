package masoud.java.test.mvp.ui.activities.main;

import com.masoud.base_mvp_module.interfaces.IBaseContract;

import masoud.java.test.mvp.models.User;

public interface MainContract {

    interface View extends IBaseContract.View {

        void onSuccess(User user);

        android.view.View getRootView();
    }

    interface Presenter<V extends IBaseContract.View> extends IBaseContract.Presenter<V> {

        void request_getUserInfo();
    }
}
