package com.example.driverapplication;

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

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {

    private Context mCtx;

  //  SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(mCtx);
//    String ip = sharedPrefManager.getIP();

    private List<Booking> bookingList; // list of staff
    //private String imgUrl = IPAddress.ROOT_HTTP + ip +IPAddress.ROOT_URL_IMAGE;


    public BookingAdapter(Context mCtx, List<Booking> bookingList) {
        this.mCtx = mCtx;
        this.bookingList = bookingList;
    }

    @Override
    public BookingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_layout, null);

        return new BookingViewHolder(view);

    }

    @Override
    public void onBindViewHolder(BookingViewHolder holder, int position) {
        final Booking booking = bookingList.get(position);
     //   final int id = staff.getId();
        holder.txtDateTime.setText(booking.getTime_date());
        holder.txtPrice.setText(booking.getPrice());
        holder.txtStatus.setText(booking.getStatus());
        holder.txtPickupPoint.setText(booking.getLocation());
        holder.txtDestination.setText(booking.getDestination());
        holder.txtRating.setRating(Float.parseFloat(booking.getRating()));




     //   holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(staff.getImage(),null));


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int booking_id = booking.getBooking_id();
                String status = booking.getStatus();

                    Intent intent = new Intent(mCtx, BookingDetailsActivity.class);
                    intent.putExtra("booking_id", booking_id);
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

        ImageView imageView;
        TextView txtDateTime, txtPrice, txtStatus, txtPickupPoint, txtDestination;
        CardView cardView;
        RatingBar  txtRating;
      //  Button share;


        public BookingViewHolder(final View itemView) {
            super(itemView);

            txtDateTime = itemView.findViewById(R.id.txtDateTime);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            txtPickupPoint = itemView.findViewById(R.id.txtPickupPoint);
            txtDestination = itemView.findViewById(R.id.txtDestination);
            txtRating = itemView.findViewById(R.id.txtRating);
            cardView = itemView.findViewById(R.id.cardView);

//            txtRating.setStepSize(1);

            //    share = itemView.findViewById(R.id.btnShare);



        }
    }

}
