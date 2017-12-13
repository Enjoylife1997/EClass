package com.example.eclass.BasicActivity;


/*
   查看已发布的消息
 */
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.eclass.Class.MessageCard;
import com.example.eclass.R;
import com.example.eclass.Tools.MessageAdapter;
import com.example.eclass.tableClass.Message;


import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class MessageActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;


    //private MessageCard[] messageCards = {new MessageCard("66",R.drawable.cardim),new MessageCard("hhh",R.drawable.cardim),new MessageCard("eee",R.drawable.cardim),new MessageCard("ppp",R.drawable.cardim),new MessageCard("xxxx",R.drawable.cardim)};
    private MessageCard[] messageCards = null;



    private List<MessageCard> messageCardList = new ArrayList<>();
    private MessageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_layout);

        //默认初始化
        Bmob.initialize(this, "8790673e88e63fbe66a2a39a9339d15a");

        initMessageCards();

    }

    private void initMessageCards(){
        /*
        messageCardList.clear();
        for(int i = 0;i < 50;i++){
            Random random = new Random();
            int index = random.nextInt(messageCards.length);
            messageCardList.add(messageCards[index]);
        }
        */
        //将Message类的消息循环遍历放入messageCardList中
        BmobQuery<Message> query = new BmobQuery<Message>();
        query.findObjects(new FindListener<Message>() {
            @Override
            public void done(List<Message> list, BmobException e) {
                for(Message message : list){
                    messageCardList.add(new MessageCard("信息内容："+message.getText()+
                            "发送者："+message.getSender()
                            +"发送时间："+message.getTime(),R.drawable.cardim));
                }
                RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
                GridLayoutManager layoutManager = new GridLayoutManager(MessageActivity.this,2);
                recyclerView.setLayoutManager(layoutManager);
                adapter = new MessageAdapter(messageCardList);
                recyclerView.setAdapter(adapter);

            }
        });

    }
}
