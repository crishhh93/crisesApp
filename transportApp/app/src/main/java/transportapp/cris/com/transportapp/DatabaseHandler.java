package transportapp.cris.com.transportapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by Cristiana on 5/30/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String TAG = SQLiteOpenHelper.class.getSimpleName();

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "adminratt";

    private static final String TABLE_USER = "user";
    private static final String TABLE_TICKET = "ticket";
    private static final String TABLE_ACQUISITIONS = "acquisitions";

    private static final String KEY_USERID = "UserID";
    private static final String KEY_LASTNAME = "LastName";
    private static final String KEY_FIRSTNAME = "FirstName";
    private static final String KEY_EMAIL = "Email";
    //???
    private static final String KEY_UID = "UniqueID";
    private static final String KEY_CREATED_AT = "CreatedAt";

    public DatabaseHandler (Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE" + TABLE_USER + "("
                + KEY_USERID + " INTEGER PRIMARY KEY,"  +KEY_LASTNAME + " TEXT,"
                + KEY_FIRSTNAME + " TEXT," + KEY_EMAIL + " TEXT UNIQUE"
                + KEY_UID + " TEXT," + KEY_CREATED_AT + " TEXT" + ")";
        db.execSQL(CREATE_LOGIN_TABLE);

        Log.d(TAG, "Database tables created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        onCreate(db);
    }

    public  void addUser(String lastname, String firstname, String email, String uid, String createdAt){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_LASTNAME, lastname);
        values.put(KEY_FIRSTNAME, firstname);
        values.put(KEY_EMAIL, email);
        values.put(KEY_UID, uid);
        values.put(KEY_CREATED_AT, createdAt);

        long id = db.insert(TABLE_USER, null, values);
        db.close();

        Log.d(TAG, "New user inserted into sqlite" + id);

    }

    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            user.put("lastname", cursor.getString(1));
            user.put("firstname", cursor.getString(2));
            user.put("email", cursor.getString(3));
            user.put("uid", cursor.getString(4));
            user.put("created_at", cursor.getString(5));
        }
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

        return user;
    }

    public void delteUsers(){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_USER, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from Sqlite");
    }
}
