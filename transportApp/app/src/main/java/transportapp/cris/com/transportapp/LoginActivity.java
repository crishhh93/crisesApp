package transportapp.cris.com.transportapp;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.os.AsyncTask;
import android.os.Bundle;

//import org.apache.http.HTTPRESPONSE;
//import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import java.sql.SQLException;

public class LoginActivity extends AppCompatActivity {

    //private UserDataSource userDataSource;
    protected EditText emailPrime;
    protected EditText passwordPrime;

    protected String enteredUsername;
    private final String serverUrl = "http://10.0.2.2/adminratt/index.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView forgotPass = (TextView) findViewById(R.id.tv_Pass);
        forgotPass.setPaintFlags(forgotPass.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        TextView newAcc = (TextView) findViewById(R.id.tv_NewAcc);
        newAcc.setPaintFlags(newAcc.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        emailPrime = (EditText) findViewById(R.id.et_Username);
        passwordPrime = (EditText) findViewById(R.id.et_Password);

        Button loginButton = (Button) findViewById(R.id.btn_login);
        TextView newAccTextView = (TextView) findViewById(R.id.tv_NewAcc);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                enteredUsername = emailPrime.getText().toString();
                String enteredPassword = passwordPrime.getText().toString();

                if (enteredUsername.equals("") || enteredPassword.equals("")) {
                    Toast.makeText(LoginActivity.this, "Campurile trebuie completate", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (enteredUsername.length() <= 1 || enteredPassword.length() <= 1) {
                    Toast.makeText(LoginActivity.this, "Emailul sau parola lunigimi mai mari ca unu", Toast.LENGTH_SHORT).show();
                    return;
                }

                AsyncDataClass asyncReqObj = new AsyncDataClass();
                asyncReqObj.execute(serverUrl, enteredUsername, enteredPassword);
            }
        });

        newAccTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, NewAccountActivity.class);
                startActivity(intent);
            }
        });
    }

    private class AsyncDataClass extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {

            Uri.Builder builder = new Uri.Builder();

            return null;
        }
    }
}

/*    public void forgotPass(View view){
        if(view.getId() == R.id.tv_Pass)
        {
            Intent intent  = new Intent(this, RecoverPasswordActivity.class);
            startActivity(intent);
        }*/

