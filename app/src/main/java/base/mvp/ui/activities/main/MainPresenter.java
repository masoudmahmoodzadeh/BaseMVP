package base.mvp.ui.activities.main;

import com.masoud.base_mvp_module.interfaces.IBaseRepository;

import base.mvp.core.AppPresenter;
import base.mvp.models.User;

public class MainPresenter<V extends MainContract.View>
        extends AppPresenter<V> implements MainContract.Presenter<V> {


    public MainPresenter(IBaseRepository repository) {
        super(repository);
    }

    @Override
    public void request_getUserInfo() {
        // request to server

        getView().getRootView().postDelayed(new Runnable() {
            @Override
            public void run() {

                getRepository().api().getUserInfo();

                // After the response came back from the server
                getView().onSuccess(new User(1 , "masoud"));
            }
        }, 1000);

    }
}
