package eu.pellerito.popularmoviesproject2.data;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

@SuppressWarnings("WeakerAccess")
public class Db extends SQLiteOpenHelper {

    private static final String DB_NAME = "popularmoviesproject2.db";
    private static final int DB_VERSION = 1;

    public Db(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Statements.FAVORITES_STATEMENT);

        // Add Schema DB
        db.execSQL(Statements.FAVORITES_STATEMENT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        switch (oldVersion) {

            case 1:// CURRENT VERSION NOTHING .....

                break;

            case 2:
                // DROP TABLE

                db.execSQL("DROP TABLE IF EXISTS " + Statements.FAVORITES_STATEMENT);

                // call on create db..... start initialize
                onCreate(db);

                break;

            default:
                throw new IllegalStateException(
                        "onUpgrade()  unknown oldVersion " + oldVersion);
        }

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onConfigure(SQLiteDatabase db) {
        // Enable DB to use Foreign Key
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }


}