package inc.rhino.rhinoguard;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static android.R.id.toggle;
import static android.content.Intent.ACTION_DIAL;
import static inc.rhino.rhinoguard.R.id.button;

public class MainActivity extends AppCompatActivity {

    private String phone = "112";
    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference killDB = database.getReference("isKill/kill");
    DatabaseReference movDB = database.getReference("accelerometer/moving");
    private String currentCar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void sendMessage(View view) {
        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
        startActivity(intent);
    }

    public void shutdownCar(View view) {
        movDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                Toast.makeText(MainActivity.this, "Car Moving: " + value,
                        Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(MainActivity.this, "Failed to Read",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onReport(View view) {
        Intent i = new Intent(Intent.ACTION_DIAL);
        String p = "tel:" + phone;
        i.setData(Uri.parse(p));
        startActivity(i);
    }

    public void showDialog(Activity activity, String title, CharSequence message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        if (title != null) builder.setTitle(title);

        builder.setMessage(message);
        builder.setPositiveButton("OK", null);
        builder.show();
    }

    public void onKill(View view) {
        killDB.child("kill").setValue("no");
    }

    public void offKill(View view) {
        killDB.child("kill").setValue("yes");
    }

    public void gotoJourneys(View view) {
        Intent intent = new Intent(MainActivity.this, JourneysActivity.class);
        startActivity(intent);
    }

    public void fleetManager(View v)
    {
        DatabaseReference fleetDB = database.getReference();
        fleetDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final List<String> cars = new ArrayList<>();

                for (DataSnapshot carsSnapshot : dataSnapshot.getChildren()) {
                    String carName = carsSnapshot.child("Vehicle Info").child("Security Number").getValue(String.class);
                    cars.add(carName);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void registerCar(View view) {
        Intent intent = new Intent(MainActivity.this, RegisterCarActivity.class);
        startActivity(intent);
    }
    public void selectCar(View view) {
        Intent intent = new Intent(MainActivity.this, RegisterCarActivity.class);
        startActivity(intent);
    }
}





