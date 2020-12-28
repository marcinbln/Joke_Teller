package com.udacity.gradle.builditbigger.paid;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.VisibleForTesting;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.androidjoker.JokerActivity;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.AppExecutors;
import com.udacity.gradle.builditbigger.MainActivity;
import com.udacity.gradle.builditbigger.R;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;
import java.util.Random;

import static com.example.androidjoker.JokerActivity.INTENT_JOKE_EXTRA;

public class MainActivityFragment extends Fragment {

    private MutableLiveData<String> joke = new MutableLiveData<>();
    private ProgressBar spinner;

    public MainActivityFragment() {
    }

    private static int getRandomNumber() {
        int min = 0;
        int max = 9;

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        spinner = root.findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);

        Button button = root.findViewById(R.id.tellJoke);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setVisibility(View.VISIBLE);
                setIdle(false);
                getJokeInBackground();
            }
        });

        //set observer on a MutableLiveData<String> joke
        jokeObserver();
        return root;
    }

    private void getJokeInBackground() {

        // String joke = new JavaJoker().getJavaJoke();
        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                joke.postValue(getJokeFromEndpoint(getRandomNumber()));
            }
        });
    }

    // Get a joke from an endpoint
    private String getJokeFromEndpoint(int jokeNumber) {

        MyApi myApiService;

        MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                new AndroidJsonFactory(), null)
                // options for running against local devappserver
                // - 10.0.2.2 is localhost's IP address in Android emulator
                // - turn off compression when running against local devappserver
                .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                    @Override
                    public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                        abstractGoogleClientRequest.setDisableGZipContent(true);
                    }
                });
        myApiService = builder.build();

        try {

            return myApiService.getJoke(jokeNumber).execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    // Set observer on a MutableLiveData<String> joke
    // Once joke is retrieved from endpoint JokerActivity can be opened.
    private void jokeObserver() {

        joke.observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(String s) {

                        setIdle(true);
                        openJokeActivity(joke.getValue());
                        spinner.setVisibility(View.GONE);

                    }
                }
        );
    }

    private void openJokeActivity(String joke) {
        Intent intent = new Intent(getContext(), JokerActivity.class);
        intent.putExtra(INTENT_JOKE_EXTRA, joke);
        startActivity(intent);
    }

    @VisibleForTesting
    public void setIdle(boolean idle) {
        {
            ((MainActivity) getActivity()).setIdle(idle);

        }
    }

}
