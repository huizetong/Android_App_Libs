package com.tonghz.android.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tonghz.android.utils.LogUtils;


/**
 * SQLiteOpenHelper子类
 * Created by TongHuiZe on 2016/3/30.
 */
public class SQLiteDBHelper extends SQLiteOpenHelper {
    private static final String TAG = SQLiteDBHelper.class.getSimpleName();
    /**
     * 创建表SQL语句数组
     */
    private String[] mCreateTableSQLs;

    /**
     * 更新数据库SQL语句数组
     */
    private String[] mUpgradeDBSQLs;

    public SQLiteDBHelper(Context context, String name, int version, String[] createTableSQLs, String[] upgradeDBSQLs) {
        super(context, name, null, version);
        this.mCreateTableSQLs = createTableSQLs;
        this.mUpgradeDBSQLs = upgradeDBSQLs;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        LogUtils.d(TAG, "onCreate(SQLiteDatabase db) is execute!");
        /*
         * 创建表
         */
        createTables(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        LogUtils.i(TAG, "onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) is execute!");
        if (mUpgradeDBSQLs != null && mUpgradeDBSQLs.length > 0) {
            for (String sql : mUpgradeDBSQLs) {
                db.execSQL(sql);
            }
        }
//        db.execSQL("DROP TABLE IF EXISTS " + TableDrugRemind.class.getSimpleName());
//        onCreate(db);
    }

    /**
     * 创建表
     *
     * @param db SQLiteDatabase对象
     */
    private void createTables(SQLiteDatabase db) {
        if (mCreateTableSQLs == null || mCreateTableSQLs.length == 0)
            return;

        if (db != null) {
            db.beginTransaction();

            try {
                // 循环执行数组中的SQL语句
                for (String sql : mCreateTableSQLs) {
                    db.execSQL(sql);
                }
                db.setTransactionSuccessful();
            } catch (Exception e) {
                e.printStackTrace();
                LogUtils.e(TAG, "createTables(SQLiteDatabase db) is exception!");
            } finally {
                db.endTransaction();
            }
        }

    }
}
