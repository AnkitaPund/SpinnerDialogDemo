package com.example.spinnerdialogdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
Button mshowDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference=FirebaseDatabase.getInstance().getReference();



        mshowDialog=findViewById(R.id.btnShowDialog);
        mshowDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_spinner,null);
                mBuilder.setTitle("Spinner in custom dialog");
                Spinner nameSpinner = mView.findViewById(R.id.productName);
                Spinner companySpinner=mView.findViewById(R.id.companyName);
                Spinner specificationSpinner=mView.findViewById(R.id.specification);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.productList));
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                nameSpinner.setAdapter(adapter);

                ArrayAdapter<String> companyadapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.companyList));
                companyadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                nameSpinner.setAdapter(companyadapter);
                mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(!nameSpinner.getSelectedItem().toString().equalsIgnoreCase("Choose a restaurant..")){
                            String RestaurantValue = nameSpinner.getSelectedItem().toString();
                            addDataToFireBase(RestaurantValue);
                            Toast.makeText(MainActivity.this,nameSpinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                            dialogInterface.dismiss();
                        }
                    }
                });
                mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        });
    }

    private void addDataToFireBase(String restaurantValue) {
        databaseReference.push().setValue(restaurantValue);

    }
}