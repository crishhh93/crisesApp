package transportapp.cris.com.transportapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cristiana on 1/9/2016.
 */
public class UserDataSource {

    private SQLiteDatabase database;
    private DBHelper dbHelper;
    private String[] allCollumns = {DBHelper.COLUMN_EMAIL, DBHelper.COLUMN_NUME, DBHelper.COLUMN_PRENUME, DBHelper.COLUMN_PSW};

    public  UserDataSource(Context context){

        dbHelper = new DBHelper(context);
    }

    public void open() throws SQLException{

        database = dbHelper.getWritableDatabase();
    }

    public void close(){

        dbHelper.close();
    }

    public User createUser(String email, String nume, String prenume, String password){
        ContentValues values = new ContentValues();

        values.put(DBHelper.COLUMN_EMAIL, email);
        values.put(DBHelper.COLUMN_NUME, nume);
        values.put(DBHelper.COLUMN_PRENUME, prenume);
        values.put(DBHelper.COLUMN_PSW, password);

        long insertId = database.insert(DBHelper.TABLE_USERS, null, values);

        Cursor cursor = database.query(DBHelper.TABLE_USERS,
                allCollumns, null, null,
                null, null, null);
        cursor.moveToFirst();
        User newUser = cursorToUser(cursor);
        cursor.close();
        return newUser;

    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<User>();
        Cursor cursor = database.query(DBHelper.TABLE_USERS,
                allCollumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            User user = cursorToUser(cursor);
            users.add(user);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return users;
    }

    public User getUserForEmailAddress(String email) {
        Cursor cursor = database.query(DBHelper.TABLE_USERS,
                allCollumns, null, null, null, null, null);
        cursor.moveToFirst();
        User user = null;
        while (!cursor.isAfterLast() && user == null) {
            User temp = cursorToUser(cursor);
            if (temp.getEmailAdr().compareTo(email) == 0) {
                user = temp;
            }
            cursor.moveToNext();
        }

        return user;
    }

    private User cursorToUser(Cursor cursor) {
        User user = new User();
        user.setEmailAdr(cursor.getString(0));
        user.setFirstName(cursor.getString(1));
        user.setLastName(cursor.getString(2));
        user.setPassword(cursor.getString(3));
        return user;
    }
}
