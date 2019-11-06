package masoud.java.test.mvp.core;

import masoud.java.test.mvp.api.ApiHelper;
import masoud.java.test.mvp.core.shared.SharedHelper;
import masoud.java.test.mvp.db.DbHelper;

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
