package base.mvp.core;

import com.masoud.base_mvp_module.MVP_BasePresenter;
import com.masoud.base_mvp_module.interfaces.IBaseContract;
import com.masoud.base_mvp_module.interfaces.IBaseRepository;

public class AppPresenter<V extends IBaseContract.View> extends MVP_BasePresenter<V> {

    private IRepository repository;


    public AppPresenter(IBaseRepository repository) {
        super(repository);

        this.repository = (IRepository) repository;
    }


    public IRepository getRepository() {

        return repository;
    }


}
