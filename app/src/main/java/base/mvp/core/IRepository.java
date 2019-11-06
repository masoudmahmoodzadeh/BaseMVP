package base.mvp.core;

import com.masoud.base_mvp_module.interfaces.IBaseRepository;

import base.mvp.api.ApiHelper;
import base.mvp.db.DbHelper;
import base.mvp.core.shared.SharedHelper;

public interface IRepository extends IBaseRepository {

    DbHelper db();

    ApiHelper api();

    SharedHelper sharedPrefs();
}
