package transportapp.cris.com.transportapp;

import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

public class NewAccountActivity extends AppCompatActivity {

    private UserDataSource userDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);

        userDataSource = new UserDataSource(this);
    }

    public  void registerButton(View view){
        if(view.getId() == R.id.btn_register)
        {
            EditText lastName, firstName, emailAdr, password, passwordCheck;

            lastName = (EditText)findViewById(R.id.et_lastName);
            firstName = (EditText)findViewById(R.id.et_firstName);
            emailAdr = (EditText)findViewById(R.id.et_emailAdrReg);
            password = (EditText)findViewById(R.id.et_passwordReg);
            passwordCheck = (EditText)findViewById(R.id.et_passwordRegCheck);

            if(lastName != null && firstName != null && emailAdr != null && password != null && passwordCheck != null)
            {
                String lastNameStr = lastName.getText().toString();
                String firstNameStr = firstName.getText().toString();
                String emailAdrStr = emailAdr.getText().toString();
                String passwordStr = password.getText().toString();
                String passwordCheckStr = passwordCheck.getText().toString();

                if( lastNameStr.length() > 0 && firstNameStr.length() > 0 && emailAdrStr.length() > 0 && passwordStr.length() > 0 && isValidEmail(emailAdrStr)){

                    if (passwordStr.equals(passwordCheckStr)) {

                        User user = userDataSource.createUser(emailAdrStr, firstNameStr, lastNameStr, passwordStr);

                        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();

                        finish();
                    }

                    else{
                        Toast.makeText(this, "Parolele nu se potrivesc!", Toast.LENGTH_SHORT).show();
                    }
                }
                else{

                    Toast.makeText(this, "Incorrect parameters", Toast.LENGTH_SHORT).show();
                }

            }

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            userDataSource.open();

            List<User> users = userDataSource.getAllUsers();

            for (Iterator it = users.iterator(); it.hasNext(); ) {
                User user1 = (User)it.next();
                Log.i("RATT", user1.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        userDataSource.close();
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
}
