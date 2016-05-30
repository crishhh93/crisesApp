package transportapp.cris.com.transportapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.gigamole.library.NavigationTabBar;

import java.util.ArrayList;

public class ClientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        initToolbar();
    }

//    public void buyTicket(View view) {
//        if (view.getId() == R.id.btn_buyTicket) {
//            RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
//            if (radioGroup != null) {
//                int radioButtonID = radioGroup.getCheckedRadioButtonId();
//                if (radioButtonID == R.id.rbtn_express){
//                    Toast.makeText(this, "Ati achizitionat un bilet de Express!", Toast.LENGTH_SHORT).show();
//                } else if (radioButtonID == R.id.rbtn_tramvai){
//                    Toast.makeText(this, "Ati achizitionat un bilet de Tramvai!", Toast.LENGTH_SHORT).show();
//                }
//            }
//            radioGroup.setVisibility(View.GONE);
//        }
//    }

    public void initToolbar(){
        final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_face_black_36dp),
                        R.color.colorAccent
                ).title("Heart")
                        .badgeTitle("NTB")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_list_black_36dp),
                        R.color.colorPrimary
                ).title("Cup")
                        .badgeTitle("with")
                        .build()
        );

        navigationTabBar.setTitleMode(NavigationTabBar.TitleMode.ACTIVE);
        navigationTabBar.setBadgeGravity(NavigationTabBar.BadgeGravity.BOTTOM);
        navigationTabBar.setBadgePosition(NavigationTabBar.BadgePosition.CENTER);
        navigationTabBar.setTypeface("fonts/custom_font.ttf");
        navigationTabBar.setIsBadged(true);
        navigationTabBar.setIsTitled(true);
        navigationTabBar.setIsBadgeUseTypeface(true);
        navigationTabBar.setBadgeBgColor(Color.RED);
        navigationTabBar.setBadgeTitleColor(Color.WHITE);

    }
}
