package transportapp.cris.com.transportapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class ClientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
    }

    public void buyTicket(View view) {
        if (view.getId() == R.id.btn_buyTicket) {
            RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
            if (radioGroup != null) {
                int radioButtonID = radioGroup.getCheckedRadioButtonId();
                if (radioButtonID == R.id.rbtn_express){
                    Toast.makeText(this, "Ati achizitionat un bilet de Express!", Toast.LENGTH_SHORT).show();
                } else if (radioButtonID == R.id.rbtn_tramvai){
                    Toast.makeText(this, "Ati achizitionat un bilet de Tramvai!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
