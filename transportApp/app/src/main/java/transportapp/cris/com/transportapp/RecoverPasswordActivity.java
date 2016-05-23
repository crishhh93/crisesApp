package transportapp.cris.com.transportapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

public class RecoverPasswordActivity extends AppCompatActivity {

    private UserDataSource userDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_password);

        userDataSource = new UserDataSource(this);
    }

    public void sendRecEmail(View view){
        if(view.getId() == R.id.btn_sendEmail)
        {
            EditText email;

            email = (EditText)findViewById(R.id.et_emailRec);
            if (email != null)
            {
                String emailAddr = email.getText().toString();
                User user = userDataSource.getUserForEmailAddress(emailAddr);
                if (emailAddr.length() >=0 /*&& NewAccountActivity.isValidEmail(emailAddr)*/ && user != null) {
                    try {
                        GMailSender sender = new GMailSender("username@gmail.com", "password");
                        sender.sendMail("PasswordRecovery RATT",
                                "Your password is: " + user.getPassword(),
                                "user@gmail.com",
                                user.getEmailAdr());
                    } catch (Exception e) {
                        Log.e("SendMail", e.getMessage(), e);
                    }
                }
                else {
                    Toast.makeText(this, "Invalid email address!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            userDataSource.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        userDataSource.close();
    }
}
