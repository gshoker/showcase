package com.sourtimestudios.www.materialtest.twitter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sourtimestudios.www.materialtest.R;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

/**
 * Created by user on 11/08/15.
 */
public class TweetListAdapter extends RecyclerView.Adapter<TweetListAdapter.CustomViewHolder> {

    private LayoutInflater inflater;
    private Context context;
    List<TweetModel> list = Collections.emptyList();

    public TweetListAdapter(Context context, List<TweetModel> list) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.list = list;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.list_item_micropost,viewGroup,false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        TweetModel current = list.get(i);
//        customViewHolder.imageView.setImageBitmap(current.icon);
        customViewHolder.message.setText(current.text);
        customViewHolder.user.setText(current.user);
        customViewHolder.time.setText(current.time);

        ImageView imageView = customViewHolder.imageView;

        Picasso.with(context)
                .load(current.gravatar)
                .into(imageView);
    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public void delete(int position){
        list.remove(position);
        notifyItemRemoved(position);
    }


    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView imageView;
        TextView user;
        TextView message;
        TextView time;

        public CustomViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.mplImageView);
            user = (TextView) itemView.findViewById(R.id.mplUsername);
            message = (TextView) itemView.findViewById(R.id.mplContent);
            time = (TextView) itemView.findViewById(R.id.mplTime);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            delete(getAdapterPosition());
        }
    }

    public class TweetModel{
        String gravatar;
        String user;
        String text;
        String time;

        public String getGravatar() {
            return gravatar;
        }

        public void setGravatar(String gravatar) {
            this.gravatar = gravatar;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }


        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }



        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }


    }

}
