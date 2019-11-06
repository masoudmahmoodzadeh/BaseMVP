package base.mvp.core;

import base.mvp.api.ApiHelper;
import base.mvp.db.DbHelper;
import base.mvp.core.shared.SharedHelper;

public class Repository implements IRepository {

    private DbHelper dbHelper;
    private ApiHelper apiHelper;
    private SharedHelper sharedHelper;


    @Override
    public DbHelper db() {
        return dbHelper != null ? dbHelper : new DbHelper();
    }

    @Override
    public ApiHelper api() {
        return apiHelper != null ? apiHelper : new ApiHelper();
    }

    @Override
    public SharedHelper sharedPrefs() {
        return sharedHelper != null ? sharedHelper : new SharedHelper();
    }
}
