package com.first.dsc.dsc_first_one;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
  private int IMAGE_CODE = 207;
  private Button getImageButton;
  private ImageView imageView;
  private TextView statusTv;
  private ImageButton shareButton;
  private Uri imageUri = null;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    //initialize

    imageView = findViewById(R.id.mainActivityImage);
    statusTv = findViewById(R.id.status);
    getImageButton = findViewById(R.id.mainActivityLoadBtn);
    shareButton=findViewById(R.id.sharebutton);

    //set button click event

    getImageButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        getImage();
      }
    });

    shareButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        shareImage();
      }
    });
  }

  //create to handle loading of the image from the gallery

  private void getImage() {
    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
    intent.setType("image/*");
    startActivityForResult(intent, IMAGE_CODE);
  }

  //listen for the result and check against the code  for the right results

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == IMAGE_CODE && resultCode == Activity.RESULT_OK) {
      //update the status and call method to set the image
      statusTv.setText("Successfully Pick !");
      if (data != null) {
        imageUri = data.getData();
        imageView.setImageURI(imageUri);
      } else {
        statusTv.setText("Did not get the image!");
      }
    } else {
      //update the status
      statusTv.setText("Did not get the image!");
    }
  }

  // share the image we picked
  public void shareImage() {
    if (imageUri != null) {
      Intent shareIntent=new Intent(Intent.ACTION_SEND);
      shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //temporary permission
      shareIntent.setDataAndType(imageUri,getContentResolver().getType(imageUri));
      shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
      shareIntent.setType("image/png");
      startActivity(Intent.createChooser(shareIntent, "Choose an app"));

    } else {
      statusTv.setText("Please pick an image first");
    }
  }
}
