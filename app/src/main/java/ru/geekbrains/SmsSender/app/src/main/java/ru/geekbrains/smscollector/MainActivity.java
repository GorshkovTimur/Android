package ru.geekbrains.smscollector;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button send = findViewById(R.id.send);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText number=(EditText)findViewById(R.id.phone);
                EditText message=(EditText)findViewById(R.id.body);
                String toSms="smsto:"+number.getText().toString();
                String messageText= message.getText().toString();
                Intent sms=new Intent(Intent.ACTION_SENDTO, Uri.parse(toSms));
                sms.putExtra("sms_body", messageText);
                startActivity(sms);
            }
        });

    }
}
