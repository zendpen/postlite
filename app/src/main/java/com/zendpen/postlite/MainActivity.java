package com.zendpen.postlite;

import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.zendpen.postlite.ui.main.SectionsPagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 123;
    private static final String TAG = "EmailPassword";
    private FirebaseAuth mAuth;
    private ListView mListView;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    FirebaseUser fullUser;
    //private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = null;
        mAuth = FirebaseAuth.getInstance();
        fullUser = mAuth.getCurrentUser();
        //FirebaseUser currentUser = mAuth.getCurrentUser();
        setContentView(R.layout.signin_activity);

    }

    public void createUserClick(View v){
        EditText email = (EditText)findViewById(R.id.emailText);
        EditText password = (EditText)findViewById(R.id.passwordText);

        createAccount(email.getText().toString(), password.getText().toString());
    }

    public void signInUserClick(View v){
        EditText email = (EditText)findViewById(R.id.emailText);
        EditText password = (EditText)findViewById(R.id.passwordText);

        signinAccount(email.getText().toString(), password.getText().toString());
    }

    public void updateUI(FirebaseUser user){
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                  //      .setAction("Action", null).show();
            }
        });

        CustomUser customUser = new CustomUser("rrrrrrttttttttt", 19, user.getEmail());
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String userId = user.getUid();
        DatabaseReference mRef =  database.getReference("Users").child("Users").child(userId);
        mRef.setValue(customUser);

        //QuestionClass question = new QuestionClass("Where is the hello world?");
        //DatabaseReference qRef = database.getReference().child("Questions").child("Question");
        //qRef.setValue(question);
    }

    public void qPostClick(View v){
        EditText post = (EditText)findViewById(R.id.qEditText);
        QuestionClass question = new QuestionClass(post.getText().toString());
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference qRef = database.getReference();
        String key = qRef.child("Questions").push().getKey();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/Questions/" + key, question);
        qRef.updateChildren(childUpdates);

        //updateUI(fullUser);
    }

    public void sPostClick(View v){
        EditText post = (EditText)findViewById(R.id.sEditText);
        QuestionClass question = new QuestionClass(post.getText().toString());
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference qRef = database.getReference();
        String key = qRef.child("Statements").push().getKey();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/Statements/" + key, question);
        qRef.updateChildren(childUpdates);

        //updateUI(fullUser);
    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);

        //showProgressDialog();

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // [START_EXCLUDE]
                        //hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }

    private void signinAccount(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            fullUser = user;
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }

}