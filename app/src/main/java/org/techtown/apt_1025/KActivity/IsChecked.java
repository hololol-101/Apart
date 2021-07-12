package org.techtown.apt_1025.KActivity;

import android.os.AsyncTask;

import org.techtown.apt_1025.KNetwork.HttpClient;
import org.techtown.apt_1025.KNetwork.HttpInterface;

import java.io.IOException;

import retrofit2.Call;

public class IsChecked extends AsyncTask<Integer, Void, Boolean> {
    @Override
    protected Boolean doInBackground(Integer... integers) {
        HttpInterface httpInterface = HttpClient.getClient().create(HttpInterface.class);
        Call<Boolean> call = httpInterface.isChecked(integers[0], integers[1]);
        boolean b=false;
        try {
            b=call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return b;
    }
}