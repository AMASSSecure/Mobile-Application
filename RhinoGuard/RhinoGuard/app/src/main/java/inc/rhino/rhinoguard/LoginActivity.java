package inc.rhino.rhinoguard;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.R.color.holo_blue_dark;
import static android.graphics.Color.RED;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText emailID,pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailID = (EditText)findViewById(R.id.editText);
        pass = (EditText)findViewById(R.id.editText2);
    }
    public void sendMessage(View view)
    {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public void startSignUp(View v){
        String email = emailID.getText().toString();
        String password = pass.getText().toString();
        if(isValidEmail(email) == true){
            if(password.length() > 3 && password.length() < 12){
                SignUp();
            }
        }
        else{
            Toast.makeText(LoginActivity.this, "Invalid Email/Password",
                    Toast.LENGTH_SHORT).show();
            emailID.setText("");
            emailID.setHint("johndoe@email.com");
            emailID.setHintTextColor(RED);
            pass.setText("");
            pass.setHint("Password must be 5 to 12 chars");
            pass.setHintTextColor(RED);

        }
    }

    public void SignIn(View v) {
        if(emailID.getText() != null && pass.getText() != null) {

            String email = emailID.getText().toString();
            String password = pass.getText().toString();


            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (!task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Sign in Failed." + task.getException(),
                                        Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this, LoginActivity.class));

                            } else {
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            }
                        }
                    });
        }
    }

        public void SignUp(){
        final String email = emailID.getText().toString();
        String password = pass.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Sign Up Failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        }
                        else {
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference();
                            FirebaseUser user = mAuth.getCurrentUser();
                            myRef.child(user.getUid()).child("Email").setValue(email);
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        }

                    }
                });
    }
    }

