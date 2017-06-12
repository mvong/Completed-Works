package itp341.vong.mark.finalproject;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class ListActivity extends AppCompatActivity {


    private ActionBarDrawerToggle toggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        FragmentManager fm = getSupportFragmentManager();
        Fragment f = fm.findFragmentById(R.id.list_fragment_container);

        Toolbar myBar = (Toolbar)findViewById(R.id.toolBar);
        setSupportActionBar(myBar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView naviTextView = (TextView)findViewById(R.id.naviTextView);
        naviTextView.setText(LoginFragment.currUser.getUsername());

        DrawerLayout dLayout = (DrawerLayout)findViewById(R.id.activity_list);
        ListView naviList = (ListView)findViewById(R.id.left_drawer);
        final String[] naviString = getResources().getStringArray(R.array.navi_array);
        naviList.setAdapter(new ArrayAdapter<String>(this, R.layout.navilayout, naviString) {
            @NonNull
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if(convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.navilayout, null);
                }
                TextView naviTextView = (TextView)convertView.findViewById(R.id.naviTextView);
                naviTextView.setText(naviString[position]);
                return convertView;
            }
        });
        naviList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position) {
                    case 0:
                        LoginFragment.userAuth.signOut();
                        Intent i = new Intent(ListActivity.this, MainActivity.class);
                        startActivity(i);
                        break;
                    case 1:
                        // Display
                        break;
                }
            }
        });

        toggle = new ActionBarDrawerToggle(this, dLayout, 0, 0) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                Log.d("CLICKED", "CLICKEd");
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };




        dLayout.addDrawerListener(toggle);

        if(f == null) {
            f = ListFragment.newInstance();
        }

        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.list_fragment_container, f);
        ft.commit();
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(toggle.onOptionsItemSelected(item)) {
            return true;
        }
        if(item.getItemId() == R.id.menu_item_add) {
            Intent i = new Intent(this, AddActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
