package itp341.vong.mark.finalproject;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import itp341.vong.mark.finalproject.model.Location;
import itp341.vong.mark.finalproject.model.Post;
import itp341.vong.mark.finalproject.model.User;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment {

    private ListView feedListView;
    private FirebaseDatabase database;
    private DatabaseReference databaseRef;
    private ArrayList<Post> myPosts;


    public ListFragment() {
        // Required empty public constructor
    }

    public static ListFragment newInstance() {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list, container, false);

        feedListView = (ListView)v.findViewById(R.id.feedList);
        database = FirebaseDatabase.getInstance();
        databaseRef = database.getReference("Posts");

        // With reference to database child, read from the database to check if any data was changed/added
        // Retrieve data from database
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> postIterable = dataSnapshot.getChildren();
                Iterator<DataSnapshot> postIterator = postIterable.iterator();
                myPosts = new ArrayList<Post>();
                while(postIterator.hasNext()) {
                    DataSnapshot singlePostIterable = postIterator.next();
                    String title = (String)singlePostIterable.child("title").getValue();
                    String description = (String)singlePostIterable.child("description").getValue();
                    HashMap<String, String> locationMap = (HashMap<String, String>)singlePostIterable.child("location").getValue();
                    Location location = new Location(locationMap.get("housenumber"), locationMap.get("streetname")
                            , locationMap.get("city"), locationMap.get("state"));
                    HashMap<String, String> userMap = (HashMap<String, String>)singlePostIterable.child("mainUser").getValue();
                    User user = new User(userMap.get("username"), userMap.get("password"), userMap.get("email"));
                    String time = (String)singlePostIterable.child("time").getValue();
                    Boolean offer = (Boolean)singlePostIterable.child("offer").getValue();
                    Post currPost = new Post(title, user, description, time, location, offer);
                    myPosts.add(currPost);
                }

                FeedArrayAdapter myAdapter = new FeedArrayAdapter(getActivity(), myPosts);
                feedListView.setAdapter(myAdapter);

                feedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent i = new Intent(getActivity(), DetailActivity.class);
                        i.putExtra("POST", myPosts.get(position));
                        startActivity(i);
                    }
                });
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return v;
    }
    // Custom ArrayAdapter
    private class FeedArrayAdapter extends ArrayAdapter<Post> {

        private ArrayList<Post> myPosts;

        public FeedArrayAdapter(Context context, ArrayList<Post> myPosts) {
            super(context, 0, myPosts);
            this.myPosts = myPosts;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.feedlayout, null);
            }
            ImageView feedImageView = (ImageView)convertView.findViewById(R.id.feedImageView);
            TextView requestTextView = (TextView)convertView.findViewById(R.id.requestTextView);
            TextView timeTextView = (TextView)convertView.findViewById(R.id.timeTextView);

            if(myPosts.get(position).isOffer()) {
                feedImageView.setImageResource(R.drawable.officon);
            }
            else {
                feedImageView.setImageResource(R.drawable.reqicon);
            }
            requestTextView.setText(myPosts.get(position).getDescription());
            timeTextView.setText(myPosts.get(position).getTime());


            return convertView;
        }
    }



}
