package com.example.adminapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

/*
RecyclerView.Adapter
RecyclerView.ViewHolder
 */

public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.BookingViewHolder> {

    private Context mCtx;

  //  SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(mCtx);
//    String ip = sharedPrefManager.getIP();

    private List<Route> bookingList; // list of staff
    //private String imgUrl = IPAddress.ROOT_HTTP + ip +IPAddress.ROOT_URL_IMAGE;


    public RouteAdapter(Context mCtx, List<Route> bookingList) {
        this.mCtx = mCtx;
        this.bookingList = bookingList;
    }

    @Override
    public BookingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_layout_feedback, null);

        return new BookingViewHolder(view);

    }

    @Override
    public void onBindViewHolder(BookingViewHolder holder, int position) {
        final Route booking = bookingList.get(position);
     //   final int id = staff.getId();
        holder.txtPrice.setText("TK " + booking.getPrice());
        holder.txtPickupPoint.setText(booking.getLocation());
        holder.txtDestination.setText(booking.getDestination());





     //   holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(staff.getImage(),null));


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int location_id = booking.getLocation_id();

                    Intent intent = new Intent(mCtx, RouteDetailsActivity.class);
                    intent.putExtra("location_id", location_id);
                    mCtx.startActivity(intent);

                }
        });

    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }


    /*
     *     ViewHolder will hold the item
     */
    class BookingViewHolder extends RecyclerView.ViewHolder{

        TextView  txtPrice, txtPickupPoint, txtDestination;
        CardView cardView;
        RatingBar  txtRating;
      //  Button share;


        public BookingViewHolder(final View itemView) {
            super(itemView);

            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtPickupPoint = itemView.findViewById(R.id.txtPickupPoint);
            txtDestination = itemView.findViewById(R.id.txtDestination);
            cardView = itemView.findViewById(R.id.cardView);



            //    share = itemView.findViewById(R.id.btnShare);



        }
    }

}
