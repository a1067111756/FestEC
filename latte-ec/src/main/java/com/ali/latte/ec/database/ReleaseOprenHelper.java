package com.ali.latte.ec.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.greenrobot.greendao.database.Database;

/**
 * Created by 澄鱼 on 2018/4/6.
 */

public class ReleaseOprenHelper extends DaoMaster.OpenHelper{


    public ReleaseOprenHelper(Context context, String name) {
        super(context, name);
    }

    public ReleaseOprenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onCreate(Database db) {
        super.onCreate(db);
    }
}
