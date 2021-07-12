package org.techtown.apt_1025.KActivity;

import android.os.AsyncTask;

import org.techtown.apt_1025.KNetwork.HttpClient;
import org.techtown.apt_1025.KNetwork.HttpInterface;

import java.io.IOException;

import retrofit2.Call;

public class DeleteStore extends AsyncTask<String, Void, Void> {

    @Override
    protected Void doInBackground(String... strings) {
        HttpInterface httpInterface = HttpClient.getClient().create(HttpInterface.class);
        Call<Void> call = httpInterface.deleteStore(Integer.parseInt(strings[0]), Integer.parseInt(strings[1]), strings[2]);
        try {
            call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
