package com.example.week4project2;

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

    EditText messageToShare;
    ImageView photo;
    Button takeAPhotoButton;
    Button shareButton;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        messageToShare = (EditText) findViewById(R.id.message);
        photo = (ImageView) findViewById(R.id.photo);
        takeAPhotoButton = (Button) findViewById(R.id.camera);
        shareButton = (Button) findViewById(R.id.share_id_butt);

        takeAPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }

            }
        });
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
//                Intent callIntent = new Intent();
//                callIntent.setAction(Intent.ACTION_DIAL);
//                startActivity(callIntent);

                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY, "Android studio");
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }

//                Intent sendIntent = new Intent();
//
//                sendIntent.setAction(Intent.ACTION_SEND);
//                sendIntent.putExtra(Intent.EXTRA_TEXT, messageToShare.getText());
//                sendIntent.setType("text/plain");
//
//// Verify that the intent will resolve to an activity
//                if (sendIntent.resolveActivity(getPackageManager()) != null) {
//                    startActivity(sendIntent);
//                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            photo.setImageBitmap(imageBitmap);
        }
    }
}
