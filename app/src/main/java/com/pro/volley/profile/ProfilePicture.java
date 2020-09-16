package com.pro.volley.profile;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.appbar.MaterialToolbar;
import com.pro.volley.R;
import com.pro.volley.SharedPreferencesHelper;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ProfilePicture extends AppCompatActivity {

    ImageView iv_profile;
    //SharedPreferences sharedPreferences;
    SharedPreferencesHelper sharedPreferencesHelper;
    SharedPreferencesHelper.ProfileSharedPreference profileSharedPreference;
    Bitmap profilepic, dp;
    String imagedata;
    private Uri filePath, uri, u;
    Handler handler, handler2;
    MaterialToolbar toolbar;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_top_profilepicture, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_profile_picture);
        iv_profile = findViewById(R.id.iv_profilepicture_image);
        // sharedPreferences = this.getSharedPreferences("com.pro.volley.ProfileSharedPreference", MODE_PRIVATE);
        sharedPreferencesHelper = new SharedPreferencesHelper(getApplicationContext(), "com.pro.volley");
        profileSharedPreference = sharedPreferencesHelper.getProfileSharedPreference();
//Intent i=getIntent();
//final String imagedata=i.getExtras().getString("imagedata");
//Toast.makeText(getApplicationContext(),imagedata,Toast.LENGTH_SHORT).show();
        // bitmap=stringToBitMap(imagedata);

        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                iv_profile.setImageBitmap(dp);

                // sharedPreferences.edit().putString("imagedata", bitmapToString(dp)).apply();
                profileSharedPreference.setGetProfile_imagedata(bitmapToString(dp));


            }
        };
        String s=profileSharedPreference.getGetProfile_imagedata();
        if (!s.equalsIgnoreCase("null")) {
Log.i("profile Page :","not nulll");

            profilepic = stringToBitMap(s);
            iv_profile.setImageBitmap(profilepic);


        } else {

            Log.i("profile Page :"," nulll value for image data");
            iv_profile.setImageResource(R.drawable.dronecollege);

        }


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profilepic_edit:
                choose_file();
                return true;
            case R.id.profilepic_share:
                shareImageUri();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void shareImageUri() {
        if (profilepic != null) {


            if (u == null) {
                u = saveImage(profilepic);
            }
            Intent intent = new Intent(android.content.Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_STREAM, u);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setType("image/png");
            startActivity(intent);
        }


    }

    private Uri saveImage(Bitmap image) {
        //TODO - Should be processed in another thread
        File imagesFolder = new File(getCacheDir(), "images");
        Uri uri = null;
        try {
            imagesFolder.mkdirs();
            File file = new File(imagesFolder, "shared_image.png");

            FileOutputStream stream = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.PNG, 90, stream);
            stream.flush();
            stream.close();
            uri = FileProvider.getUriForFile(this, "com.mydomain.fileprovider", file);

        } catch (IOException e) {
            Log.d("Error", "IOException while trying to write file for sharing: " + e.getMessage());
        }
        return uri;
    }

    public Bitmap stringToBitMap(String encodedString) {


        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), Profile.class);
        startActivity(i);
        finish();
    }


    private void choose_file() {

        CropImage.startPickImageActivity(this);
    }

    private void uploadProfilePicture(final Bitmap bitmap) {

        Runnable r = new Runnable() {
            @Override
            public void run() {
                //  final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                final StringRequest request = new StringRequest(Request.Method.POST, getResources().getString(R.string.url) + "profile/uploadProfilePic.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("title").equals("success")) {
                                Log.i("uploadProfilePicture", "msg: Everything is correct");
                                dp = bitmap;
                                profilepic = bitmap;
                                u = saveImage(bitmap);

                                handler.sendEmptyMessage(0);

                                return;
                            } else {
                                //  Toast.makeText(getApplicationContext(), jsonObject.getString("title") + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                Log.i("uploadProfilePicture", "msg: php Error");
                                //return;

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(), " " + "Server error", Toast.LENGTH_SHORT).show();

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("image", bitmapToString(bitmap));
                        params.put("usn", sharedPreferencesHelper.getUsn());
                        return params;
                    }
                };
                int socketTimeOut = 50000;
                RetryPolicy policy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                request.setRetryPolicy(policy);
                RequestQueue queue = Volley.newRequestQueue(ProfilePicture.this);
                queue.add(request);


            }
        };
        Thread t = new Thread(r);
        t.start();


        //  requestQueue.add(request);
    }

    private String bitmapToString(Bitmap bitmap) {


        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int bc = bitmap.getByteCount();
        Log.i("image quality:", "Image quality :" + bc);

        if (bitmap.getByteCount()>1000000){
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream);
            Log.i("image quality:", "Image quality : 1  :" + bc);


        }else
        if (bitmap.getByteCount() > 100000) {
            Log.i("image quality:", "Image quality  : 2 :" + bc);

            bitmap.compress(Bitmap.CompressFormat.JPEG, 30, byteArrayOutputStream);
        }else {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);


        }


        bc = byteArrayOutputStream.size();        Log.i("image quality:", "Image quality :" + bc);

        byte[] b = byteArrayOutputStream.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);

        return temp;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            filePath = CropImage.getPickImageResultUri(this, data);
            if (CropImage.isReadExternalStoragePermissionsRequired(this, filePath)) {
                uri = filePath;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            } else {
                imageCrop(filePath);
            }

        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                Bitmap bitmap = null;
                try {

                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), result.getUri());


                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (bitmap != null) {

                    uploadProfilePicture(bitmap);
                }


            }
        }
    }


    private void imageCrop(Uri filePath) {
        CropImage.activity(filePath).setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);
    }

}