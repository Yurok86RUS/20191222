package geekbrains.ru.receivesms;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.TextView;

public class SmsReceiver extends BroadcastReceiver {

//    private static final String TAG = "myLog";
//    MainActivity text;
    int messageId = 0;
    @Override
    public void onReceive(Context context, Intent intent) {
// Минимальные проверки
        if (intent == null || intent.getAction() == null) return;

        Bundle bundle = intent.getExtras();
// Получаем сообщения
        Object[] pdus = (Object[]) bundle.get("pdus");
        SmsMessage[] messages = new SmsMessage[pdus.length];
        for (int i = 0; i < pdus.length; i++) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i], bundle.getString("format"));
            }
            else
            {
                messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
            }
        }
        String smsFromPhone = messages[0].getDisplayOriginatingAddress();
        StringBuilder body = new StringBuilder();
        for (int i = 1; i < messages.length; i++) {
            body.append(messages[i].getMessageBody());
        }
//        Log.d(TAG,"сейчас будет делаться бодитекст",null);
//        Log.d(TAG,"message " + messages, null );
//        Log.d(TAG,"body " + body, null );

        String bodyText = body.toString();

//        Log.d(TAG,"boditext = " + bodyText, null);

        makeNote(context, smsFromPhone, bodyText);

//        Log.d(TAG,"уже вызвался makeNote " + bodyText,null);
// Это будет работать только на Android ниже 4.4
        abortBroadcast();

    }

    // Вывод уведомления в строке состояния
    private void makeNote(Context context, String addressFrom, String message) {

//        Log.d(TAG,"я уже в методе makeNote и тут бодитекст = " + message, null);
//        Log.d(TAG,"adres = " + addressFrom,null);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "2")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(String.format("Receiver:Sms [%s]", addressFrom))
                .setContentText(message);
        Intent resultIntent = new Intent(context, SmsReceiver.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        builder.setContentIntent(resultPendingIntent);
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(messageId++, builder.build());
    }
}
