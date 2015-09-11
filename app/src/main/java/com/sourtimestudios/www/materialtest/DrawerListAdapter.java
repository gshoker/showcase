package com.sourtimestudios.www.materialtest;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

/**
 * Created by user on 07/07/15.
 */
public class DrawerListAdapter extends RecyclerView.Adapter<DrawerListAdapter.CustomViewHolder> {
    private  LayoutInflater inflater;
    private Context context;
    List<DrawerListModel> list = Collections.emptyList();

    public DrawerListAdapter(Context context, List<DrawerListModel> info){
        inflater = LayoutInflater.from(context);
        this.list = info;
        this.context = context;
    }

    public void delete(int position){
        list.remove(position);
        notifyItemRemoved(position);

    }

    @Override
    public DrawerListAdapter.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.custom_view,viewGroup,false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(DrawerListAdapter.CustomViewHolder viewHolder, int i) {
        DrawerListModel current = list.get(i);
        viewHolder.textView.setText(current.title);
        viewHolder.imageView.setImageResource(current.iconId);
    }

    public static void getData(){

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageView;
        TextView textView;

        public CustomViewHolder(View itemView){
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.list_icon);
            textView = (TextView) itemView.findViewById(R.id.list_textview);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
//            Toast.makeText(context,"Item clicked at "+getAdapterPosition(),Toast.LENGTH_SHORT).show();
            context.startActivity(new Intent(context,SubActivity.class));
        }
    }
}
