package com.example.lhd.myonerowcode;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lhd.myonerowcode.entity.MessagesItem;

import java.util.List;

/**
 * Created by lhd on 2018/5/12.
 */

public class MessageConversationAdapter extends RecyclerView.Adapter<MessageConversationAdapter.ViewHolde> {


    private List<MessagesItem> mMessageList;

    static class ViewHolde extends RecyclerView.ViewHolder {

        LinearLayout linearLayoutLeft;
        LinearLayout linearLayoutRight;
        TextView msgLeft;
        TextView msgRight;

        public ViewHolde(View itemView) {
            super(itemView);
            linearLayoutLeft = (LinearLayout) itemView.findViewById(R.id.message_item_left_layout);
            linearLayoutRight = (LinearLayout) itemView.findViewById(R.id.message_item_right_layout);
            msgLeft = (TextView) itemView.findViewById(R.id.message_item_left_msg);
            msgRight = (TextView) itemView.findViewById(R.id.message_item_right_msg);
        }
    }

    public MessageConversationAdapter(List<MessagesItem> mMsgList) {
        mMessageList = mMsgList;
    }

    @Override
    public ViewHolde onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item_layout, parent, false);
        return new ViewHolde(view);
    }

    @Override
    public void onBindViewHolder(ViewHolde holder, int position) {
        MessagesItem messagesItem = mMessageList.get(position);
        if (messagesItem.getMsgType() == MessagesItem.TYPE_RECEIVED) {
            //显示左边消息
            holder.linearLayoutLeft.setVisibility(View.VISIBLE);
            holder.linearLayoutRight.setVisibility(View.GONE);
            holder.msgLeft.setText(messagesItem.getMsgContent());
        } else if (messagesItem.getMsgType() == MessagesItem.TYPE_SENT) {
            //显示右边消息
            holder.linearLayoutRight.setVisibility(View.VISIBLE);
            holder.linearLayoutLeft.setVisibility(View.GONE);
            holder.msgRight.setText(messagesItem.getMsgContent());
        }
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

}
