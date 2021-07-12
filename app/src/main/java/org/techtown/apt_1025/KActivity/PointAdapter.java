package org.techtown.apt_1025.KActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.naver.maps.map.overlay.InfoWindow;

import org.techtown.apt_1025.KNetwork.HttpClient;
import org.techtown.apt_1025.KNetwork.HttpInterface;
import org.techtown.apt_1025.KNetwork.Serialpplyear;
import org.techtown.apt_1025.R;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;

import static android.content.ContentValues.TAG;

public class PointAdapter extends InfoWindow.DefaultViewAdapter
{
    private final Context mContext;
    private final ViewGroup mParent;
    private final String aptname;

    public PointAdapter(@NonNull Context context, String aptname, ViewGroup parent)
    {
        super(context);
        mContext = context;
        this.aptname = aptname;
        mParent = parent;
    }

    @NonNull
    @Override
    protected View getContentView(@NonNull InfoWindow infoWindow)
    {

        View view = (View) LayoutInflater.from(mContext).inflate(R.layout.item_point, mParent, false);

        TextView txtTitle = (TextView) view.findViewById(R.id.txttitle);
        TextView buildyear = (TextView) view.findViewById(R.id.buildyear);
        TextView people = (TextView) view.findViewById(R.id.people);

        try {
            Serialpplyear serialpplyear = new Getpplyear().execute(aptname).get();
            txtTitle.setText(aptname);
            buildyear.setText("준공년도 : "+serialpplyear.year_built);
            people.setText(serialpplyear.scale);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return view;
    }

    @SuppressLint("StaticFieldLeak")
    private class Getpplyear extends AsyncTask<String, Void, Serialpplyear> {
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Serialpplyear doInBackground(String... strings) {
            HttpInterface httpInterface = HttpClient.getClient().create(HttpInterface.class);
            Call<Serialpplyear> call = httpInterface.getpplyear(strings[0]);
            Serialpplyear response = null;
            try {
                response = call.execute().body();
            } catch (Exception e) {
                Log.e(TAG, Objects.requireNonNull(e.getMessage()));
            }
            return response;

        }
    }
}