package com.pro.volley.profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.pro.volley.R;
import com.pro.volley.SharedPreferencesHelper;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;


public class Profile extends AppCompatActivity {
    TextView tv_name, tv_usn, tv_email, tv_phno, tv_sem, tv_dept, tv_sec;
    //SharedPreferences sharedPreferences, sharedPreferences_profile;
    SharedPreferencesHelper sharedPreferencesHelper;
    SharedPreferencesHelper.ProfileSharedPreference sharedPreference_profile;
    SwipeRefreshLayout swipeRefreshLayout;
    String usn, name, email, phno, sem, sec, dept;
    static String imgurl = "null", imagedata = "null";
    ImageView iv_profile_pic;
    private Uri filePath, uri;
    Bitmap image;
    private static int SELECT_PHOTO = 1;
    Handler handler1, handler2, handler3, handler4;
    String downloaded_response = "null";
    Bitmap dp;
    String from_this_activity_called;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_profile);

        if(getIntent().hasExtra("from_this_activity_called")){

            from_this_activity_called = getIntent().getStringExtra("from_this_activity_called");
        }
        // sharedPreferences = this.getSharedPreferences("com.pro.volley", MODE_PRIVATE);
        //sharedPreferences_profile = this.getSharedPreferences("com.pro.volley.ProfileSharedPreference", MODE_PRIVATE);
        sharedPreferencesHelper = new SharedPreferencesHelper(getApplicationContext(), "com.pro.volley");

        sharedPreference_profile = sharedPreferencesHelper.new ProfileSharedPreference(getApplicationContext());


        iv_profile_pic = findViewById(R.id.iv_profile_pic);
        swipeRefreshLayout = findViewById(R.id.sr_profile);
        tv_email = findViewById(R.id.tv_profile_email);
        tv_name = findViewById(R.id.tv_profile_name);
        tv_usn = findViewById(R.id.tv_profile_usn);
        tv_phno = findViewById(R.id.tv_profile_phno);
        tv_dept = findViewById(R.id.tv_profile_dept);
        tv_sem = findViewById(R.id.tv_profile_sem);
        tv_sec = findViewById(R.id.tv_profile_sec);

        iv_profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  choose_file();
                Intent intent=new Intent(Profile.this,ProfilePicture.class);
              //  intent.putExtra("imagedata",imagedata);
               // intent.putExtra(Intent.EXTRA_TEXT,imagedata);


                startActivity(intent);
                finish();

            }
        });


        if (!sharedPreference_profile.getProfile_usn().equalsIgnoreCase("null")) {
            // Toast.makeText(getApplicationContext(),imagedata,Toast.LENGTH_SHORT).show();


            updateUserInterface();//update datadirectly

        } else {


            downloadDataFromServer();

        }
        handler1 = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                swipeRefreshLayout.setRefreshing(false);

                if (!downloaded_response.equalsIgnoreCase("null")) {
                    try {
                        JSONObject jsonObject = new JSONObject(downloaded_response);
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

                            Log.i("ProfileSharedPreference pic:", downloaded_response);
                            saveDataInPreference();

                            updateUserInterface();
                            download_profile_pic();

                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                            // return;


                        } else {
                            Toast.makeText(getApplicationContext(), jsonObject.getString("title") + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                            //return;

                        }

                    } catch (Exception e) {
                        Log.i("", "error " + e.getMessage());

                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "some unexpected Error", Toast.LENGTH_SHORT).show();
                }

            }


        };
        //DOWNLOAD DATA IN BACKGROUND caught error
        handler2 = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getApplicationContext(), "Error while downloadind ", Toast.LENGTH_SHORT);
            }
        };
        handler3 = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                iv_profile_pic.setImageBitmap(dp);

            }
        };
         handler4=new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                iv_profile_pic.setImageBitmap(image);

            }
        };
        refreshSwipe();

    }

    private void downloadDataFromServer() {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                StringRequest stringRequest = new StringRequest(Request.Method.POST, getResources().getString(R.string.url) + "profile/refreshprofile.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        requestQueue.getCache().clear();

                        downloaded_response = response;


                        handler1.sendEmptyMessage(0);


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        handler2.sendEmptyMessage(0);


                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("usn", sharedPreferencesHelper.getUsn());
                        return params;
                    }

                };

                requestQueue.add(stringRequest);


            }
        };
        Thread thread = new Thread(r);
        thread.start();


        return;
    }

    private void refreshSwipe() {

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                downloadDataFromServer();

            }
        });

    }

    private void saveDataInPreference() {
        sharedPreference_profile.setProfile_name(name);

        sharedPreference_profile.setProfile_usn(usn);
        sharedPreference_profile.setProfile_email(email);
        sharedPreference_profile.setProfile_phno(phno);
        sharedPreference_profile.setProfile_dept(dept);

        sharedPreference_profile.setProfile_sem(sem);

        sharedPreference_profile.setProfile_sec(sec);

        sharedPreference_profile.setProfile_picURL(imgurl);
        // sharedPreferences_profile.edit().putString("imagedata", imagedata).apply();


    }

    private void updateUserInterface() {
        imagedata = sharedPreference_profile.getGetProfile_imagedata();
        if (!imagedata.equalsIgnoreCase("null")) {


            Runnable r = new Runnable() {
                @Override
                public void run() {

                    if (!imagedata.equalsIgnoreCase("null")) {
                        image = stringToBitMap(imagedata);

                        //update ProfileSharedPreference pic
                    }

                    handler4.sendEmptyMessage(0);
                }

            };
            Thread t = new Thread(r);
            t.start();

            name = sharedPreference_profile.getProfile_name();
            usn = sharedPreference_profile.getProfile_usn();
            email = sharedPreference_profile.getProfile_email();
            phno = sharedPreference_profile.getProfile_phno();
            dept = sharedPreference_profile.getProfile_dept();
            sem = sharedPreference_profile.getProfile_sem();
            sec = sharedPreference_profile.getProfile_sec();
            imgurl = sharedPreference_profile.getProfile_picURL();


        } else {
            iv_profile_pic.setImageResource(R.drawable.dronecollege);
        }


        tv_name.setText(name);
        tv_usn.setText(usn);
        tv_email.setText(email);
        tv_phno.setText(phno);
        tv_dept.setText(dept);
        tv_sec.setText(sec);
        tv_sem.setText(sem);


    }


    private void download_profile_pic() {

        Runnable r = new Runnable() {
            @Override
            public void run() {
                if (!imgurl.equalsIgnoreCase("null")) {

                    try {

                        Glide.with(Profile.this) //1
                                // .load("http://192.168.43.180/mycollege/data/student/profile/17cs030.png")
                                .load(getResources().getString(R.string.urlroot) + imgurl)
                                .error(R.drawable.dronecollege)
                                .skipMemoryCache(false) //2
                                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                                .into(new CustomTarget<Drawable>() {
                                    @Override
                                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                                        dp = ((BitmapDrawable) resource).getBitmap();
                                        //Toast.makeText(MainActivity.this, "Saving Image...", Toast.LENGTH_SHORT).show();
                                        // BitMapToString(bitmap);
                                        //  iv_profile_pic.setImageBitmap(dp);
                                        imagedata = bitmapToString(dp);
                                        sharedPreference_profile.setGetProfile_imagedata(imagedata);
                                     //   sharedPreferences_profile.edit().putString("imagedata", imagedata).apply();

                                        handler3.sendEmptyMessage(0);


                                    }

                                    @Override
                                    public void onLoadCleared(@Nullable Drawable placeholder) {
                                        //  handler3.sendEmptyMessage(0);

                                    }

                                    @Override
                                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                                        super.onLoadFailed(errorDrawable);
                                        //handler3.sendEmptyMessage(0);

                                        // Toast.makeText(Profile.this, "Failed to Download Image! Please try again later.", Toast.LENGTH_SHORT).show();
                                    }
                                });

                        saveDataInPreference();
                    } catch (Exception e) {
                        Log.i("Error in download", "error " + e.getMessage());

                        e.printStackTrace();
                    }

                    //  Glide.with(this).load("http://192.168.43.180/mycollege/data/student/profile/17cs030.png").into(iv_profile_pic);
                }


            }
        };
        Thread t = new Thread(r);
        t.start();


    }



    private String bitmapToString(Bitmap bitmap) {


        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 10, byteArrayOutputStream);
        byte[] b = byteArrayOutputStream.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);

        return temp;
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
//        Intent i = new Intent(Profile.this, MainActivity.class);
//        startActivity(i);
//
  //if (from_this_activity_called.equalsIgnoreCase("from_this_activity_called"))
    //    finish();
        super.onBackPressed();

    }



}