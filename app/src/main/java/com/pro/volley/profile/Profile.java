package com.pro.volley.profile;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.pro.volley.MainActivity;
import com.pro.volley.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;


import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Profile extends AppCompatActivity {
    TextView tv_name, tv_usn, tv_email, tv_phno, tv_sem, tv_dept, tv_sec;
    SharedPreferences sharedPreferences, sharedPreferences_profile;
    SwipeRefreshLayout swipeRefreshLayout;
    String usn, name, email, phno, sem, sec, dept;
    static String imgurl = "null";
    ImageView iv_profile_pic;
    private Uri filePath, uri;
    private static int SELECT_PHOTO = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_profile);
        sharedPreferences = this.getSharedPreferences("com.pro.volley", MODE_PRIVATE);
        sharedPreferences_profile = this.getSharedPreferences("com.pro.volley.profile", MODE_PRIVATE);
        iv_profile_pic = findViewById(R.id.iv_profile_pic);
        swipeRefreshLayout = findViewById(R.id.sr_profile);
        tv_email = findViewById(R.id.tv_profile_email);
        tv_name = findViewById(R.id.tv_profile_name);
        tv_usn = findViewById(R.id.tv_profile_usn);
        tv_phno = findViewById(R.id.tv_profile_phno);
        tv_dept = findViewById(R.id.tv_profile_dept);
        tv_sem = findViewById(R.id.tv_profile_sem);
        tv_sec = findViewById(R.id.tv_profile_sec);
        if (sharedPreferences_profile.contains("usn")) {
            update_profile();
            try {
                Glide.with(this) //1
                        // .load("http://192.168.43.180/mycollege/data/student/profile/17cs030.png")
                        .load(getResources().getString(R.string.urlroot) + imgurl)
                        .error(R.drawable.dronecollege)
                        .skipMemoryCache(true) //2
                        .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                        .into(iv_profile_pic);
            } catch (Exception e) {
                Log.i("Error in download", "error " + e.getMessage());

                e.printStackTrace();
            }



        } else {
            stringrequest();
        }
        refresh();

    }

    private void savedata() {
        sharedPreferences_profile.edit().putString("name", name).apply();
        sharedPreferences_profile.edit().putString("usn", usn).apply();
        sharedPreferences_profile.edit().putString("email", email).apply();
        sharedPreferences_profile.edit().putString("phno", phno).apply();
        sharedPreferences_profile.edit().putString("dept", dept).apply();
        sharedPreferences_profile.edit().putString("sem", sem).apply();
        sharedPreferences_profile.edit().putString("sec", sec).apply();
        sharedPreferences_profile.edit().putString("pic", imgurl).apply();


    }

    private void update_profile() {

        name = sharedPreferences_profile.getString("name", "001");
        usn = sharedPreferences_profile.getString("usn", "001");
        email = sharedPreferences_profile.getString("email", "001");
        phno = sharedPreferences_profile.getString("phno", "001");
        dept = sharedPreferences_profile.getString("dept", "001");
        sem = sharedPreferences_profile.getString("sem", "001");
        sec = sharedPreferences_profile.getString("sec", "001");
        imgurl = sharedPreferences_profile.getString("pic", "001");

        tv_name.setText(name);
        tv_usn.setText(usn);
        tv_email.setText(email);
        tv_phno.setText(phno);
        tv_dept.setText(dept);
        tv_sec.setText(sec);
        tv_sem.setText(sem);

        //update profile pic


    }

    private void refresh() {

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                stringrequest();

            }
        });
        iv_profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose_file();

            }
        });

    }

    private void choose_file() {

        CropImage.startPickImageActivity(this);
    }


    private void stringrequest() {
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, getResources().getString(R.string.url) + "profile/refreshprofile.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                requestQueue.getCache().clear();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("title").equals("success")) {
                        // Log.i("Profile :",response);
                        name = jsonObject.optString("name");
                        usn = jsonObject.optString("usn");
                        email = jsonObject.optString("email");
                        dept = jsonObject.optString("dept");
                        sem = jsonObject.optString("sem");
                        sec = jsonObject.optString("sec");
                        phno = jsonObject.optString("phno");
                        imgurl = jsonObject.optString("pic");

                        Log.i("profile pic:", response);
                        savedata();

                        update_profile();
                        download_profile_pic();

                        Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                        // return;


                    } else {
                        Toast.makeText(getApplicationContext(), jsonObject.getString("title") + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                        //return;

                    }

                } catch (Exception e) {
                    Log.i("Error in download", "error " + e.getMessage());

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
                params.put("usn", sharedPreferences.getString("usn", "001"));
                return params;
            }
        };

        requestQueue.add(stringRequest);
        swipeRefreshLayout.setRefreshing(false);
        return;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Profile.this, MainActivity.class);
        startActivity(i);
        finish();

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
                startCrop(filePath);
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


                uploadBitmap(bitmap);


            }
        }
    }

    private void startCrop(Uri filePath) {
        CropImage.activity(filePath).setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);
    }


    private void uploadBitmap(final Bitmap bitmap) {

        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        final StringRequest request = new StringRequest(Request.Method.POST, getResources().getString(R.string.url) + "profile/uploadProfilePic.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                requestQueue.getCache().clear();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("title").equals("success")) {

                        stringrequest();

                        return;
                    } else {
                        Toast.makeText(getApplicationContext(), jsonObject.getString("title") + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

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
                params.put("image", getStringImage(bitmap));
                params.put("usn", usn);
                return params;
            }
        };

        requestQueue.add(request);
    }

    private String getStringImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,10 , byteArrayOutputStream);
        byte[] b = byteArrayOutputStream.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);

        return temp;
    }


    private void download_profile_pic() {
        if (!imgurl.equalsIgnoreCase("null")) {
            //   Glide.with(this).clear(iv_profile_pic);

            try {
                Glide.with(this) //1
                        // .load("http://192.168.43.180/mycollege/data/student/profile/17cs030.png")
                        .load(getResources().getString(R.string.urlroot) + imgurl)
                        .error(R.drawable.dronecollege)
                        .skipMemoryCache(false) //2
                        .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                        .into(iv_profile_pic);
            } catch (Exception e) {
                Log.i("Error in download", "error " + e.getMessage());

                e.printStackTrace();
            }

            //  Glide.with(this).load("http://192.168.43.180/mycollege/data/student/profile/17cs030.png").into(iv_profile_pic);
        }


    }

}