package inc.rhino.rhinoguard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterCarActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText regNo,dispName, make,model,extra;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_car);
        regNo = (EditText)findViewById(R.id.regNo);
        dispName = (EditText)findViewById(R.id.dispName);
        make = (EditText)findViewById(R.id.make);
        model = (EditText)findViewById(R.id.model);
        extra = (EditText)findViewById(R.id.extra);

    }



    public void onRegister(View v){
        FirebaseUser user = mAuth.getCurrentUser();
        if(regNo != null && dispName != null && make != null && model != null && extra != null) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();
            myRef.child(user.getUid()).child("Vehicle Info").child(dispName.getText().toString()).child("Reg Number").setValue(regNo.getText().toString());
            myRef.child(user.getUid()).child("Vehicle Info").child(dispName.getText().toString()).child("Display Name").setValue(dispName.getText().toString());
            myRef.child(user.getUid()).child("Vehicle Info").child(dispName.getText().toString()).child("Make").setValue(make.getText().toString());
            myRef.child(user.getUid()).child("Vehicle Info").child(dispName.getText().toString()).child("Model").setValue(model.getText().toString());
            myRef.child(user.getUid()).child("Vehicle Info").child(dispName.getText().toString()).child("Extra").setValue(extra.getText().toString());
            Toast.makeText(RegisterCarActivity.this, "Car Information Added",
                    Toast.LENGTH_SHORT).show();
            startActivity(new Intent(RegisterCarActivity.this, MainActivity.class));
        }
        else{
            Toast.makeText(RegisterCarActivity.this, "Please Fill all Fields",
                    Toast.LENGTH_SHORT).show();
            startActivity(new Intent(RegisterCarActivity.this, RegisterCarActivity.class));
        }
    }

    public void carInfo(View view) {
        Intent intent = new Intent(RegisterCarActivity.this, CarInfoActivity.class);
        startActivity(intent);
    }
}
