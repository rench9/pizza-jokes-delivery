package com.udacity.gradle.builditbigger;

public interface JokesNavigator {
    void onJokeReceived(String s);

    void onDataNotAvailable();
}
