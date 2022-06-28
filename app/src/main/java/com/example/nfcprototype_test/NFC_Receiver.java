package com.example.nfcprototype_test;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.TextView;
import android.widget.Toast;

public class NFC_Receiver extends AppCompatActivity {

    NfcAdapter mAdapter;
    PendingIntent mPendingIntent;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc_receiver);

        textView = findViewById(R.id.textView);

        mAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mAdapter == null) {
            //nfc not support your device.
            return;
        }
        mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
                getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.enableForegroundDispatch(this, mPendingIntent, null, null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAdapter != null) {
            mAdapter.disableForegroundDispatch(this);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        getTagInfo(intent);
    }

    private void getTagInfo(Intent intent) {
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (tag != null){
            Toast.makeText(getApplicationContext(), tag.toString(), Toast.LENGTH_SHORT).show();

            //readFromIntent(intent);
            parseNdefMessage(intent);
        }
    }
    void parseNdefMessage(Intent intent) {
        Parcelable[] ndefMessageArray = intent.getParcelableArrayExtra(
                NfcAdapter.EXTRA_NDEF_MESSAGES);
        NdefMessage ndefMessage = (NdefMessage) ndefMessageArray[0];
        String msg = new String(ndefMessage.getRecords()[0].getPayload());
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        Toast.makeText( getApplicationContext(),"Here is my text",
                Toast.LENGTH_LONG).show();

        //editText = (EditText) findViewById(R.id.editText);
        //String text = editText.getText().toString();
        textView.setText(msg); //my attempt to set my received data to "editText" field
    }


}