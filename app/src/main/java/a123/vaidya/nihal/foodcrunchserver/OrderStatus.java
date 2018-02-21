package a123.vaidya.nihal.foodcrunchserver;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jaredrummler.materialspinner.MaterialSpinner;

import a123.vaidya.nihal.foodcrunchserver.Common.Common;
import a123.vaidya.nihal.foodcrunchserver.Interface.ItemClickListener;
import a123.vaidya.nihal.foodcrunchserver.Model.MyResponse;
import a123.vaidya.nihal.foodcrunchserver.Model.Notification;
import a123.vaidya.nihal.foodcrunchserver.Model.Request;
import a123.vaidya.nihal.foodcrunchserver.Model.Sender;
import a123.vaidya.nihal.foodcrunchserver.Model.Token;
import a123.vaidya.nihal.foodcrunchserver.Remote.APIService;
import a123.vaidya.nihal.foodcrunchserver.ViewHolder.OrderViewHolder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderStatus extends AppCompatActivity {

    public RecyclerView recyclerView;
    public  RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Request,OrderViewHolder> adapter;
    FirebaseDatabase db;
    DatabaseReference requests;
    MaterialSpinner spinner;
    APIService mAPIService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);
        //service
        mAPIService = Common.getFCMClient();

        //firebase
        db = FirebaseDatabase.getInstance();
        requests = db.getReference("Requests");

        recyclerView = (RecyclerView)findViewById(R.id.listOrders);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        loadOrders(Common.currentUser.getPhone());


    }


    private void loadOrders(String phone) {
        adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(
                Request.class, R.layout.order_layout, OrderViewHolder.class,
                requests
                //.orderByChild("phone").equalTo(phone)
        ) {
            @Override
            protected void populateViewHolder(OrderViewHolder viewHolder, final Request model, final int position) {
                viewHolder.txtOrderId.setText("Order Id : "+adapter.getRef(position).getKey());
                viewHolder.txtOrderStatus.setText("Status : "+Common.convertCodeToStatus(model.getStatus()));
                viewHolder.txtOrderAddress.setText("Address : "+model.getAddress());
                viewHolder.txtOrderPhonw.setText("Phone No : "+model.getPhone());
                viewHolder.txtOrderComment.setText("Comment : "+model.getComment());

                //event button
                viewHolder.btnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showUpdateDialog(adapter.getRef(position).getKey(),adapter.getItem(position));
                    }
                });
                viewHolder.btnRemove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteOrder(adapter.getRef(position).getKey(),adapter.getItem(position));
                        adapter.notifyDataSetChanged();//update view
                    }
                });
                viewHolder.btnDetails.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent orderDetail = new Intent(OrderStatus.this,OrderDetail.class);
                        Common.currentRequest = model;
                        orderDetail.putExtra("OrderId",adapter.getRef(position).getKey());
                        startActivity(orderDetail);
                    }
                });
                viewHolder.btnDirections.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent trackingOrder = new Intent(OrderStatus.this,TrackingOrder.class);
                        Common.currentRequest = model;
                        startActivity(trackingOrder);
                    }
                });
            }

        };
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle().equals(Common.UPDATE))
            showUpdateDialog(adapter.getRef(item.getOrder()).getKey(),adapter.getItem(item.getOrder()));
        else
        if(item.getTitle().equals(Common.DELETE))
        {
            deleteOrder(adapter.getRef(item.getOrder()).getKey(),adapter.getItem(item.getOrder()));
        }
        return super.onContextItemSelected(item);
    }


    private void deleteOrder(String key, final Request item) {
        final String localKey = key;
        //final Request item = null;
        requests.child(key).removeValue();
        sendorderstatustoUSER(localKey,item);
        Toast.makeText(OrderStatus.this,"   The order was deleted   ",Toast.LENGTH_LONG).show();

    }

    private void showUpdateDialog(String key, final Request item) {

        final AlertDialog.Builder alertDialog  = new AlertDialog.Builder(OrderStatus.this);
        alertDialog.setTitle("Update order");
        alertDialog.setMessage("Please choose status");
        final LayoutInflater inflater = this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.update_order_layout,null);
        spinner = (MaterialSpinner)view.findViewById(R.id.statusspinner);
        spinner.setItems("Placed","On the way","Shipped!!");
        alertDialog.setView(view);
        final String localKey = key;
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                item.setStatus(String.valueOf(spinner.getSelectedIndex()));
                requests.child(localKey).setValue(item);
                adapter.notifyDataSetChanged();//to update item size
                sendorderstatustoUSER(localKey,item);
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.show();
    }

    private void sendorderstatustoUSER(final String key, final Request item) {
        DatabaseReference tokens = db.getReference("Tokens");
        tokens.orderByKey().equalTo(item.getPhone())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot:dataSnapshot.getChildren())
                        {
                            Token token = postSnapshot.getValue(Token.class);
                            //raw payload for notification
                            Notification notification = new Notification("Food-Crunch","Your order"+key+"was updated");
                            Sender content = new Sender(token.getToken(),notification);
                            mAPIService.sendNotification(content)
                                    .enqueue(new Callback<MyResponse>() {
                                        @Override
                                        public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                            if(response.code() == 200){
                                                if(response.body().success ==1)
                                                {
                                                    Toast.makeText(OrderStatus.this,"   Your order was updated   ",Toast.LENGTH_LONG).show();
                                                }else{
                                                    Toast.makeText(OrderStatus.this,"Your order was updated...\n\nFailed to send notification",Toast.LENGTH_LONG).show();
                                                }}
                                        }

                                        @Override
                                        public void onFailure(Call<MyResponse> call, Throwable t) {
                                            Toast.makeText(OrderStatus.this,"Background Notification Refresh",Toast.LENGTH_LONG).show();
                                        }
                                    });
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
}
