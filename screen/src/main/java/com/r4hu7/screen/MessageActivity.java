package com.r4hu7.screen;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class MessageActivity extends AppCompatActivity {
    public static final String EXTRA_CIRCULAR_REVEAL_X = "_x";
    public static final String EXTRA_CIRCULAR_REVEAL_Y = "_y";
    private static final String MSG_LABEL = "MSG_LABEL";

    private String message;
    private String title;
    private boolean isDarkTheme;

    Toolbar tbPrimary;
    TextView tvTitle;
    TextView tvContent;
    AppCompatImageButton buttonCopy;
    AppCompatImageButton buttonShare;
    AppCompatCheckBox likeDislike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        isDarkTheme = Objects.requireNonNull(i.getExtras()).getBoolean(MessageScreen.EXTRA_DARK_THEME);
        if (isDarkTheme) {
            setTheme(R.style.screen_AppTheme_Dark);
            setContentView(R.layout.screen_activity_message_dark);
        } else {
            setTheme(R.style.screen_AppTheme);
            setContentView(R.layout.screen_activity_message);
        }

        tbPrimary = findViewById(R.id.screen_tbPrimary);
        tvTitle = findViewById(R.id.screen_tvTitle);
        tvContent = findViewById(R.id.screen_tvContent);
        buttonCopy = findViewById(R.id.screen_btn_copy);
        buttonShare = findViewById(R.id.screen_btn_share);
        likeDislike = findViewById(R.id.screen_cbFav);

        setSupportActionBar(tbPrimary);

        initExtras(i);
        initToolbar();
        initClickActions();

    }

    private void initClickActions() {
        buttonCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                copyMsg();
            }
        });
        buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareMsg();
            }
        });

        likeDislike.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.e("Sdsd", String.valueOf(b));
            }
        });
    }

    private void initExtras(Intent i) {
        message = Objects.requireNonNull(i.getExtras()).getString(MessageScreen.EXTRA_MSG);
        title = Objects.requireNonNull(i.getExtras()).getString(MessageScreen.EXTRA_TITLE);
        if (message != null)
            tvContent.setText(message);
    }

    private void initNavigationIcon() {
        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.screen_ic_arrow);
        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_IN);
        DrawableCompat.setTint(drawable, getResources().getColor(isDarkTheme ? R.color.shade0 : R.color.screen_bg));
        tbPrimary.setNavigationIcon(drawable);
        tbPrimary.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageActivity.super.onBackPressed();
            }
        });
    }

    private void initToolbar() {
        initNavigationIcon();
        if (title != null)
            tvTitle.setText(title);
    }

    private void shareMsg() {
        if (message == null)
            return;
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, message);
        startActivity(sharingIntent);
    }

    private void copyMsg() {
        if (message == null)
            return;
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(MSG_LABEL, message);
        if (clipboard != null) {
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getApplicationContext(), "Copied to clipboard", Toast.LENGTH_SHORT).show();
        }
    }
}
