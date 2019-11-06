package masoud.java.test.mvp.core;

import com.masoud.base_mvp_module.MVP_AppController;
import com.masoud.base_mvp_module.interfaces.IBaseRepository;

public class AppController extends MVP_AppController {

    private Repository repository;

    @Override
    public boolean isNetworkConnected() {
        return false;
    }

    @Override
    public IBaseRepository provideRepository() {
        return repository != null ? repository : new Repository();
    }
}
