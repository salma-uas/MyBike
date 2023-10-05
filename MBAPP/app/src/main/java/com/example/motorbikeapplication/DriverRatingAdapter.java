package com.example.motorbikeapplication;

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

public class DriverRatingAdapter extends RecyclerView.Adapter<DriverRatingAdapter.BookingViewHolder> {

    private Context mCtx;

    //  SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(mCtx);
//    String ip = sharedPrefManager.getIP();

    private List<DriverRating> driverRatingList; // list of staff
    //private String imgUrl = IPAddress.ROOT_HTTP + ip +IPAddress.ROOT_URL_IMAGE;


    public DriverRatingAdapter(Context mCtx, List<DriverRating> driverRatingList) {
        this.mCtx = mCtx;
        this.driverRatingList = driverRatingList;
    }

    @Override
    public BookingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_layout_driver_rating, null);

        return new BookingViewHolder(view);

    }

    @Override
    public void onBindViewHolder(BookingViewHolder holder, int position) {
        final DriverRating driverRating = driverRatingList.get(position);
        //   final int id = staff.getId();
        holder.txtFullname.setText(driverRating.getTime_date());
        holder.txtPrice.setText(driverRating.getPrice());
        holder.txtPickupPoint.setText(driverRating.getLocation());
        holder.txtDestination.setText(driverRating.getDestination());
        holder.txtRating.setRating(Float.parseFloat(driverRating.getRating()));
        holder.txtFeedback.setText(driverRating.getFeedback());




        //   holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(staff.getImage(),null));
//
//
//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int booking_id = booking.getBooking_id();
//                String status = booking.getStatus();
//
//                Intent intent = new Intent(mCtx, BookingDetailsActivity.class);
//                intent.putExtra("booking_id", booking_id);
//                mCtx.startActivity(intent);
//
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return driverRatingList.size();
    }


    /*
     *     ViewHolder will hold the item
     */
    class BookingViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView txtFullname, txtPrice, txtStatus, txtPickupPoint, txtDestination,txtFeedback;
        CardView cardView;
        RatingBar txtRating;
        //  Button share;


        public BookingViewHolder(final View itemView) {
            super(itemView);

            txtFullname = itemView.findViewById(R.id.txtFullname);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtPickupPoint = itemView.findViewById(R.id.txtPickupPoint);
            txtDestination = itemView.findViewById(R.id.txtDestination);
            txtRating = itemView.findViewById(R.id.txtRating);
            txtFeedback = itemView.findViewById(R.id.txtFeedback);
            cardView = itemView.findViewById(R.id.cardView);

//            txtRating.setStepSize(1);

            //    share = itemView.findViewById(R.id.btnShare);



        }
    }

}
