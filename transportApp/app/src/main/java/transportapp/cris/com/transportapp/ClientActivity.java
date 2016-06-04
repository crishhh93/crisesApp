package transportapp.cris.com.transportapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gigamole.library.NavigationTabBar;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.ArrayList;
import java.util.Random;

public class ClientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        initToolbar();

        //ToDo: ar trebui sa bag si drawer-ul intr-o functie de init?
        new DrawerBuilder().withActivity(this).build();

        Toolbar toolbar = new Toolbar(getApplicationContext());
        //if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem pitem1 = new PrimaryDrawerItem().withName(R.string.primary_drawer_item1).withIcon(R.drawable.ic_face_black_36dp);
        PrimaryDrawerItem pitem2 = new PrimaryDrawerItem().withName(R.string.primary_drawer_item2).withIcon(R.drawable.ic_help_black_36dp);
        PrimaryDrawerItem sitem1 = new SecondaryDrawerItem().withName(R.string.primary_drawer_item3).withIcon(R.drawable.ic_exit_to_app_black_36dp);

        // Create the AccountHeader
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();
//create the drawer and remember the `Drawer` result object
        Drawer result = new DrawerBuilder()
                .withAccountHeader(headerResult)
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(
                        pitem1,
                        pitem2,
                        new DividerDrawerItem(),
                        sitem1
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D
                        //ToDo: add actvities for each case
                        if (position == 0) {
                            Toast.makeText(ClientActivity.this, "Am apasat primul item!", Toast.LENGTH_SHORT).show();
                            return true;
                        } else if (position == 1) {
                            Toast.makeText(ClientActivity.this, "Am apasat al doilea item!", Toast.LENGTH_SHORT).show();
                            return true;

                        }
                        return true;
                    }
                })
                .build();

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

    //ToDo: ask again here the items are populated
    public void initToolbar() {
        final ViewPager viewPager = (ViewPager) findViewById(R.id.vp_horizontal_ntb);
        {
            viewPager.setAdapter(new PagerAdapter() {
                @Override
                public int getCount() {
                    return 2;
                }

                @Override
                public boolean isViewFromObject(final View view, final Object object) {
                    return view.equals(object);
                }

                @Override
                public void destroyItem(final View container, final int position, final Object object) {
                    ((ViewPager) container).removeView((View) object);
                }

                @Override
                public Object instantiateItem(final ViewGroup container, final int position) {
                    View view = null;
                    if (position == 0) {
                        // Order Layout
                        //TODO replae it with your own layout
                        view = LayoutInflater.from(
                                getBaseContext()).inflate(R.layout.order_layout, null, false);
                    } else if (position == 1) {
                        // History list layout
                        view = LayoutInflater.from(
                                getBaseContext()).inflate(R.layout.item_vp_list, null, false);
                        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(
                                        getBaseContext(), LinearLayoutManager.VERTICAL, false
                                )
                        );
                        // TODO: Pass the history data
                        recyclerView.setAdapter(new RecycleAdapter());
                    }
                    container.addView(view);
                    return view;
                }
            });

        }

        final String[] colors = getResources().getStringArray(R.array.default_preview);

        final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb_horizontal);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                //ToDo: change icon here
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_payment_black_36dp),
                        Color.parseColor(colors[0]))
                        .title("Cumpara bilet")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_list_black_36dp),
                        Color.parseColor(colors[1]))
                        .title("Comenzile mele")
                        .build()
        );

        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(viewPager, 2);

        navigationTabBar.post(new Runnable() {
            @Override
            public void run() {
                final View bgNavigationTabBar = findViewById(R.id.bg_ntb_horizontal);
                bgNavigationTabBar.getLayoutParams().height = (int) navigationTabBar.getBarHeight();
                bgNavigationTabBar.requestLayout();

                final View viewPager = findViewById(R.id.vp_horizontal_ntb);
                ((ViewGroup.MarginLayoutParams) viewPager.getLayoutParams()).topMargin =
                        (int) -navigationTabBar.getBadgeMargin();
                viewPager.requestLayout();
            }
        });

        navigationTabBar.setOnTabBarSelectedIndexListener(new NavigationTabBar.OnTabBarSelectedIndexListener() {
            @Override
            public void onStartTabSelected(final NavigationTabBar.Model model, final int index) {

            }

            @Override
            public void onEndTabSelected(final NavigationTabBar.Model model, final int index) {
                model.hideBadge();
            }
        });

        findViewById(R.id.mask).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                for (int i = 0; i < navigationTabBar.getModels().size(); i++) {
                    final NavigationTabBar.Model model = navigationTabBar.getModels().get(i);
                    navigationTabBar.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            final String title = String.valueOf(new Random().nextInt(15));
                            if (!model.isBadgeShowed()) {
                                model.setBadgeTitle(title);
                                model.showBadge();
                            } else model.updateBadgeTitle(title);
                        }
                    }, i * 100);
                }
            }
        });

    }

    public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
            final View view = LayoutInflater.from(getBaseContext()).inflate(R.layout.item_list, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.txt.setText(String.format("Navigation Item #%d", position));
            // Populate view elements with data
        }

        @Override
        public int getItemCount() {
            // TODO: return the size of the List<History>
            return 20;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            //TODO: Add view elements here
            public TextView txt;

            public ViewHolder(final View itemView) {
                // TODO: define the item_list layout, read layout from view
                super(itemView);
                txt = (TextView) itemView.findViewById(R.id.txt_vp_item_list);
            }
        }
    }
}
