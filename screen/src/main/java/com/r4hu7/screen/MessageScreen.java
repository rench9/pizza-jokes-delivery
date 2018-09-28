package com.r4hu7.screen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.Toast;

public class MessageScreen {

    public interface LikeListner {
        void onMsgLike();

        void onMsgDisLike();
    }

    public static final int REQUEST_CODE = 1001;
    public static final int REQUEST_OK = 1002;
    public static final int RESULT_ERROR = 1003;

    private static final String EXTRA_PREFIX = BuildConfig.APPLICATION_ID;
    static final String EXTRA_MSG = EXTRA_PREFIX + ".MSG";
    static final String EXTRA_TITLE = EXTRA_PREFIX + ".TITLE";
    static final String EXTRA_DARK_THEME = EXTRA_PREFIX + ".THEME";

    private Intent screenIntent;
    private Bundle screenBundle;


    private MessageScreen(@NonNull String joke) {
        screenIntent = new Intent();
        screenBundle = new Bundle();
        setMessage(joke);
        screenBundle.putBoolean(EXTRA_DARK_THEME, false);
    }

    private MessageScreen() {
        screenIntent = new Intent();
        screenBundle = new Bundle();
        screenBundle.putBoolean(EXTRA_DARK_THEME, false);
    }

    public static MessageScreen getInstance(String joke) {
        return new MessageScreen(joke);
    }

    public static MessageScreen getInstance() {
        return new MessageScreen();
    }


    /**
     * Get Intent to start {@link MessageActivity}
     *
     * @return Intent for {@link MessageActivity}
     */
    private Intent getIntent(@NonNull Context context) {
        screenIntent.setClass(context, MessageActivity.class);
        screenIntent.putExtras(screenBundle);
        return screenIntent;
    }

    /**
     * Send the Screen Intent from an Activity with a custom request code
     *
     * @param fragment    Fragment to receive result
     * @param requestCode requestCode for result
     */
    public void start(@NonNull Fragment fragment, int requestCode) {
        if (isMessageSet(fragment.getContext()))
            fragment.startActivityForResult(getIntent(fragment.getContext()), requestCode);
    }

    public void start(@NonNull Activity activity, int requestCode) {
        if (isMessageSet(activity.getApplicationContext()))
            activity.startActivityForResult(getIntent(activity), requestCode);
    }

    public void start(@NonNull Activity activity) {
        if (isMessageSet(activity.getApplicationContext()))
            activity.startActivity(getIntent(activity));
    }

    public void enableDarkTheme() {
        screenBundle.putBoolean(EXTRA_DARK_THEME, true);
    }

    public void setMessage(String message) {
        screenBundle.putString(EXTRA_MSG, message);
    }

    public void setTitle(String title) {
        screenBundle.putString(EXTRA_TITLE, title);
    }

    private boolean isMessageSet(Context context) {
        if (screenBundle.getString(EXTRA_MSG) == null) {
            Toast.makeText(context, "No message set", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
