package com.example.eclass.Tools;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.eclass.Class.MessageCard;
import com.example.eclass.R;

import java.util.List;

/**
 * Created by Enjoy life on 2017/10/14.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private Context mContext;

    private List<MessageCard> messageCardList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView image;
        TextView message;

        public ViewHolder(View view){
            super(view);
            cardView = (CardView) view;
            image = (ImageView) view.findViewById(R.id.image);
            message = (TextView) view.findViewById(R.id.message);
        }
    }
    public MessageAdapter(List<MessageCard> messagecardList) {
        messageCardList = messagecardList;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.message_item, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MessageCard messageCard = messageCardList.get(position);
        holder.message.setText(messageCard.getMessage());
        Glide.with(mContext).load(messageCard.getImageId()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return messageCardList.size();
    }


}
