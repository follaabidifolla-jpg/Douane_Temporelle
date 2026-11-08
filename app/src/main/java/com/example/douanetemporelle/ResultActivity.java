
package com.example.douanetemporelle;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    // نفس رقم بطاقة الطالب المستعمل في بقية الأنشطة
    private static final String MATRICULE = "12345678";

    LinearLayout layout;
    TextView message;
    Button nouveau;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        layout = findViewById(R.id.layout_YB_result);
        message = findViewById(R.id.txt_YB_message);
        nouveau = findViewById(R.id.btn_YB_nouveau);

        // استقبال القرار واسم المسافر
        Intent receivedIntent = getIntent();

        boolean verdict = receivedIntent.getBooleanExtra(
                INTENTKEYS.verdict(MATRICULE), false);

        String nom = receivedIntent.getStringExtra(
                INTENTKEYS.nom(MATRICULE));

        if (nom == null) {
            nom = "Voyageur";
        }

        // تغيير لون الشاشة حسب القرار
        if (verdict) {

            layout.setBackgroundColor(Color.parseColor("#2E7D32"));

            message.setText("Bon voyage " + nom + " !");

        } else {

            layout.setBackgroundColor(Color.parseColor("#C62828"));

            message.setText(
                    nom + " est en état d'arrestation temporelle."
            );
        }

        // العودة إلى النشاط الأول مع غلق النشاطين السابقين
        nouveau.setOnClickListener(v -> {

            Intent intent = new Intent(
                    ResultActivity.this,
                    MainActivity.class
            );

            intent.setFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP
                            | Intent.FLAG_ACTIVITY_SINGLE_TOP
            );

            startActivity(intent);
            finish();
        });
    }
}
