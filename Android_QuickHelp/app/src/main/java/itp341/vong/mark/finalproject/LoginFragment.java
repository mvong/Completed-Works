package itp341.vong.mark.finalproject;


import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

import itp341.vong.mark.finalproject.model.Location;
import itp341.vong.mark.finalproject.model.Post;
import itp341.vong.mark.finalproject.model.User;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button registerButton;
    private TextView errorLabel;

    public static FirebaseAuth userAuth;
    private FirebaseAuth.AuthStateListener userAuthListener;
    private FirebaseDatabase database;
    private DatabaseReference userRef;

    public static User currUser;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        userAuth = FirebaseAuth.getInstance();
        userAuthListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mainUser = firebaseAuth.getCurrentUser();

                if(mainUser != null) {
                    Log.d("Tag", "User is signed in.");
                }
                else {
                    Log.d("Tag", "User is signed out.");
                }
            }
        };
        userAuth.addAuthStateListener(userAuthListener);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("Users");

        usernameEditText = (EditText)v.findViewById(R.id.usernameEdit);
        passwordEditText = (EditText)v.findViewById(R.id.passwordEdit);
        loginButton = (Button)v.findViewById(R.id.loginButton);
        registerButton = (Button)v.findViewById(R.id.registerButton);
        errorLabel = (TextView)v.findViewById(R.id.errorLabel);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                if(username.length() > 0 && password.length() > 0) {
                    signInUser(username, password);
                }
                Log.d("Button clicked: ", "LOGIN BUTTON CLICKED");
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                if(username.length() > 0 && password.length() > 0) {
                    createUser(username, password);
                }
                Log.d("Button clicked: ", "REGISTER BUTTON CLICKED");
            }
        });
        return v;
    }

    // Create a user with provided email and password
    private void createUser(final String email, final String password) {
        userAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()) {
                    Log.d("Error: ", "Creating user failed.");
                    errorLabel.setText("User already exists!");
                }
                else if(task.isSuccessful()) {
                    currUser = new User(getUsername(email), password, email);
                    addUserToDatabase(currUser);
                    loadFeedFragment();
                }

            }
        });
    }
    // Sign in an already existing user with correct credentials, firebase auth handles verification
    private void signInUser(final String email, final String password) {
        userAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()) {
                    Log.d("Error: ", "Signing in user failed.");
                    errorLabel.setText("User doesn't exist or password is wrong.");
                }
                else if(task.isSuccessful()) {
                    getUserFromDatabase(new User(getUsername(email), password, email));
                }

            }
        });
    }

    // Load the general feed to display all posts
    private void loadFeedFragment() {
        Intent i = new Intent(getActivity(), ListActivity.class);
        startActivity(i);
    }

    // Parse email for username
    private String getUsername(String email) {
        String[] split = email.split("@");
        return split[0];
    }

    // Add user to database
    private void addUserToDatabase(User currUser) {
        userRef.push().setValue(currUser);
    }

    // Find existing user from database
    private void getUserFromDatabase(final User cUser) {
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> userIterable = dataSnapshot.getChildren();
                Iterator<DataSnapshot> userIterator = userIterable.iterator();
                boolean found = false;
                while(userIterator.hasNext()) {
                    DataSnapshot userSingleIterable = (DataSnapshot)userIterator.next();
                    String email = (String)userSingleIterable.child("email").getValue();
                    if(email.equals(cUser.getEmail())) {
                        found = true;
                        String username = (String) userSingleIterable.child("username").getValue();
                        String password = (String) userSingleIterable.child("password").getValue();
                        currUser = new User(username, password, email);
                        loadFeedFragment();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
