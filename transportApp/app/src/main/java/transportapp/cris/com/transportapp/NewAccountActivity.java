package transportapp.cris.com.transportapp;

import android.app.ProgressDialog;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import java.io.IOException;
import java.io.IOException.*;

public class NewAccountActivity extends AppCompatActivity {

    //private UserDataSource userDataSource;
    protected EditText lastName, firstName, emailAdr, password, passwordCheck;

    protected  String enteredLastName, enteredFirstName, enteredEmailAdr, enteredPassword, enteredPasswordCheck;
    private final String serverUrl = "http://10.0.2.2/adminratt/index.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);

        lastName = (EditText)findViewById(R.id.et_lastName);
        firstName = (EditText)findViewById(R.id.et_firstName);
        emailAdr = (EditText)findViewById(R.id.et_emailAdrReg);
        password = (EditText)findViewById(R.id.et_passwordReg);
        passwordCheck = (EditText)findViewById(R.id.et_passwordRegCheck);

        Button registerButton = (Button)findViewById(R.id.btn_register);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                enteredLastName = lastName.getText().toString();
                enteredFirstName = firstName.getText().toString();
                enteredEmailAdr = emailAdr.getText().toString();
                enteredPassword = password.getText().toString();
                enteredPasswordCheck = passwordCheck.getText().toString();
            }
        });
    }
}

/*    public  void registerButton(View view){
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
    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }*/
