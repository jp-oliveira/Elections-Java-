package com.pac.elections;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pac.elections.Controle.Gerente;
import com.pac.elections.Modelo.candidato;
import com.pac.elections.Modelo.eleitor;

import java.util.ArrayList;

public class ActivityVotar extends AppCompatActivity {

    Gerente ger = new Gerente();

    candidato cand = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_votar);

        final EditText edtNumero = (EditText) findViewById(R.id.edtNumero);
        Button btSelCandidato = (Button) findViewById(R.id.btSelCandidato);
        final Button btConfirmar = (Button) findViewById(R.id.btConfirmar);
        final TextView tvNomeCandidato = (TextView) findViewById(R.id.tvNomeCandidato);
        final TextView tvPartido = (TextView) findViewById(R.id.tvPartido);

        final ArrayList<candidato> candidatos = (ArrayList<candidato>) getIntent().getSerializableExtra("candidatos");
        final eleitor ele = (eleitor) getIntent().getSerializableExtra("eleitor");
        ger.setCandidatos(candidatos);

        btSelCandidato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btConfirmar.setVisibility(View.INVISIBLE);
                int num;
                if(edtNumero.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Digite o número do candidato", Toast.LENGTH_LONG).show();
                }
                else{
                    num = Integer.parseInt(edtNumero.getText().toString());

                    cand = ger.consultaPorNumero(num);
                    if (cand == null)
                        Toast.makeText(getApplicationContext(),"Número Inválido!!!", Toast.LENGTH_LONG).show();
                    else {
                        tvNomeCandidato.setText("Candidato: " + cand.getNome());
                        tvPartido.setText("Partido: " + cand.getPartido());
                        btConfirmar.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        btConfirmar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ele.setVotou(true);
                cand.setVotos(cand.getVotos()+1);

                Intent returnIntent = new Intent();
                returnIntent.putExtra("eleitor",ele);
                returnIntent.putExtra("candidato",cand);
                setResult(RESULT_OK,returnIntent);
                finish();
            }

        });
    }
}
