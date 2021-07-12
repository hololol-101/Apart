package org.techtown.apt_1025.KActivity;

import android.os.AsyncTask;
import android.util.Log;

import org.techtown.apt_1025.KNetwork.HttpClient;
import org.techtown.apt_1025.KNetwork.HttpInterface;

import java.io.IOException;

import retrofit2.Call;

public class ChangeLikes extends AsyncTask<String, Void, Void> {
    @Override
    protected Void doInBackground(String... strings) {
        HttpInterface httpInterface = HttpClient.getClient().create(HttpInterface.class);
        Call<Void> call = httpInterface.changeLikes(Integer.parseInt(strings[0]), Integer.parseInt(strings[1]));
        try {
        call.execute();
        } catch (IOException e) {
        e.printStackTrace();
        }
        return null;
    }
}
class InsertStore extends AsyncTask<String, Void, Void> {

    @Override
    protected Void doInBackground(String... strings) {
        HttpInterface httpInterface = HttpClient.getClient().create(HttpInterface.class);
        Log.d("hell", strings[2]);
        Call<Void> call = httpInterface.insertStore(Integer.parseInt(strings[0]), Integer.parseInt(strings[1]), strings[2]);
        try {
            call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
