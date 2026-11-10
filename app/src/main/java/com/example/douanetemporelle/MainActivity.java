
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

    // lezem nhotou ra9em bita9et ta3rif el taleb bech nesta3mlouh fi les activites lkol

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

        // na3mlou liste feha asbeb essafar bech el utilisateur ye5tar menha

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

            // nesta5rjou les informations eli ktebhom el utilisateur

            String nomVoyageur = nom.getText().toString().trim();
            String anneeN = naissance.getText().toString().trim();
            String anneeD = destination.getText().toString().trim();

            // net2akdou eli les 5anet lkol m3amrin

            if (nomVoyageur.isEmpty()
                    || anneeN.isEmpty()
                    || anneeD.isEmpty()) {

                Toast.makeText(this,
                        "يرجى تعمير جميع الخانات",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                // n7awlou les annees men String l Integer

                int anneeNaissance = Integer.parseInt(anneeN);
                int anneeDestination = Integer.parseInt(anneeD);

                // net2akdou eli les annees s7a7 w akther men 0

                if (anneeNaissance <= 0 || anneeDestination <= 0) {
                    Toast.makeText(this,
                            "السنة غير صحيحة",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // na3mlou Intent bech yna9elna lel VerificationActivity

                Intent intent = new Intent(
                        MainActivity.this,
                        VerificationActivity.class
                );

                // nba3thou les informations mta3 el voyageur lel activity lokhra

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

                // ken el utilisateur da5el 7aja moch ra9em ya3tih message d'erreur

                Toast.makeText(this,
                        "يرجى إدخال سنوات صحيحة",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
