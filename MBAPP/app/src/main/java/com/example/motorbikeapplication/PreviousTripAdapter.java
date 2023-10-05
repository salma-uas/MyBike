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

/*
RecyclerView.Adapter
RecyclerView.ViewHolder
 */

public class PreviousTripAdapter extends RecyclerView.Adapter<PreviousTripAdapter.AppointmentViewHolder> {

    private Context mCtx;

  //  SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(mCtx);
//    String ip = sharedPrefManager.getIP();

    private List<Previous> previousList; // list of staff
    //private String imgUrl = IPAddress.ROOT_HTTP + ip +IPAddress.ROOT_URL_IMAGE;


    public PreviousTripAdapter(Context mCtx, List<Previous> previousList) {
        this.mCtx = mCtx;
        this.previousList = previousList;
    }

    @Override
    public AppointmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_layout, null);

        return new AppointmentViewHolder(view);

    }

    @Override
    public void onBindViewHolder(AppointmentViewHolder holder, int position) {
        final Previous previous = previousList.get(position);
     //   final int id = staff.getId();
        holder.txtDateTime.setText(previous.getTime_date());
        holder.txtPrice.setText(previous.getPrice());
        holder.txtStatus.setText(previous.getStatus());
        holder.txtPickupPoint.setText(previous.getLocation());
        holder.txtDestination.setText(previous.getDestination());
        holder.txtRating.setRating(Float.parseFloat(previous.getRating()));




     //   holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(staff.getImage(),null));


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int booking_id = previous.getBooking_id();


             //   Bundle dataBundle = new Bundle();
             //   dataBundle.putInt("id", ok);
                Intent intent = new Intent(mCtx, DigitalReceipt.class);
                intent.putExtra("booking_id", booking_id);
                mCtx.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return previousList.size();
    }


    /*
     *     ViewHolder will hold the item
     */
    class AppointmentViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView txtDateTime, txtPrice, txtStatus, txtPickupPoint, txtDestination;
        CardView cardView;
        RatingBar  txtRating;
      //  Button share;


        public AppointmentViewHolder(final View itemView) {
            super(itemView);

            txtDateTime = itemView.findViewById(R.id.txtDateTime);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            txtPickupPoint = itemView.findViewById(R.id.txtPickupPoint);
            txtDestination = itemView.findViewById(R.id.txtDestination);
            txtRating = itemView.findViewById(R.id.txtRating);
            cardView = itemView.findViewById(R.id.cardView);


            //    share = itemView.findViewById(R.id.btnShare);



        }
    }

}
