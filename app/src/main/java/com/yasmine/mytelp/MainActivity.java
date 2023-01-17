package com.yasmine.mytelp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ArrayList<YelpObject> restaurantList;
    YelpRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Spinner sortingSpinner = findViewById(R.id.spinner_id);
        SearchView searchBar = findViewById(R.id.searchView_id);

        //Set searchBar on Click Listener
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                //Getting API List of Restaurant:
                YelpAPI yelpAPI = new YelpClient().build();
                Call<YelpResponse> call = yelpAPI.getBusinesses("New York City", s);
                Callback<YelpResponse> callback = new Callback<YelpResponse>() {
                    @Override
                    public void onResponse(Call<YelpResponse> call, Response<YelpResponse> response) {
                        YelpResponse res = response.body();
                        adapter = new YelpRecyclerAdapter(getApplicationContext(), res.businesses);
                        RecyclerView recView = findViewById(R.id.recyclerView_id);
                        recView.setAdapter(adapter);
                        RecyclerView.LayoutManager layMan = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                        recView.setLayoutManager(layMan);
                    }
                    @Override
                    public void onFailure(Call<YelpResponse> call, Throwable t) {
                    }
                };
                call.enqueue(callback);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        //Set Spinner on Click Listener
        sortingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sortYelpAdapterData(adapter, adapterView.getSelectedItem().toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //OnClickListener for the drawer
        NavigationView navigationView = findViewById(R.id.navView_id);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                MenuItem searchMenu =  findViewById(R.id.searchMenu_id);
                searchMenu.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        Toast.makeText(MainActivity.this, "You clicked search", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });

                MenuItem favoriteMenu = findViewById(R.id.favoriteMenu_id);
                favoriteMenu.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        Toast.makeText(MainActivity.this, "You clicked faves", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });
                int id = item.getItemId();
                if(id == R.id.favoriteMenu_id) {
                    Toast.makeText(MainActivity.this, "You clicked faves", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.searchMenu_id){
                    Toast.makeText(MainActivity.this, "You clicked search", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void sortYelpAdapterData(YelpRecyclerAdapter adapter, String sortBy) {
        if (sortBy.equalsIgnoreCase("Price")){
            Collections.sort(adapter.data, Comparator.comparing(YelpResponse.Businesses::getPrice));
            adapter.notifyDataSetChanged();
        } else if (sortBy.equalsIgnoreCase("Rating")){
            Collections.sort(adapter.data, Comparator.comparing(YelpResponse.Businesses::getRating).reversed());
            adapter.notifyDataSetChanged();
        }
    }
}