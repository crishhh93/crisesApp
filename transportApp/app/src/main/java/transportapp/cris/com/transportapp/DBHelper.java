package transportapp.cris.com.transportapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Cristiana on 1/9/2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String TABLE_USERS = "users";
    public static final String COLUMN_NUME = "nume";
    public static final String COLUMN_PRENUME = "prenume";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PSW= "psw";
    private static final String DATABASE_NAME = "user.db";
    private static final int DATABASE_VERSION = 1;
    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table " + TABLE_USERS + "(" + COLUMN_EMAIL + " text primary key not null, " + COLUMN_NUME + " text not null, " + COLUMN_PRENUME + " text not null, " + COLUMN_PSW + " text not null);";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DBHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }
}