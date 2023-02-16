package com.myslsa.vidhika;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FinishedGrievanceAdapter extends RecyclerView.Adapter<FinishedGrievanceAdapter.ViewHolder>{

    private List<Model> listItems;
    private Context context;
    private ProgressDialog dialog;


    public FinishedGrievanceAdapter(List<Model> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView id;
        public TextView uid;
        public TextView name;
        public TextView address;
        public TextView details;

        public TextView phone;
        public CardView card_view;

        public ViewHolder(View itemView ) {
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.id);
            uid = (TextView) itemView.findViewById(R.id.uid);
            name = (TextView) itemView.findViewById(R.id.name);
            address = (TextView) itemView.findViewById(R.id.address);
            phone = (TextView) itemView.findViewById(R.id.phone);
            details = (TextView) itemView.findViewById(R.id.details);
            card_view = (CardView) itemView.findViewById(R.id.card_view);

        }


    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Model listItem = listItems.get(position);
        holder.id.setText(listItem.getId());
        holder.uid.setText(listItem.getUId());
        holder.name.setText(listItem.getName());
        holder.address.setText(listItem.getAddress());
        holder.phone.setText(listItem.getPhone());

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Intent intent;
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                final ProgressDialog dialog = new ProgressDialog(view.getContext());
                dialog.setMessage("Loading Withdrawn Grievance");
                final CharSequence[] dialogitem = {"View Grievance","Accept Solution","Resubmit Grievance"};
                builder.setTitle(listItem.getName());
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0 :
                                Intent intent = new Intent(view.getContext(), DetailData.class);
                                intent.putExtra("id", listItem.getId());
                                intent.putExtra("uid",listItem.getUId());
                                intent.putExtra("name",listItem.getName());
                                intent.putExtra("address",listItem.getAddress());
                                intent.putExtra("phone", listItem.getPhone());
                                intent.putExtra("details", listItem.getDetails());
                                intent.putExtra("status", listItem.getStatus());
                                view.getContext().startActivity(intent);
                                break;
                                // commented by Ankit at 14-09-2020 16:57
                            case 1 :

                                AlertDialog.Builder builderDel = new AlertDialog.Builder(view.getContext());
                                builderDel.setTitle(listItem.getName());
                                builderDel.setMessage("Are You Sure, You Want to Accept Solution?");
                                builderDel.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialog.show();

                                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_ACCEPT, new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                dialog.hide();
                                                dialog.dismiss();
                                                Toast.makeText(view.getContext(),"Solution Accepted by User "+ listItem.getName(), Toast.LENGTH_LONG).show();
                                                ListActivity.ma.refresh_list();

                                            }
                                        }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                dialog.hide();
                                                dialog.dismiss();
                                            }
                                        }){
                                            protected HashMap<String, String> getParams() throws AuthFailureError {
                                                Map<String, String> params= new HashMap<>();
                                                params.put("id",listItem.getId());
                                                return (HashMap<String, String>) params;

                                            }
                                        };
                                        RequestHandler.getInstance(view.getContext()).addToRequestQueue(stringRequest);
                                        dialogInterface.dismiss();
                                    }
                                });

                                builderDel.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override

                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                });


                                builderDel.create().show();
                                break;

                            case 2 :

                                Intent intent2 = new Intent(view.getContext(), EditActivity.class);
                                intent2.putExtra("id", listItem.getId());
                                intent2.putExtra("uid",listItem.getUId());
                                intent2.putExtra("name",listItem.getName());
                                intent2.putExtra("address",listItem.getAddress());
                                intent2.putExtra("phone", listItem.getPhone());
                                intent2.putExtra("details", listItem.getDetails());
                                intent2.putExtra("status", listItem.getStatus());
                                view.getContext().startActivity(intent2);
                                break;

                        }
                    }
                });

                builder.create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }
}