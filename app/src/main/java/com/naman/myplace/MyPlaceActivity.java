package com.naman.myplace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.naman.myplace.Adapters.AdapterPlaces;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class MyPlaceActivity extends AppCompatActivity {

    private RecyclerView gridRv;
    private AdapterPlaces adapterPlaces;
    private List<ModelPlace> modelPlaceList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_place);

        modelPlaceList=new ArrayList<>();

        gridRv = findViewById(R.id.placerRV);
        gridRv.setHasFixedSize(true);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(MyPlaceActivity.this, 2);
        gridRv.setDrawingCacheEnabled(true);
        gridRv.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW);
        linearLayoutManager.setItemPrefetchEnabled(true);
        linearLayoutManager.setInitialPrefetchItemCount(20);
        gridRv.setLayoutManager(linearLayoutManager);

        getPlaceDetails();



    }


    private void getPlaceDetails() {

        // Initialize a new RequestQueue instance
        RequestQueue requestQueue = Volley.newRequestQueue(MyPlaceActivity.this);

        String url = "https://s3-ap-southeast-1.amazonaws.com/he-public-data/placesofinterest39c1c48.json";
        // Initialize a new JsonArrayRequest instance
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            // Loop through the array elements
                            for (int i = 0; i < response.length(); i++) {
                                // Get current json object
                                JSONObject object = response.getJSONObject(i);

                                ModelPlace modelPlace = new ModelPlace();
                                modelPlace.name = object.getString("name");
                                modelPlace.id = object.getString("id");
                                modelPlace.image = object.getString("image");
                                modelPlace.longitude = object.getString("longitude");
                                modelPlace.latitude = object.getString("latitude");
                                modelPlace.address = object.getString("address");


                                modelPlaceList.add(modelPlace);

                            }
                             adapterPlaces = new AdapterPlaces(MyPlaceActivity.this, modelPlaceList);
                            adapterPlaces.setHasStableIds(true);
                            gridRv.setAdapter(adapterPlaces);

                            Log.d(TAG, "onResponse: " + modelPlaceList.size());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        // Add JsonArrayRequest to the RequestQueue
        requestQueue.add(jsonArrayRequest);
    }

}