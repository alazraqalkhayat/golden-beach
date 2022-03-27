package com.alazraq.alkhayat.goldenbeach.broad_cast_resivers;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import com.alazraq.alkhayat.goldenbeach.broad_cast_resivers.Network_connection_broadcaster_receiver;


public class Network_broadcast_receiver_register {

    Context context;

    public Network_broadcast_receiver_register(Context context) {
        this.context = context;
    }

    public void registerNetworkConnection(){
        Network_connection_broadcaster_receiver connection=new Network_connection_broadcaster_receiver();
        context.registerReceiver(connection,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

    }

    public void unRegisterNetworkConnection(){
        Network_connection_broadcaster_receiver connection=new Network_connection_broadcaster_receiver();
        context.unregisterReceiver(connection);
    }
}
