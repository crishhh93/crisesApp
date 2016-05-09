package transportapp.cris.com.transportapp;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

public class LoginActivity extends AppCompatActivity {

    private UserDataSource userDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView forgotPass = (TextView)findViewById(R.id.tv_Pass);
        forgotPass.setPaintFlags(forgotPass.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        TextView newAcc = (TextView)findViewById(R.id.tv_NewAcc);
        newAcc.setPaintFlags(newAcc.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        userDataSource = new UserDataSource(this);
    }

    public void signInButton(View view){
        if(view.getId() == R.id.btn_login)
        {
            EditText userName, password;

            userName = (EditText)findViewById(R.id.et_Username);
            password = (EditText)findViewById(R.id.et_Password);

            if(userName != null && password != null)
            {
                String email = userName.getText().toString();
                String psw = password.getText().toString();

                User user = userDataSource.getUserForEmailAddress(email);
                if (user != null && user.getPassword().compareTo(psw) == 0){
                    startActivity(new Intent(this, ClientActivity.class));
                }

               else{
                    Toast.makeText(LoginActivity.this, "Wrong information", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void mkNewAccount(View view){
        if(view.getId() == R.id.tv_NewAcc)
        {
            Intent intent  = new Intent(this, NewAccountActivity.class);
            startActivity(intent);
        }
    }

    public void forgotPass(View view){
        if(view.getId() == R.id.tv_Pass)
        {
            Intent intent  = new Intent(this, RecoverPasswordActivity.class);
            startActivity(intent);
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
