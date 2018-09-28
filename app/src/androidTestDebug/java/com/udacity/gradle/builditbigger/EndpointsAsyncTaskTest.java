package com.udacity.gradle.builditbigger;

import android.util.Log;

import com.udacity.gradle.builditbigger.data.remote.EndpointsAsyncTask;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class EndpointsAsyncTaskTest {
    @Test
    public void testAsyncTask() throws InterruptedException {
        assertTrue(true);
        final CountDownLatch latch = new CountDownLatch(1);
        EndpointsAsyncTask testTask = new EndpointsAsyncTask(new JokesNavigator() {
            @Override
            public void onJokeReceived(String s) {

            }

            @Override
            public void onDataNotAvailable() {

            }
        }) {
            @Override
            protected void onPostExecute(String result) {
                assertNotNull(result);
                if (result != null) {
                    Log.i("EndpointsAsyncTaskTest - Response", result);
                    assertTrue(result.length() > 0);
                }
                latch.countDown();
            }
        };
        testTask.execute();
        latch.await();
    }
}