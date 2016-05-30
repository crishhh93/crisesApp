package transportapp.cris.com.transportapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.location.GpsStatus;
import android.net.sip.SipAudioCall;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.sql.ClientInfoStatus;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;

//import android.os.AsyncTask;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = NewAccountActivity.class.getSimpleName();
    private Button loginButton;
    private TextView newAccTextView;
    private EditText enteredEmail;
    private EditText enteredPassword;
    private ProgressDialog pDialog;
    private SessionManager session;
    public DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView forgotPass = (TextView) findViewById(R.id.tv_Pass);
        forgotPass.setPaintFlags(forgotPass.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        TextView newAcc = (TextView) findViewById(R.id.tv_NewAcc);
        newAcc.setPaintFlags(newAcc.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        enteredEmail = (EditText) findViewById(R.id.et_Username);
        enteredPassword = (EditText) findViewById(R.id.et_Password);
        loginButton = (Button) findViewById(R.id.btn_login);
        newAccTextView = (TextView) findViewById(R.id.tv_NewAcc);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        db = new DatabaseHandler(getApplicationContext());

        session = new SessionManager(getApplicationContext());

        if (session.isLoggedIn()) {

            Intent intent = new Intent(LoginActivity.this, NewAccountActivity.class);
            startActivity(intent);
            finish();
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = enteredEmail.getText().toString();
                String password = enteredPassword.getText().toString();

                if (!email.isEmpty() && !password.isEmpty()) {
                    checkLogin(email, password);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Datele nu sunt valide!", Toast.LENGTH_LONG).show();
                }
   /*             if (enteredUsername.length() <= 1 || enteredPassword.length() <= 1) {
                    Toast.makeText(LoginActivity.this, "Emailul sau parola lunigimi mai mari ca unu", Toast.LENGTH_SHORT).show();
                    return;
                }*/

               /* AsyncDataClass asyncReqObj = new AsyncDataClass();
                asyncReqObj.execute(serverUrl, enteredUsername, enteredPassword);*/
            }
        });

        newAccTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, NewAccountActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
        private void checkLogin(final String email,final String password){

            String tag_string_req = "req_login";

            StringRequest strReq = new StringRequest(Request.Method.POST, Constants.LOGIN_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "Login Response: " + response.toString());
                    hideDialog();

                    try {
                        JSONObject jObj = new JSONObject(response);
                        boolean error = jObj.getBoolean("error");

                        if(!error){
                            session.setLogin(true);

                            String uid = jObj.getString("uid");

                            JSONObject user = jObj.getJSONObject("userid");
                            String lastname = user.getString("lastname");
                            String firstname = user.getString("firstname");
                            String email = user.getString("email");
                            String created_at = user.getString("createdat");

                            db.addUser(lastname, firstname,email, uid, created_at);

                            Intent intent = new Intent(LoginActivity.this, ClientActivity.class);
                            startActivity(intent);
                            finish();

                        }
                        else{

                            String errorMsg = jObj.getString("error_msg");
                            Toast.makeText(getApplicationContext(),errorMsg, Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (JSONException e){
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Json error" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

            }, new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "Login Error: " + error.getMessage());
                    Toast.makeText(getApplicationContext(),
                            error.getMessage(), Toast.LENGTH_LONG).show();
                    hideDialog();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                // Posting parameters to login url
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("email", email);
                    params.put("password", password);
                    return params;
            }
            };
            AppController.getmInstance().addToRequestQueue(strReq, tag_string_req);
        }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
/*    public void forgotPass(View view){
        if(view.getId() == R.id.tv_Pass)
        {
            Intent intent  = new Intent(this, RecoverPasswordActivity.class);
            startActivity(intent);
        }*/

