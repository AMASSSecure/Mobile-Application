package inc.rhino.rhinoguard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CarInfoActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth mAuth;
    private TextView regNo,dispName, make,model,extra;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        DatabaseReference DB = database.getReference(user.getUid().toString());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_info);

        regNo = (TextView) findViewById(R.id.regNo);
        dispName = (TextView) findViewById(R.id.dispName);
        make = (TextView) findViewById(R.id.make);
        model = (TextView) findViewById(R.id.model);
        extra = (TextView) findViewById(R.id.extra);
        Spinner carSpinner = (Spinner) findViewById(R.id.spinner);



       DB.child("Vehicle Info").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Is better to use a List, because you don't know the size
                // of the iterator returned by dataSnapshot.getChildren() to
                // initialize the array
                final List<String> cars = new ArrayList<String>();

                for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                    String carName = areaSnapshot.child("Display Name").getValue(String.class);
                    cars.add(carName);
                }

                Spinner carSpinner = (Spinner) findViewById(R.id.spinner);
                ArrayAdapter<String> carsAdapter = new ArrayAdapter<String>(CarInfoActivity.this, android.R.layout.simple_spinner_item, cars);
                carsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                carSpinner.setAdapter(carsAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        DB.child("Vehicle Info").child(carSpinner.toString()).child("Reg Number").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                regNo.setText(text);
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
        DB.child("Vehicle Info").child(carSpinner.toString()).child("Make").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                make.setText(text);
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
        DB.child("Vehicle Info").child(carSpinner.toString()).child("Model").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                model.setText(text);
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
        DB.child("Vehicle Info").child(carSpinner.toString()).child("Extra").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                regNo.setText(text);
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
        DB.child("Vehicle Info").child(carSpinner.toString()).child("Display Name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                dispName.setText(text);
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }
}
