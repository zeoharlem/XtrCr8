package com.example.xtremecardz.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.xtremecardz.DashboardActivity;

import java.util.HashMap;

/**
 * Created by Theophilus on 7/27/2017.
 */

public class SessionManager {
    private static final String ROLE = "user";
    private static final String USER_ID = "userid";
    private static final String TOKEN_F = "token";
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "gmanzo";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";

    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";

    public static final String DEVICE   = "device";

    public static String walletCode = null;


    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, context.MODE_PRIVATE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(String name, String email, String userid, String token, String role, String device){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_NAME, name);

        // Storing email in pref
        editor.putString(KEY_EMAIL, email);
        
        editor.putString(USER_ID, userid);
        
        editor.putString(TOKEN_F, token);
        
        editor.putString(ROLE, role);

        editor.putString(DEVICE, device);

        editor.putString("walletCode", walletCode);

        // commit changes
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!isLoggedIn() && !checkToken()){
            // user is not logged in redirect him to Login WalletActivity
            Intent i = new Intent(_context, DashboardActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new WalletActivity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            // Staring Login WalletActivity
            _context.startActivity(i);
        }

    }

    private void showAlertFailDialogBox(){

    }

    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));

        // user email id
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        user.put(TOKEN_F, pref.getString(TOKEN_F, null));

        user.put(DEVICE, pref.getString(DEVICE, null));

        user.put(USER_ID, pref.getString(USER_ID, null));
        // return user
        return user;
    }

    public boolean checkToken(){
        HashMap<String, String> user = getUserDetails();
        String token    = user.get(SessionManager.TOKEN_F);
        return token.length() > 1 ? true : false;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.putBoolean(IS_LOGIN, false);
        editor.putString(TOKEN_F, "");
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing WalletActivity
        Intent i = new Intent(_context, DashboardActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new WalletActivity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login WalletActivity
        _context.startActivity(i);
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}
