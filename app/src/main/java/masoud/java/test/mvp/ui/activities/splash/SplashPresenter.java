package masoud.java.test.mvp.ui.activities.splash;

import com.masoud.base_mvp_module.interfaces.IBaseRepository;

import masoud.java.test.mvp.core.AppPresenter;

public class SplashPresenter<V extends SplashContract.View> extends AppPresenter<V>
        implements SplashContract.Presenter<V> {

    public SplashPresenter(IBaseRepository repository) {
        super(repository);
    }

    @Override
    public void request_check_version() {

        getView().getRootView().postDelayed(new Runnable() {
            @Override
            public void run() {

                getRepository().api().check_version();

                // After the response came back from the server
                getView().onSuccess();
            }
        }, 3000);


    }
}
