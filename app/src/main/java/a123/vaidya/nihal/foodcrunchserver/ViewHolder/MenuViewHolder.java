package a123.vaidya.nihal.foodcrunchserver.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import a123.vaidya.nihal.foodcrunchserver.R;

/**
 * Created by nnnn on 26/12/2017.
 */

        public class MenuViewHolder extends RecyclerView.ViewHolder
        //implements View.OnClickListener,View.OnCreateContextMenuListener
{

        public TextView txtMenuName;
        public ImageView imageView;
//      private ItemClickListener itemClickListener;
        public android.widget.Button btnUpdate,btnRemove,btnGoIn;
//      public void setItemClickListener(ItemClickListener itemClickListener) {
//        this.itemClickListener = itemClickListener;
//    }

        public MenuViewHolder(View itemView) {
        super(itemView);

        txtMenuName = itemView.findViewById(R.id.menu_name);
        imageView = itemView.findViewById(R.id.menu_image);
        btnUpdate = itemView.findViewById(R.id.btnUpdate);
        btnGoIn = itemView.findViewById(R.id.btnGoIn);
        btnRemove = itemView.findViewById(R.id.btnRemove);
        //itemView.setOnCreateContextMenuListener(this);
        //itemView.setOnClickListener(this);

    }

//    @Override
//    public void onClick(View v) {
//        itemClickListener.onClick(v,getAdapterPosition(),false);
//    }
//
//    @Override
//    public void onCreateContextMenu(ContextMenu contextMenu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        contextMenu.setHeaderTitle("Select the action");
//        contextMenu.add(0,0,getAdapterPosition(), Common.UPDATE);
//        contextMenu.add(0,0,getAdapterPosition(), Common.DELETE);
        //contextMenu.add(0,0,getAdapterPosition(), Common.DETAILS);
        //contextMenu.add(0,0,getAdapterPosition(), Common.DIRECTIONS);
//    }
}
