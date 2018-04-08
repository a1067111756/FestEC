package com.ali.latte.ec.database;

import android.content.Context;

import org.greenrobot.greendao.database.Database;

/**
 * Created by 澄鱼 on 2018/4/6.
 */

public class DatabaseManager {

    private DaoSession mDaoSession = null;
    private UserProfileDao mDao = null;

    private DatabaseManager() {}

    private static final class Holder {
        private static final DatabaseManager INSTANCE = new DatabaseManager();
    }

    public DatabaseManager init(Context context) {
        initDao(context);
        return this;
    }

    public static DatabaseManager getInstance () {
        return Holder.INSTANCE;
    }

    private void initDao(Context context) {
        final ReleaseOprenHelper helper = new ReleaseOprenHelper(context, "fast_ec.db");
        final Database db = helper.getWritableDb();
        mDaoSession = new DaoMaster(db).newSession();
        mDao = mDaoSession.getUserProfileDao();
    }

    public final UserProfileDao getmDao() {
        return mDao;
    }
}
