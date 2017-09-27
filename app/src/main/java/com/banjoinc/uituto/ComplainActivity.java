package com.banjoinc.uituto;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static android.provider.MediaStore.Images.Media.getBitmap;

public class ComplainActivity extends AppCompatActivity {
    public final static int REQUEST_PHOTO= 5;
    public final static int REQUEST_PERMISSION= 5;
    private Uri imageToUploadUri1;
    Button camera;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain);
        final PackageManager pmg = getPackageManager();

        image = (ImageView)findViewById(R.id.photoview);
        camera = (Button)findViewById(R.id.photobtn);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if(pmg.hasSystemFeature(PackageManager.FEATURE_CAMERA)==false){
                        Toast.makeText(ComplainActivity.this,"This device doesn´t have camera",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    pickIntent.setType("image/*");
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(Environment.getExternalStorageDirectory(), "complain.png");
                    imageToUploadUri1=Uri.fromFile(f);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageToUploadUri1);

                String picktitle = "Take or select a photo";
                Intent chooser = Intent.createChooser(pickIntent,picktitle);
                chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{takePictureIntent});
                Uri imageToUploadUri = Uri.fromFile(f);
                if(takePictureIntent.resolveActivity(getPackageManager())!= null){
                    startActivityForResult(chooser, REQUEST_PHOTO);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PHOTO && resultCode == Activity.RESULT_OK) {
            if(data!=null) {
                imageToUploadUri1 = data.getData();
            }
            if(imageToUploadUri1 != null){
                Uri selectedImage = imageToUploadUri1;
                getContentResolver().notifyChange(selectedImage, null);

                if (ContextCompat.checkSelfPermission(this,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            REQUEST_PERMISSION);


                }
                Bitmap reducedSizeBitmap = getBitmap(getRealPathFromURI(imageToUploadUri1));
                if(reducedSizeBitmap != null) {

                    image.setImageBitmap(reducedSizeBitmap);
                }
            }else{
                Toast.makeText(this,"Error while capturing Image by Uri",Toast.LENGTH_LONG).show();
            }
        }
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result ="";
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            try{
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                result = cursor.getString(idx);

            }catch(Exception e)
            {
                return contentURI.getPath();
            }
            cursor.close();
        }
        return result;
    }
    private Bitmap getBitmap(String path) {

        Uri uri = Uri.fromFile(new File(path));
        InputStream in = null;
        try {
            final int IMAGE_MAX_SIZE = 1200000; // 1.2MP
            in = getContentResolver().openInputStream(uri);

            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(in, null, o);
            in.close();


            int scale = 1;
            while ((o.outWidth * o.outHeight) * (1 / Math.pow(scale, 2)) >
                    IMAGE_MAX_SIZE) {
                scale++;
            }


            Bitmap b = null;
            in = getContentResolver().openInputStream(uri);
            if (scale > 1) {
                scale--;
                // scale to max possible inSampleSize that still yields an image
                // larger than target
                o = new BitmapFactory.Options();
                o.inSampleSize = scale;
                b = BitmapFactory.decodeStream(in, null, o);

                // resize to desired dimensions
                int height = b.getHeight();
                int width = b.getWidth();


                double y = Math.sqrt(IMAGE_MAX_SIZE
                        / (((double) width) / height));
                double x = (y / height) * width;

                Bitmap scaledBitmap = Bitmap.createScaledBitmap(b, (int) x,
                        (int) y, true);
                b.recycle();
                b = scaledBitmap;

                System.gc();
            } else {
                b = BitmapFactory.decodeStream(in);
            }
            in.close();


            return b;
        } catch (IOException e) {

            return null;
        }
    }
}
