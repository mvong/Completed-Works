package itp341.vong.mark.finalproject;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import itp341.vong.mark.finalproject.model.User;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        FragmentManager fm = getSupportFragmentManager();
        Fragment f = fm.findFragmentById(R.id.add_fragment_container);

        if(f == null) {
            f = AddFragment.newInstance();
        }

        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.add_fragment_container, f);
        ft.commit();
    }
}
