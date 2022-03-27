package com.alazraq.alkhayat.goldenbeach.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.alazraq.alkhayat.goldenbeach.MainActivity;
import com.alazraq.alkhayat.goldenbeach.R;
import com.alazraq.alkhayat.goldenbeach.activities.Base_chatting_activity;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Shared_Helper;
import com.alazraq.alkhayat.goldenbeach.lists_items.Items_of_base_chatting;
import com.alazraq.alkhayat.goldenbeach.ui.chatting.Chatting_fragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.alazraq.alkhayat.goldenbeach.helper_classes.App.LOGIN_CHANNEL_ID;

public class Chatting_service extends Service {

    DatabaseReference databaseReference;
    List<Items_of_base_chatting> items_of_base_chatting = new ArrayList<>();
    int number;
    Items_of_base_chatting current_item;


    //notification of new message objects
    NotificationManagerCompat notificationManager;

    Intent go_to_base_chatting_activity_intent;
    Bundle go_to_base_chatting_activity_bundle;
    PendingIntent go_to_base_chatting_activity_pending_intent;

    Intent go_to_base_chatting_activity_intent2;
    PendingIntent go_to_base_chatting_activity_pending_intent2;

    String sender,receiver,message;

    public static int NOTIFICATION_ID=1;
    Integer number_of_chatting;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        databaseReference = FirebaseDatabase.getInstance().getReference().child("main_chating_rooms");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                try{
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    current_item = dataSnapshot.getValue(Items_of_base_chatting.class);
                    items_of_base_chatting.add(current_item);

                    if (items_of_base_chatting != null) {
                        if (items_of_base_chatting.get(items_of_base_chatting.size() - 1).getRead().equalsIgnoreCase("no")) {
                            sender = items_of_base_chatting.get(items_of_base_chatting.size() - 1).getSender();
                            receiver = items_of_base_chatting.get(items_of_base_chatting.size() - 1).getReceiver();
                            message = items_of_base_chatting.get(items_of_base_chatting.size() - 1).getMessage();

                            if (Shared_Helper.getkey(Chatting_service.this, "notifications").equalsIgnoreCase("on")) {

                                if (Shared_Helper.getkey(Chatting_service.this, "user_name").equalsIgnoreCase("admin")) {
                                    if (!sender.equalsIgnoreCase("admin")) {
                                        notificationManager = NotificationManagerCompat.from(Chatting_service.this);
                                        go_to_base_chatting_activity_intent = new Intent(Chatting_service.this, Base_chatting_activity.class);
                                        //go_to_base_chatting_activity_bundle=new Bundle();
                                        //go_to_base_chatting_activity_bundle.putString("user_name", sender);

                                        go_to_base_chatting_activity_intent.putExtra("user_name",sender);
                                        go_to_base_chatting_activity_pending_intent = PendingIntent.getActivity(Chatting_service.this, 1, go_to_base_chatting_activity_intent, 0);

                                        startNotification(sender, message);
                                    }

                                } else {
                                    if (Shared_Helper.getkey(Chatting_service.this, "user_name").equalsIgnoreCase(receiver)) {
                                        notificationManager = NotificationManagerCompat.from(Chatting_service.this);

                                        go_to_base_chatting_activity_intent2 = new Intent(Chatting_service.this, MainActivity.class);
                                        go_to_base_chatting_activity_pending_intent2 = PendingIntent.getActivity(Chatting_service.this, 1, go_to_base_chatting_activity_intent2, 0);

                                        startNotification2();
                                        //Toast.makeText(Chatting_service.this, sender, Toast.LENGTH_SHORT).show();
                                    }
                                }


                            }

                        }


                        //Toast.makeText(Chatting_service.this, receiver, Toast.LENGTH_SHORT).show();


                    }


                }


            }catch (Exception e){
                    Toast.makeText(Chatting_service.this, "catch error", Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return number;
    }
/*
   // private void startInitObjects(String user_name){

        //notification objects
        notificationManager=NotificationManagerCompat.from(this);
        go_to_base_chatting_activity_intent=new Intent(this,Base_chatting_activity.class);
        go_to_base_chatting_activity_intent.putExtra("user_name",user_name);
        go_to_base_chatting_activity_pending_intent=PendingIntent.getActivity(this,1,go_to_base_chatting_activity_intent,0);


    }

 */

    private void startNotification(String sender, String message){

        Notification notification= new NotificationCompat.Builder(this, LOGIN_CHANNEL_ID)

                .setSmallIcon(R.drawable.email_icon_golden)

                .setContentTitle(sender)
                .setContentText(message)
                .setAutoCancel(true)
                .setCategory(Notification.CATEGORY_MESSAGE)
                .setContentTitle(getResources().getString(R.string.app_name))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setColor(getResources().getColor(R.color.light_golden))
                .setContentIntent(go_to_base_chatting_activity_pending_intent)

                .build();


        notificationManager.notify(NOTIFICATION_ID,notification);

        NOTIFICATION_ID++;


    }

    private void startNotification2(){

        Notification notification= new NotificationCompat.Builder(this, LOGIN_CHANNEL_ID)

                .setSmallIcon(R.drawable.email_icon_golden)

                .setContentTitle(sender)
                .setContentText(message)
                .setAutoCancel(true)
                .setCategory(Notification.CATEGORY_MESSAGE)
                .setContentTitle(getResources().getString(R.string.app_name))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setColor(getResources().getColor(R.color.light_golden))
                .setContentIntent(go_to_base_chatting_activity_pending_intent2)

                .build();


        notificationManager.notify(NOTIFICATION_ID,notification);

        NOTIFICATION_ID++;


    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
