
package com.example.douanetemporelle;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class VerificationActivity extends AppCompatActivity {

    // نفس رقم بطاقة الطالب المستعمل في النشاط الأول
    private static final String MATRICULE = "09655737";

    TextView txtNom, txtAge, txtMotif;
    Button approve, refuse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        txtNom = findViewById(R.id.txt_IB_nom);
        txtAge = findViewById(R.id.txt_IB_age);
        txtMotif = findViewById(R.id.txt_IB_motif);

        approve = findViewById(R.id.btn_IB_approve);
        refuse = findViewById(R.id.btn_IB_refuse);

        // استقبال البيانات من النشاط الأول
        Intent receivedIntent = getIntent();

        String nom = receivedIntent.getStringExtra(
                INTENTKEYS.nom(MATRICULE));

        int naissance = receivedIntent.getIntExtra(
                INTENTKEYS.naissance(MATRICULE), 0);

        int destination = receivedIntent.getIntExtra(
                INTENTKEYS.destination(MATRICULE), 0);

        String motif = receivedIntent.getStringExtra(
                INTENTKEYS.motif(MATRICULE));

        // حساب الفرق بين سنة الوجهة وسنة الميلاد
        int difference = Math.abs(destination - naissance);

        // عرض المعلومات على جواز السفر
        txtNom.setText("Nom : " + nom);
        txtAge.setText("Age temporel : " + difference + " ans");
        txtMotif.setText("Motif : " + motif);

        // تعطيل الموافقة عند وجود خطر المفارقة الزمنية
        if ("Modification du passé".equals(motif)
                && estPremier(difference)) {

            approve.setEnabled(false);
            approve.setText("Approbation impossible");
        }

        // إرسال الموافقة
        approve.setOnClickListener(v -> envoyerVerdict(true, nom));

        // إرسال الرفض
        refuse.setOnClickListener(v -> envoyerVerdict(false, nom));
    }

    // دالة للتثبت إذا كان العدد أوليا
    private boolean estPremier(int nombre) {

        if (nombre < 2) {
            return false;
        }

        for (int i = 2; i <= Math.sqrt(nombre); i++) {
            if (nombre % i == 0) {
                return false;
            }
        }

        return true;
    }

    // إرسال القرار واسم المسافر إلى النشاط الثالث
    private void envoyerVerdict(boolean verdict, String nom) {

        Intent intent = new Intent(
                VerificationActivity.this,
                ResultActivity.class
        );

        intent.putExtra(
                INTENTKEYS.verdict(MATRICULE), verdict);

        intent.putExtra(
                INTENTKEYS.nom(MATRICULE), nom);

        startActivity(intent);
    }
}

