package com.example.tripplanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

<<<<<<< HEAD
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

=======
>>>>>>> 4b82059739812c7f778750a80483f2a10769cf61
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends Activity {
    public final static String EXTRA_CITY = "com.example.tripplanner.CITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create a REST adapter which points to our API
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TripAdvisor tripAdvisor = retrofit.create(TripAdvisor.class);

        String API_KEY = getResources().getString(R.string.TripAdvisorKey);
        Call<Api.SearchResults> call = tripAdvisor.searchGeos("San%20Francisco", API_KEY);

        call.enqueue(new Callback<Api.SearchResults>() {
            @Override
            public void onResponse(Response<Api.SearchResults> response) {
                Api.SearchResults searchResults = response.body();
                System.out.println(searchResults.geos.get(0).location_string);
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    /**Called when User clicks button to enter City Name **/
    public void selectCity(View view){
        Intent intent = new Intent(this, DisplayTripAdvisorActivity.class);
        EditText cityEntry = (EditText) findViewById(R.id.editText);
        String city = cityEntry.getText().toString();
        city = changeString(city);
        intent.putExtra(EXTRA_CITY, city);
        System.out.println("OMG" + city);
        startActivity(intent);
    }
    public String changeString(String cityString){
        StringBuilder query = new StringBuilder();
        try {
            query.append(URLEncoder.encode(cityString, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        cityString = query.toString().toLowerCase().replace("+","%20");
        return cityString;
    }
}
