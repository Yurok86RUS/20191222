package geekbrains.ru.receivesms;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

//    public TextView textView123 = findViewById(R.id.text_view_receive_message);
    String phoneNumber = "6505551212";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editText = findViewById(R.id.edit_text_message);
        Button button = findViewById(R.id.button_send);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageSend = editText.getText().toString();
                SmsManager.getDefault().sendTextMessage(phoneNumber,null,messageSend,null,null);
//
//                String number="6505551212";
//                String message="Привет GeekBrains!";
//                String toNumberSms="smsto:"+number;
//                Intent sms=new Intent(Intent.ACTION_SENDTO, Uri.parse(toNumberSms));
//                sms.putExtra("sms_body", message);
//                startActivity(sms);

            }
        });
    }

//    public void setTextView(String text){
//        TextView textView = findViewById(R.id.text_view_receive_message);
//        textView.setText(text);
//    }
}
