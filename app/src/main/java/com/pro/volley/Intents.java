package com.pro.volley;

import android.content.Context;
import android.content.Intent;

import com.pro.volley.auth.LoginActivity;

public  final class Intents {
    private static final String PACKAGE_NAME = "com.pro.volley";

    public static Intent createIntentLogin() {
        Intent intent = new Intent();
        intent.setClassName(
                PACKAGE_NAME,
                PACKAGE_NAME + ".auth.LoginActivity");
        return intent;
    }
    public static Intent createIntentRegister() {
        Intent intent = new Intent();
        intent.setClassName(
                PACKAGE_NAME,
                PACKAGE_NAME + ".auth.register");
        return intent;
    }

}
