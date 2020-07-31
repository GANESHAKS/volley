package com.pro.volley;

import android.content.Intent;

public  final class Intents {
    private static final String PACKAGE_NAME = "com.pro.volley";

    public static Intent createIntentLogin() {
        Intent intent = new Intent();
        intent.setClassName(
                PACKAGE_NAME,
                PACKAGE_NAME + ".api1.auth");
        return intent;
    }

}
