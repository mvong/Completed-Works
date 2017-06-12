package itp341.vong.mark.finalproject;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import itp341.vong.mark.finalproject.model.Post;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        FragmentManager fm = getSupportFragmentManager();
        Fragment f = fm.findFragmentById(R.id.detail_fragment_container);

        Post currPost = (Post)getIntent().getSerializableExtra("POST");

        if(f == null) {
            f = DetailFragment.newInstance(currPost);
        }

        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.detail_fragment_container, f);
        ft.commit();

    }
}
