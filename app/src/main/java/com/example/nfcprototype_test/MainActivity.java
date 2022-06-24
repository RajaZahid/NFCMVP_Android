package com.example.nfcprototype_test;

import androidx.appcompat.app.AppCompatActivity;

import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NfcAdapter.CreateNdefMessageCallback {

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.edittext);


        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            editText.setText("NFC not supported.");
            return;
        }

        if (!nfcAdapter.isEnabled()) {
            Toast.makeText(this, "Enable NFC first!", Toast.LENGTH_LONG).show();
        }

        nfcAdapter.setNdefPushMessageCallback(this, this);
    }


    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {
        String message = editText.getText().toString();
        NdefRecord ndefRecord = null;
        NdefMessage ndefMessage = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            ndefRecord = NdefRecord.createMime("text/plain", message.getBytes());
            ndefMessage = new NdefMessage(ndefRecord);
        }
        return ndefMessage;
    }
}