package com.swopx.chat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.swopx.R;
import com.swopx.Urls_Api.LoginApi;
import com.swopx.Urls_Api.Url_Links;
import com.swopx.adapter.Bid_Adapter;
import com.swopx.fragment.my_project_pkg.Bid_List;
import com.swopx.setter_getter.Items;
import com.swopx.utils.Constant;
import com.swopx.utils.CustomPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Office Work on 28-12-2017.
 */

public class Chat extends AppCompatActivity {
    LinearLayout layout;
    RelativeLayout layout_2;
    ImageView sendButton;
    EditText messageArea;
    ScrollView scrollView;
    Firebase reference1, reference2;
    private TextView back,title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activit_chat);

        layout = (LinearLayout) findViewById(R.id.layout1);
        layout_2 = (RelativeLayout)findViewById(R.id.layout2);
        sendButton = (ImageView)findViewById(R.id.sendButton);
        messageArea = (EditText)findViewById(R.id.messageArea);
        scrollView = (ScrollView)findViewById(R.id.scrollView);
        back = (TextView) findViewById(R.id.back);
        title = (TextView) findViewById(R.id.title);
//        if(!TextUtils.isEmpty(getIntent().getStringExtra("project_name")))
//        {
//            title.setText(UserDetails.chatWith+"("+getIntent().getStringExtra("project_name")+")");
//        }
//        else
//        {
//            title.setText(UserDetails.chatWith);
//        }
        title.setText(UserDetails.chatWith);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        UserDetails.user_id = CustomPreference.readString(Chat.this,CustomPreference.Client_id,"");
        UserDetails.username = CustomPreference.readString(Chat.this,CustomPreference.Client_name,"");;
        Firebase.setAndroidContext(this);
//        Constant.ToastShort(Chat.this,UserDetails.user_id);
        reference1 = new Firebase(Url_Links.Messages + UserDetails.username + "_" + UserDetails.chatWith);
        reference2 = new Firebase(Url_Links.Messages + UserDetails.chatWith + "_" + UserDetails.username);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageArea.getText().toString();

                if(!messageText.equals("")){
                    if(Constant.isNetConnected(Chat.this))
                    {
                        send_Message(messageText);
                    }
                    else
                    {
                        Constant.ToastShort(Chat.this,getResources().getString(R.string.internet_connection));
                    }
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("message", messageText);
                    map.put("user", UserDetails.username);
                    map.put("user_id", UserDetails.user_id);
                    reference1.push().setValue(map);
                    reference2.push().setValue(map);
                    messageArea.setText("");
                }
            }
        });

        reference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map map = dataSnapshot.getValue(Map.class);
                String message = map.get("message").toString();
                String userName = map.get("user").toString();
                String user_id = map.get("user_id").toString();

                if(user_id.equals(UserDetails.user_id)){
                    addMessageBox("You:-\n" + message, 1);
                }
                else{
                    addMessageBox(UserDetails.chatWith+ ":-\n" + message, 2);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public void addMessageBox(String message, int type){
        TextView textView = new TextView(Chat.this);
        textView.setText(message);

        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.weight = 1.0f;

        if(type == 1) {
            lp2.gravity = Gravity.LEFT;
            textView.setBackgroundResource(R.drawable.chat_left);
        }
        else{
            lp2.gravity = Gravity.RIGHT;
            textView.setBackgroundResource(R.drawable.chat_right);
        }
        textView.setLayoutParams(lp2);
        layout.addView(textView);
        scrollView.fullScroll(View.FOCUS_DOWN);
    }
    private void send_Message(String messageText) {
        JSONObject params = new JSONObject();
        JSONObject body = new JSONObject();
        try {
            params.put("sender_id" ,UserDetails.user_id);
            params.put("sender_name" ,UserDetails.username);
            params.put("receiver_id" ,UserDetails.chatWith_id);
            params.put("receiver_name" ,UserDetails.chatWith);
            params.put("message" ,messageText);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Constant.Log("reponse Login", "=================" +params);
        new LoginApi(Chat.this,params, Url_Links.setMessage, false) {
            @Override
            public void response(JSONObject response) {
                super.response(response);
                try {
                    Constant.Log("reponse Login", "=================" + response);
                    JSONObject obj= response.getJSONObject("result");
                    if (obj.getString("status").equalsIgnoreCase("200"))
                    {
                    }
//                    Constant.ToastShort(Chat.this,obj.getString("msg"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(VolleyError error) {
                super.error(error);
                Constant.ToastShort(Chat.this, error.getMessage());
            }
        };

    }

}
