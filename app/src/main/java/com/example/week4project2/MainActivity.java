package com.example.week4project2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.SearchManager;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.content.Intent;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
public class MainActivity extends AppCompatActivity {
    private static final int MY_CAMERA_REQUEST_CODE = 100;

    EditText textToSearch;
    ImageView photo;
    Button takeAPhotoButton;
    Button searchButton;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    ActivityResultLauncher<Intent> cameraResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textToSearch =  findViewById(R.id.query);
        photo =  findViewById(R.id.photo);
        takeAPhotoButton = findViewById(R.id.camera);



        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.CAMERA},MY_CAMERA_REQUEST_CODE);
        }

        cameraResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                // I have a result which is a photo
                // I can do what I want with this result
                if (result.getResultCode() == Activity.RESULT_OK){
                    Intent data = result.getData();
                    Bundle bundle = data.getExtras();
                    Bitmap image = (Bitmap) bundle.get("data");
                    photo.setImageBitmap(image);
                }
            }
        });


        takeAPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    cameraResultLauncher.launch(cameraIntent);
                }else
                    requestPermissions(new String[]{Manifest.permission.CAMERA},MY_CAMERA_REQUEST_CODE);
            }
        });


        searchButton =  findViewById(R.id.searchID);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchIntent = new Intent(Intent.ACTION_WEB_SEARCH);

                if (!textToSearch.getText().toString().isEmpty()){
                    String query = textToSearch.getText().toString();
                    searchIntent.putExtra(SearchManager.QUERY,query);

                    if (searchIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(searchIntent);
                    }

                }


            }
        });

    }
}