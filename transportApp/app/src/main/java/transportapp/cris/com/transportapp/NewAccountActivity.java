package transportapp.cris.com.transportapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NewAccountActivity extends AppCompatActivity {

    private static final String TAG = NewAccountActivity.class.getSimpleName();
    private Button btnRegister;
    private EditText enteredLastName, enteredFirstName, enteredEmailAdr, enteredPassword, enteredPasswordCheck;
    private ProgressDialog pDialog;
    private SessionManager session;
    private DatabaseHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);


        enteredLastName = (EditText) findViewById(R.id.et_lastName);
        enteredFirstName = (EditText) findViewById(R.id.et_firstName);
        enteredEmailAdr = (EditText) findViewById(R.id.et_emailAdrReg);
        enteredPassword = (EditText) findViewById(R.id.et_passwordReg);
        enteredPasswordCheck = (EditText) findViewById(R.id.et_passwordRegCheck);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        session = new SessionManager(getApplicationContext());

        db = new DatabaseHandler(getApplicationContext());

        if(session.isLoggedIn()){

            Intent intent = new Intent(NewAccountActivity.this,ClientActivity.class);
            startActivity(intent);
            finish();
        }

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String lastName = enteredLastName.getText().toString().trim();
                String firstName = enteredFirstName.getText().toString().trim();
                String email = enteredEmailAdr.getText().toString().trim();
                String password = enteredPassword.getText().toString().trim();

                if (!lastName.isEmpty() && !firstName.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                    registerUser(lastName, firstName , email, password);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please enter your details!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });
    }

    private void registerUser(final String lastName, final String firstName, final String email, final String password) {

        String tag_string_req = "req_register";

        pDialog.setMessage("Registering ...");
        showDialog();

        StringRequest strReq = new StringRequest(Method.POST,
                Constants.REGISTER_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {

                        String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("userid");
                        String lastname = user.getString("lastname");
                        String firstname = user.getString("firstname");
                        String email = user.getString("email");
                        String created_at = user.getString("createdat");

                        db.addUser(lastname, firstname,email, uid, created_at);

                        Toast.makeText(getApplicationContext(), "User successfully registered. Try login now!", Toast.LENGTH_LONG).show();

                        // Launch login activity
                        Intent intent = new Intent(
                                NewAccountActivity.this,
                                LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("lastName", lastName);
                params.put("firstName", firstName);
                params.put("email", email);
                params.put("password", password);

                return params;
            }

        };

        // Adding request to request queue
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


    public final static boolean isValidEmail (CharSequence target){
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
}
