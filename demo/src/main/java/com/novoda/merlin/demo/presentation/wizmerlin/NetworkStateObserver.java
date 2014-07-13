package com.novoda.merlin.demo.presentation.wizmerlin;

import com.novoda.merlin.NetworkStatus;
import com.novoda.merlin.registerable.bind.Bindable;
import com.novoda.merlin.registerable.connection.Connectable;
import com.novoda.merlin.registerable.disconnection.Disconnectable;

public class NetworkStateObserver implements Connectable, Disconnectable, Bindable {

    private final Callbacks callbacks;

    private NetworkStatus networkStatus;

    public NetworkStateObserver(Callbacks callbacks) {
        this.callbacks = callbacks;
    }

    @Override
    public void onBind(NetworkStatus networkStatus) {
        this.networkStatus = networkStatus;
        if (networkStatus.isAvailable()) {
            onConnect();
        } else {
            onDisconnect();
        }
    }

    @Override
    public void onConnect() {
        callbacks.onConnectedToNetwork();
    }

    @Override
    public void onDisconnect() {
        callbacks.onDisconnectedFromNetwork();
    }

    public boolean connectedToNetwork() {
        return networkStatus.isAvailable();
    }

    public interface Callbacks {

        void onConnectedToNetwork();

        void onDisconnectedFromNetwork();

    }

}
