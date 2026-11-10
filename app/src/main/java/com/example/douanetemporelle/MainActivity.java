

package com.example.douanetemporelle;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {

    // رقم بطاقة الطالب المستعمل في جميع الأنشطة
    private static final String MATRICULE = "09655737";


    EditText nom, naissance, destination;
    Spinner motif;
    Button inspecter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nom = findViewById(R.id.edt_FB_nom);
        naissance = findViewById(R.id.edt_FB_naissance);
        destination = findViewById(R.id.edt_FB_destination);
        motif = findViewById(R.id.spn_FB_motif);
        inspecter = findViewById(R.id.btn_FB_inspecter);

        // إنشاء قائمة أسباب السفر
        String[] motifs = {
                "Tourisme",
                "Modification du passé",
                "Fuite"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                motifs
        );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        motif.setAdapter(adapter);

        inspecter.setOnClickListener(v -> {

            String nomVoyageur = nom.getText().toString().trim();
            String anneeN = naissance.getText().toString().trim();
            String anneeD = destination.getText().toString().trim();

            // التثبت من أن جميع الخانات معمّرة
            if (nomVoyageur.isEmpty()
                    || anneeN.isEmpty()
                    || anneeD.isEmpty()) {

                Toast.makeText(this,
                        "يرجى تعمير جميع الخانات",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                int anneeNaissance = Integer.parseInt(anneeN);
                int anneeDestination = Integer.parseInt(anneeD);

                if (anneeNaissance <= 0 || anneeDestination <= 0) {
                    Toast.makeText(this,
                            "السنة غير صحيحة",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // إنشاء Intent صريح للانتقال إلى واجهة التثبت
                Intent intent = new Intent(
                        MainActivity.this,
                        VerificationActivity.class

                );

                //  nb3th l matriculr
                intent.putExtra(
                        INTENTKEYS.nom(MATRICULE), nomVoyageur);

                intent.putExtra(
                        INTENTKEYS.naissance(MATRICULE), anneeNaissance);

                intent.putExtra(
                        INTENTKEYS.destination(MATRICULE), anneeDestination);

                intent.putExtra(
                        INTENTKEYS.motif(MATRICULE),
                        motif.getSelectedItem().toString());

                startActivity(intent);
            } catch (NumberFormatException e) {
                Toast.makeText(this,
                        "يرجى إدخال سنوات صحيحة",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}