package itp341.vong.mark.finalproject;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import itp341.vong.mark.finalproject.model.Post;


public class DetailFragment extends Fragment implements OnMapReadyCallback{


    private RequestQueue rq;

    // Base URL for geocode API
    private String baseURL = "https://maps.googleapis.com/maps/api/geocode/json?address=";
    private String address = "";
    // API key
    private String key = "&key=AIzaSyAywxzZyifGVbYawsupeJm2pixKGGJr5To";
    private String testURL = "http://www-bcf.usc.edu/~parke/itp341/a10/stocks.json";

    private double Latitude = 0.0;
    private double Longitude = 0.0;

    private Post currPost;

    private GoogleMap currMap;


    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment newInstance(Post currPost) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putSerializable("POST", currPost);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_detail, container, false);

        currPost = (Post)getArguments().getSerializable("POST");
        TextView helpName = (TextView)v.findViewById(R.id.helpName);
        TextView helpKind = (TextView)v.findViewById(R.id.helpKind);
        TextView helpDescription = (TextView)v.findViewById(R.id.helpDescription);
        TextView helpLocation = (TextView)v.findViewById(R.id.helpLocation);

        helpName.setText(currPost.getTitle());
        String kind = (currPost.isOffer() == true)? "Offer" : "Request";
        helpKind.setText(kind);
        helpDescription.setText(currPost.getDescription());
        helpLocation.setText(currPost.getLocation().toString());


        // Google Maps API - Create instance of map fragment to be embedded in current fragment
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapView);
        if(mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        return v;


    }

    // Make a call to Google Maps geocode web services
    private void requestJSON(String URL) {
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jar = (JSONArray)response.get("results");
                    JSONObject results = (JSONObject)jar.get(0);
                    JSONObject geometry = (JSONObject)results.get("geometry");
                    JSONObject location = (JSONObject)geometry.get("location");
                    Latitude = (double)location.get("lat");
                    Longitude = (double)location.get("lng");
                    Log.d("LAT::", ""+location.get("lat"));
                    Log.d("LON::", ""+location.get("lng"));
                    // Set the map to zoom in on the provided location of the post
                    if(Latitude != 0.0 && Longitude != 0.0) {
                        currMap.addMarker(new MarkerOptions().position(new LatLng(Latitude, Longitude)).title("Marker"));
                        currMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(Latitude, Longitude)));
                        currMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Latitude, Longitude), 15));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        rq.add(jor);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        currMap = googleMap;
        currMap.getUiSettings().setZoomControlsEnabled(true);
        rq = Volley.newRequestQueue(getActivity());
        String url = baseURL + currPost.getLocation().toJSONString() + key;
        requestJSON(url);
    }
}
