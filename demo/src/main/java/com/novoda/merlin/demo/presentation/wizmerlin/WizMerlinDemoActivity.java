package com.novoda.merlin.demo.presentation.wizmerlin;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.novoda.merlin.demo.R;

public class WizMerlinDemoActivity extends Activity {

    private WizMerlin wizMerlin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wiz_merlin_demo);

        final TextView feedbackTextView = (TextView) findViewById(R.id.network_connection_feedback_text);
        wizMerlin = WizMerlin.newInstance(this, new WizMerlin.Callbacks() {

            @Override
            public void onConnectedToNetwork() {
                feedbackTextView.setText("Network status: connected");
            }

            @Override
            public void onDisconnectedFromNetwork() {
                feedbackTextView.setText("Network status: disconnected");
            }

        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        wizMerlin.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        wizMerlin.onStop();
    }

    public void checkIfConnected(View button) {
        if (wizMerlin.isConnectedToNetwork()) {
            ((Button) button).setText("Is connected? :)");
        } else {
            ((Button) button).setText("Is connected? :(");
        }
    }

}

