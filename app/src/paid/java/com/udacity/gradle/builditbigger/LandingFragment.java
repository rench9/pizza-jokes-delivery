package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.r4hu7.screen.MessageScreen;
import com.udacity.gradle.builditbigger.data.remote.EndpointsAsyncTask;
import com.udacity.gradle.builditbigger.views.MyFab;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LandingFragment extends Fragment implements JokesNavigator, MessageScreen.LikeListner {

    @BindView(R.id.fab)
    MyFab fab;
    @BindView(R.id.ivLogo)
    ImageView ivLogo;


    public LandingFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_landing, container, false);
        ButterKnife.bind(this, v);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tellJoke();
            }
        });
        return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void tellJoke() {
        fab.startLoadingAnim();
        new EndpointsAsyncTask(this).execute();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MessageScreen.REQUEST_CODE)
            fab.startAnimation();
    }

    @Override
    public void onJokeReceived(String s) {
        final MessageScreen messageScreen = MessageScreen.getInstance();
        messageScreen.setTitle(getString(R.string.slice_tag));
        messageScreen.setMessage(s);
        messageScreen.enableDarkTheme();
        messageScreen.start(this, MessageScreen.REQUEST_CODE);
    }

    @Override
    public void onDataNotAvailable() {
        fab.stopLoadingAnim();
        Toast.makeText(getContext(), R.string.no_joke, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMsgLike() {

    }

    @Override
    public void onMsgDisLike() {

    }
}
