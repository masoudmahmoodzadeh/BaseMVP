package masoud.java.test.mvp.core;

import com.masoud.base_mvp_module.interfaces.IBaseRepository;

import masoud.java.test.mvp.api.ApiHelper;
import masoud.java.test.mvp.db.DbHelper;
import masoud.java.test.mvp.core.shared.SharedHelper;

public interface IRepository extends IBaseRepository {

    DbHelper db();

    ApiHelper api();

    SharedHelper sharedPrefs();
}
