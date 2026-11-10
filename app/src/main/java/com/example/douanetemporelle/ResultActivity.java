
package com.example.douanetemporelle;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    // nafss matricule eli stamlneh f les activites lokhrin
    private static final String MATRICULE = "09655737";

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

        // est9bel 9arar w esm lmoseefrr
        Intent receivedIntent = getIntent();

        boolean verdict = receivedIntent.getBooleanExtra(
                INTENTKEYS.verdict(MATRICULE), false);

        String nom = receivedIntent.getStringExtra(
                INTENTKEYS.nom(MATRICULE));

        if (nom == null) {
            nom = "Voyageur";
        }

        // tghir couleur selon decision
        if (verdict) {

            layout.setBackgroundColor(Color.parseColor("#2E7D32"));

            message.setText("Bon voyage " + nom + " !");

        } else {

            layout.setBackgroundColor(Color.parseColor("#C62828"));

            message.setText(
                    nom + " est en état d'arrestation temporelle."
            );

        }

        // narjou lel activite loula w nskrou lokhrin
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
