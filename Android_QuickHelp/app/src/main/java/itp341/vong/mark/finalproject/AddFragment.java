package itp341.vong.mark.finalproject;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import itp341.vong.mark.finalproject.model.Location;
import itp341.vong.mark.finalproject.model.Post;
import itp341.vong.mark.finalproject.model.User;


public class AddFragment extends Fragment {

    private RadioGroup helpRadioGroup;
    private RadioButton serviceRadioButton;
    private RadioButton requestRadioButton;
    private EditText nameEdit;
    private EditText descriptionEdit;
    private EditText housenumberEdit;
    private EditText streetnameEdit;
    private EditText cityEdit;
    private EditText stateEdit;
    private Button saveButton;

    private FirebaseDatabase database;
    private DatabaseReference databaseRef;

    private User currUser;

    private String title;
    private String description;
    private String housenumber;
    private String streetname;
    private String city;
    private String state;

    private Location location;


    public AddFragment() {
        // Required empty public constructor
    }


    public static AddFragment newInstance() {
        AddFragment fragment = new AddFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add, container, false);
        initGUIComp(v);
        addListeners();
        currUser = LoginFragment.currUser;

        return v;
    }

    // Init Components
    private void initGUIComp(View v) {
        database = FirebaseDatabase.getInstance();

        databaseRef = database.getReference("Posts");

        helpRadioGroup = (RadioGroup)v.findViewById(R.id.helpRadioGroup);
        serviceRadioButton = (RadioButton)v.findViewById(R.id.serviceRadio);
        requestRadioButton = (RadioButton)v.findViewById(R.id.requestRadio);
        nameEdit = (EditText)v.findViewById(R.id.nameEdit);
        descriptionEdit = (EditText)v.findViewById(R.id.descriptionEdit);
        housenumberEdit = (EditText)v.findViewById(R.id.housenumberEdit);
        streetnameEdit = (EditText)v.findViewById(R.id.streetnameEdit);
        cityEdit = (EditText)v.findViewById(R.id.cityEdit);
        stateEdit = (EditText)v.findViewById(R.id.stateEdit);



        saveButton = (Button)v.findViewById(R.id.saveButton);

    }

    private boolean checkAllFieldsFilled() {
        boolean filled = false;

        title = nameEdit.getText().toString().trim();
        description = descriptionEdit.getText().toString().trim();
        housenumber = housenumberEdit.getText().toString().trim();
        streetname = streetnameEdit.getText().toString().trim();
        city = cityEdit.getText().toString().trim();
        state = stateEdit.getText().toString().trim();
        // Check if all fields are filled
        if(title.length() > 0 && description.length() > 0 && housenumber.length() > 0 && streetname.length() > 0
                && city.length() > 0 && state.length() > 0) {
            filled = true;
            location = new Location(housenumber, streetname, city, state);
        }

        return filled;
    }

    private void addListeners() {
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat time = new SimpleDateFormat("HH:mm");
                Timestamp ts = new Timestamp(System.currentTimeMillis());
                String timeString = time.format(ts);
                // If all fields are filled, create a new post and push to database, feed with update accordingly
                if(checkAllFieldsFilled()) {
                    Post myPost = new Post(title, currUser, description, timeString, location, serviceRadioButton.isChecked());
                    Log.d("USER::", LoginFragment.currUser.getEmail());
                    databaseRef.push().setValue(myPost);
                    getActivity().finish();
                }
                else {
                    Toast.makeText(getActivity(), "Enter in all the fields!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
