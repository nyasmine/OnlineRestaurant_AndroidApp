package com.yasmine.mytelp;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class YelpRecyclerAdapter extends RecyclerView.Adapter<YelpRecyclerAdapter.ViewHolder> {
    private Context cardViewContext;
    ArrayList<YelpResponse.Businesses> data; //Store the data in the adapter: name, description etc.

    public YelpRecyclerAdapter(Context context, ArrayList<YelpResponse.Businesses> data){
        this.data = data;
        cardViewContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.yelp_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.name.setText(data.get(position).name);
        holder.rating.setRating(data.get(position).rating);
        Glide.with(cardViewContext).load(data.get(position).imageYelpUrl).into(holder.restImage);
        holder.price.setText(data.get(position).price);
        holder.category.setText(categoryString(position));
        holder.phoneNumber.setText(data.get(position).phone);
        String address = data.get(position).location.address1+","+data.get(position).location.city+","+data.get(position).location.state;
        holder.address.setText(address);
    }

    private String categoryString (int position){
        ArrayList<YelpResponse.Businesses.Categories> categories = data.get(position).categories;
        String retCat = "";
        for (int i = 0; i < categories.size(); i++){
          retCat += categories.get(i).categoryTitle + " ";
        }
        return retCat;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView restImage;
        TextView name;
        RatingBar rating;
        TextView price;
        TextView category;
        TextView phoneNumber;
        TextView address;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            restImage = itemView.findViewById(R.id.imageView_id);
            name = itemView.findViewById(R.id.restaurantName_id);
            rating = itemView.findViewById(R.id.ratingBar_id);
            price = itemView.findViewById(R.id.price_id);
            category = itemView.findViewById(R.id.category_id);
            phoneNumber = itemView.findViewById(R.id.phnum_id);
            address = itemView.findViewById(R.id.address_id);
            cardView = itemView.findViewById(R.id.cardView_id);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final String DB_NAME = "My Yelp Databse";
                    final String FAVORITES = "Fave Rest";
                    YelpDatabaseHelper databaseHelper = new YelpDatabaseHelper(cardView.getContext(), DB_NAME, null, 1);
                    AlertDialog.Builder builder = new AlertDialog.Builder(cardView.getContext())
                            .setTitle("Add to favorite?")
                            .setMessage("Do you want to add this item to favorite?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                        ContentValues favResto = new ContentValues();
                                        favResto.put("NAME", "Restaurant Name");
                                        favResto.put("CATEGORY", "Restaurant category");
                                        favResto.put("PRICE", 80.5);
                                        favResto.put("RATING", 4.5);
                                        favResto.put("PHONE_NUMBER", 5143224);
                                        favResto.put("LOCATION", "Restaurant Location");
                                        favResto.put("IMAGE", "Restaurant url");
                                        databaseHelper.getWritableDatabase().insert(FAVORITES, null, favResto);
                                        Toast.makeText(cardView.getContext(), "Added to favorite", Toast.LENGTH_SHORT).show();

                                        //Cursor to implement when I click Favorites from the drawer:
                                    Cursor cursor = databaseHelper.getReadableDatabase()
                                            .query(FAVORITES, new String[]{"NAME", "CATEGORY", "PRICE", "RATING", "PHONE_NUMBER", "LOCATION", "IMAGE"},
                                                    null, null, null, null,null, null);
                                    cursor.moveToFirst();
                                    String name = cursor.getString(0);
                                    String catergory = cursor.getString(1);
                                    Float price = cursor.getFloat(cursor.getColumnIndexOrThrow("PRICE"));
                                    Float rating = cursor.getFloat(cursor.getColumnIndexOrThrow("RATING"));
                                    String phoneNumber = cursor.getString(4);
                                    String location = cursor.getString(5);
                                    String image = cursor.getString(6);
//                                    tv.name.setText(rating+"");
//                                    tv.rating.setText(name);
//                                    Glide.with(cardView.getContext().load(image).into(restImage));


                                }
                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(cardView.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                                }
                            });
                    builder.create().show();
                }
            });
        }
    }
}
