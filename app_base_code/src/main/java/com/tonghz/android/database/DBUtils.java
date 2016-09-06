package com.tonghz.android.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 数据库工具类
 * Created by TongHuiZe on 2016/3/30.
 */
public class DBUtils {

    /**
     * 插入数据
     *
     * @param db             数据库
     * @param table          表名
     * @param nullColumnHack 列名
     * @param values         数据
     */
    public static long insert(SQLiteDatabase db, String table, String nullColumnHack, ContentValues values) {
        if (db == null)
            return -100L;

        long newRowId = -1L;
        db.beginTransaction();
        try {
            newRowId = db.insert(table, nullColumnHack, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
            return newRowId;
        } finally {
            db.endTransaction();
        }
        return newRowId;
    }

    /**
     * 删除数据
     *
     * @param db          数据库
     * @param table       表名
     * @param whereClause WHERE条件
     * @param whereArgs   WHERE条件对应的值
     */
    public static void delete(SQLiteDatabase db, String table, String whereClause, String[] whereArgs) {
        if (db == null)
            return;

        db.beginTransaction();
        try {
            db.delete(table, whereClause, whereArgs);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    /**
     * 更新数据
     *
     * @param db          数据库
     * @param table       表名
     * @param values      新数据
     * @param whereClause WHERE条件
     * @param whereArgs   WHERE条件对应的值
     */
    public static void update(SQLiteDatabase db, String table, ContentValues values, String whereClause,
                              String[] whereArgs) {
        if (db == null)
            return;

        db.beginTransaction();
        try {
            db.update(table, values, whereClause, whereArgs);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    public static Cursor query(SQLiteDatabase db, String table, String[] columns, String selection,
                               String[] selectionArgs, String groupBy, String having, String orderBy) {
        if (db == null) {
            return null;
        }

        Cursor cursor = null;
        db.beginTransaction();

        try {
            cursor = db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }

        return cursor;
    }

    public static Cursor query(SQLiteDatabase db, String table, String[] columns, String selection,
                               String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        if (db == null) {
            return null;
        }

        Cursor cursor = null;
        db.beginTransaction();

        try {
            cursor = db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }

        return cursor;
    }

    public static Cursor rawQuery(SQLiteDatabase db, String sql, String[] selectionArgs) {
        if (db == null)
            return null;

        Cursor cursor = null;
        db.beginTransaction();

        try {
            cursor = db.rawQuery(sql, selectionArgs);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }

        return cursor;
    }

    public static void execSQL(SQLiteDatabase db, String sql) {
        if (db == null)
            return;

        db.beginTransaction();

        try {
            db.execSQL(sql);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    public static void execSQL(SQLiteDatabase db, String sql, Object[] bindArgs) {
        if (db == null)
            return;

        db.beginTransaction();

        try {
            db.execSQL(sql, bindArgs);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

}
