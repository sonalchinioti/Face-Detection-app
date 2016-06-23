package com.sonal.android.facerecognization;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by User on 22-06-2016.
 */
public class ActivityImageDetection extends AppCompatActivity {
    private FaceOverlayView mFaceOverlayView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detection);
        mFaceOverlayView = (FaceOverlayView) findViewById( R.id.face_overlay );
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        Uri selectedImage =(Uri)bundle.getParcelable("Image");
        loadImage(selectedImage);

//        InputStream stream = getResources().openRawResource( R.raw.face );
//        Bitmap bitmap = BitmapFactory.decodeStream(stream);

    }

  public void  loadImage(Uri selectedImage){
      try{
          Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
          if(bitmap != null) {
              mFaceOverlayView.setBitmap(bitmap);
          }

      }catch(Exception e){

      }

    }
}
