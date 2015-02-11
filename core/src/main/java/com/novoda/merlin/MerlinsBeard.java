package com.novoda.merlin;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Build;

/**
 * This class provides a mechanism for retrieving the current
 * state of a network connection given an application context.
 */
public class MerlinsBeard {

    private ConnectivityManager connectivityManager;
    private Context context;

    MerlinsBeard(ConnectivityManager connectivityManager, Context context) {
        this.connectivityManager = connectivityManager;
        this.context = context;

        setup();
    }

    /**
     * Use this method to create a MerlinsBeard object, this is how you can retrieve the current network state.
     *
     * @param context pass any context application or activity.
     * @return MerlinsBeard.
     */
    public static MerlinsBeard from(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return new MerlinsBeard(connectivityManager, context.getApplicationContext());
    }

    private static Intent getConnectivityIntent(boolean noConnection) {
        Intent intent = new Intent();

        intent.setAction(Merlin.CONNECTIVITY_ACTION);
        intent.putExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, noConnection);

        return intent;

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setup() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
            return;

        NetworkRequest.Builder builder = new NetworkRequest.Builder();

        connectivityManager.registerNetworkCallback(
                builder.build(),
                new ConnectivityManager.NetworkCallback() {

                    @Override
                    public void onAvailable(Network network) {
                        context.sendBroadcast(
                                getConnectivityIntent(false)
                        );
                    }

                    @Override
                    public void onLost(Network network) {
                        context.sendBroadcast(
                                getConnectivityIntent(true)
                        );
                    }
                }

        );
    }

    /**
     * Provides a boolean representing whether a network connection has been established.
     * NOTE: Therefore available does not necessarily mean that an internet connection
     * is available.
     *
     * @return boolean true if a network connection is available.
     */
    public boolean isConnected() {
        NetworkInfo activeNetworkInfo = getNetworkInfo();
        return (activeNetworkInfo != null && activeNetworkInfo.isConnected());
    }

    private NetworkInfo getNetworkInfo() {
        return connectivityManager.getActiveNetworkInfo();
    }
}
