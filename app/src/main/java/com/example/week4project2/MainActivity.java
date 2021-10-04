package com.example.week4project2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

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

        takeAPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (cameraIntent.resolveActivity(getPackageManager()) != null)
                    startActivityForResult(cameraIntent,REQUEST_IMAGE_CAPTURE);// camera App id = 1

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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extra = data.getExtras();
            Bitmap imageFromCamaraApp = (Bitmap) extra.get("data");
            photo.setImageBitmap(imageFromCamaraApp);
        }

    }
}