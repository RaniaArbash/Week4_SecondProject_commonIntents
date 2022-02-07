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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textToSearch = (EditText) findViewById(R.id.query);
        photo = (ImageView) findViewById(R.id.photo);
        takeAPhotoButton = (Button) findViewById(R.id.camera);
        searchButton = (Button) findViewById(R.id.searchID);
        if (checkSelfPermission(Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA},
                    MY_CAMERA_REQUEST_CODE);
        }

        final ActivityResultLauncher<Intent> cameraActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK )  {
                            // There are no request codes
                            Intent data = result.getData();
                            Bundle extra = data.getExtras();
                            Bitmap imageFromCamaraApp = (Bitmap) extra.get("data");
                            photo.setImageBitmap(imageFromCamaraApp);
                        }
                    }
                });
        takeAPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (cameraIntent.resolveActivity(getPackageManager()) != null)
                    cameraActivityResultLauncher.launch(cameraIntent);

                //startActivityForResult(cameraIntent,REQUEST_IMAGE_CAPTURE);// camera App id = 1
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!textToSearch.getText().toString().isEmpty()){

                    Intent searchIntent = new Intent(Intent.ACTION_WEB_SEARCH);
                    searchIntent.putExtra(SearchManager.QUERY, textToSearch.getText().toString());
                    if (searchIntent.resolveActivity(getPackageManager()) != null){
                        startActivity(searchIntent);
                    }
                }
            }
        });

        // email app  id = 2
        // clock app id = 3
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_CAMERA_REQUEST_CODE) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();

            } else {

                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();

            }

        }
    }





//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
//            Bundle extra = data.getExtras();
//            Bitmap imageFromCamaraApp = (Bitmap) extra.get("data");
//            photo.setImageBitmap(imageFromCamaraApp);
//        }
//
//    }
}