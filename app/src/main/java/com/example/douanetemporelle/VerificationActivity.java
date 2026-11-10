package com.example.douanetemporelle;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class VerificationActivity extends AppCompatActivity {

    // Na3mlou declaration lel matricule eli nesta3mlouh fi les activites lkol
    private static final String MATRICULE = "09655737";

    // Na3mlou declaration lel elements mta3 interface
    TextView txtNom, txtAge, txtMotif;
    Button approve, refuse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        // Norbto les variables m3a les elements eli mawjoudin fil XML
        txtNom = findViewById(R.id.txt_IB_nom);
        txtAge = findViewById(R.id.txt_IB_age);
        txtMotif = findViewById(R.id.txt_IB_motif);

        approve = findViewById(R.id.btn_IB_approve);
        refuse = findViewById(R.id.btn_IB_refuse);

        // Nesta9blou l'Intent eli jet mel MainActivity
        Intent receivedIntent = getIntent();

        // Nesta5rjou les donnees eli b3athhom MainActivity
        String nom = receivedIntent.getStringExtra(
                INTENTKEYS.nom(MATRICULE));

        int naissance = receivedIntent.getIntExtra(
                INTENTKEYS.naissance(MATRICULE), 0);

        int destination = receivedIntent.getIntExtra(
                INTENTKEYS.destination(MATRICULE), 0);

        String motif = receivedIntent.getStringExtra(
                INTENTKEYS.motif(MATRICULE));

        // N7asbou el far9 bin 3am el wilada w 3am el destination
        int difference = Math.abs(destination - naissance);

        // Naffichiw les informations mta3 el voyageur
        txtNom.setText("Nom : " + nom);
        txtAge.setText("Age temporel : " + difference + " ans");
        txtMotif.setText("Motif : " + motif);

        // Nethabtou ken el motif Modification du passé w el far9 nombre premier
        if ("Modification du passé".equals(motif)
                && estPremier(difference)) {

            // Ken fama risque temporel, na3mlou desactivation lel bouton Approve
            approve.setEnabled(false);
            approve.setText("Approbation impossible");
        }

        // Ki nenzelou 3la Approve, nb3thou decision positive
        approve.setOnClickListener(v -> envoyerVerdict(true, nom));

        // Ki nenzelou 3la Refuse, nb3thou decision negative
        refuse.setOnClickListener(v -> envoyerVerdict(false, nom));
    }

    // Fonction tetthabet ken el nombre premier wala le
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

    // Fonction teb3ath el decision w esm el voyageur lel ResultActivity
    private void envoyerVerdict(boolean verdict, String nom) {

        Intent intent = new Intent(
                VerificationActivity.this,
                ResultActivity.class
        );

        // Neb3thou el verdict w esm el voyageur
        intent.putExtra(
                INTENTKEYS.verdict(MATRICULE), verdict);

        intent.putExtra(
                INTENTKEYS.nom(MATRICULE), nom);

        // Nent9lou lel Activity ethletha
        startActivity(intent);
    }
}

