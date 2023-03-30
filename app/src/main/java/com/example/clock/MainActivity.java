package com.example.clock;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img = findViewById(R.id.imageView);

        // Analog saati çizmek için bir Bitmap oluşturun
        final Bitmap bitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);

        // Saat çerçevesini ve sayıları çizin
        final int centerX = bitmap.getWidth() / 2;
        final int centerY = bitmap.getHeight() / 2;
        final int radius = bitmap.getWidth() / 2 - 10;
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        canvas.drawCircle(centerX, centerY, radius, paint);
        paint.setStrokeWidth(3);
        for (int i = 1; i <= 12; i++) {

            canvas.drawText(Integer.toString(i), (float) (centerX + Math.sin(Math.toRadians(i * 30)) * radius * 0.8),
                    (float) (centerY - Math.cos(Math.toRadians(i * 30)) * radius * 0.8), paint);
        }

        // Timer kullanarak saat işaretlerini her saniye güncelleyin
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                canvas.drawColor(Color.WHITE);
                // Şu anki zamanı alın
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR);
                int minute = calendar.get(Calendar.MINUTE);
                int second = calendar.get(Calendar.SECOND);

                // Saat işaretlerini çizin
                Paint paint = new Paint();

                paint.setStyle(Paint.Style.STROKE);

                paint.setStrokeWidth(5);
                canvas.drawColor(Color.YELLOW);
                canvas.drawLine(centerX, centerY, (float)(centerX + Math.sin(Math.toRadians(hour * 30 + minute * 0.5)) * centerX / 2),
                        (float)(centerY - Math.cos(Math.toRadians(hour * 30 + minute * 0.5)) * centerY / 2), paint);
                paint.setStrokeWidth(3);

                canvas.drawLine(centerX, centerY, (float)(centerX + Math.sin(Math.toRadians(minute * 6)) * centerX / 1.5),
                        (float)(centerY - Math.cos(Math.toRadians(minute * 6)) * centerY / 1.5), paint);
                paint.setStrokeWidth(2);

                canvas.drawLine(centerX, centerY, (float)(centerX + Math.sin(Math.toRadians(second * 6)) * centerX / 1.2),
                        (float)(centerY - Math.cos(Math.toRadians(second * 6)) * centerY / 1.2), paint);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Güncellenen saat resmini ImageView'e yükleyin
                        img.setImageBitmap(bitmap);
                    }
                });
            }
        }, 0, 1000); // Her saniye güncelleyin
    }
}
