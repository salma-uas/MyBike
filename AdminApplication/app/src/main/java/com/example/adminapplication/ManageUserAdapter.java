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

import com.bumptech.glide.Glide;

import java.util.List;

public class ManageUserAdapter extends RecyclerView.Adapter<ManageUserAdapter.AppointmentViewHolder> {

    private Context mCtx;

    //  SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(mCtx);
//    String ip = sharedPrefManager.getIP();

    private List<Manage_User> manageUserList; // list of staff
    private String imgUrlPassenger = Constants.ROOT_URL_IMAGE;


    public ManageUserAdapter(Context mCtx, List<Manage_User> manageUserList) {
        this.mCtx = mCtx;
        this.manageUserList = manageUserList;
    }

    @Override
    public AppointmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_layout_users, null);

        return new AppointmentViewHolder(view);

    }

    @Override
    public void onBindViewHolder(AppointmentViewHolder holder, int position) {
        final Manage_User manageUser = manageUserList.get(position);
        //   final int id = staff.getId();
        holder.txtFullname.setText(manageUser.getFullname());
        holder.txtEmail.setText(manageUser.getEmail());
        holder.txtHpno.setText(manageUser.getHpno());
        holder.txtGender.setText(manageUser.getGender());
        Glide.with(mCtx)
                .load(imgUrlPassenger + manageUser.getPic())
                .into(holder.imgUser);


        //   holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(staff.getImage(),null));


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int user_id = manageUser.getId();
                String type = manageUser.getType();


                if(type.equals("passenger")){
                    Intent intent = new Intent(mCtx, MainUserInfoActivity.class);
                    intent.putExtra("user_id", user_id);
                    mCtx.startActivity(intent);

                }else if(type.equals("driver")){
                    Intent intent = new Intent(mCtx, DriverUserInfoActivity.class);
                    intent.putExtra("user_id", user_id);
                    mCtx.startActivity(intent);
                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return manageUserList.size();
    }


    /*
     *     ViewHolder will hold the item
     */
    class AppointmentViewHolder extends RecyclerView.ViewHolder{

        ImageView imgUser;
        TextView txtFullname, txtEmail, txtHpno, txtGender;
        CardView cardView;
        //  Button share;


        public AppointmentViewHolder(final View itemView) {
            super(itemView);

            txtFullname = itemView.findViewById(R.id.txtFullname);
            txtEmail = itemView.findViewById(R.id.txtEmail);
            txtHpno = itemView.findViewById(R.id.txtHpno);
            txtGender = itemView.findViewById(R.id.txtGender);
            imgUser = itemView.findViewById(R.id.imgUser);
            cardView = itemView.findViewById(R.id.cardView);
            //    share = itemView.findViewById(R.id.btnShare);



        }
    }

}
