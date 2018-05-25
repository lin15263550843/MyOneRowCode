package com.example.lhd.myonerowcode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.lhd.myonerowcode.entity.MessagesItem;

import java.util.ArrayList;
import java.util.List;

public class MessageConversationListActivity extends AppCompatActivity {

    private List<MessagesItem> messagesList = new ArrayList<>();
    private EditText inputText;
    private Button sendButton;
    private RecyclerView messageConversationListRecyclerView;
    private MessageConversationAdapter messageConversationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_conversation_list_layout);
        initMsgs();
        inputText = (EditText) findViewById(R.id.message_conversation_list_edit_text);
        sendButton = (Button) findViewById(R.id.message_conversation_list_button);
        messageConversationListRecyclerView = (RecyclerView) findViewById(R.id.message_conversation_list_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        messageConversationListRecyclerView.setLayoutManager(linearLayoutManager);  //指定RecyclerView的布局方式为LinearLayoutManager
        messageConversationAdapter = new MessageConversationAdapter(messagesList);
        messageConversationListRecyclerView.setAdapter(messageConversationAdapter);
        //发送消息按钮
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = inputText.getText().toString();
                if (!"".equals(content)) {
                    MessagesItem messagesItem = new MessagesItem(content, MessagesItem.TYPE_RECEIVED);
                    messagesList.add(messagesItem);
                    //当有新消息时，刷新ListView中的显示
                    messageConversationAdapter.notifyItemInserted(messagesList.size() - 1);
                    //奖ListView定位到最后一行
                    messageConversationListRecyclerView.scrollToPosition(messagesList.size() - 1);
                    //清空输入框中的内容
                    inputText.setText("");
                }
            }
        });
    }

    private void initMsgs() {
        MessagesItem messagesItem1 = new MessagesItem("你好，我是左边第一条的消息哈哈哈哈哈啊哈哈哈哈哈哈哈啊哈哈哈哈哈哈哈啊哈哈", MessagesItem.TYPE_RECEIVED);
        messagesList.add(messagesItem1);
        MessagesItem messagesItem2 = new MessagesItem("你好，我是右边第一条的消息哈哈哈哈哈啊哈哈哈哈哈哈哈啊哈哈哈哈哈哈哈啊哈哈哈哈哈哈哈啊哈哈", MessagesItem.TYPE_SENT);
        messagesList.add(messagesItem2);
        MessagesItem messagesItem3 = new MessagesItem("你好，我是左边第二条的消息哈哈哈哈哈啊哈哈哈哈哈哈哈啊哈哈哈哈哈哈哈啊哈哈哈哈哈哈哈啊哈哈", MessagesItem.TYPE_RECEIVED);
        messagesList.add(messagesItem3);
        MessagesItem messagesItem4 = new MessagesItem("你好，我是右边第二条的消息哈哈哈哈哈啊哈哈哈哈哈哈哈啊哈哈哈哈哈哈哈啊哈哈哈哈哈哈哈啊哈哈", MessagesItem.TYPE_SENT);
        messagesList.add(messagesItem4);
    }
}
