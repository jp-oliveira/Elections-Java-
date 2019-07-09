package com.pac.elections;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.pac.elections.DAO.CandidatoDAO;
import com.pac.elections.Modelo.candidato;
import java.util.ArrayList;

public class ActivityEnviarEmail extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar_email);

        final EditText edtEmail = (EditText) findViewById(R.id.edtEmail);
        Button btEnviar = (Button) findViewById(R.id.btEnviar);

        btEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<candidato> candidatos = (ArrayList<candidato>)
                        getIntent().getSerializableExtra("candidatos");
                String[] enderecos = new String[1];
                enderecos[0] = edtEmail.getText().toString();
                CandidatoDAO adao = new CandidatoDAO();

                comporEmail(enderecos,"Lista de Candidatos", adao.montaConteudoEmail(candidatos));
            }
        });

    }

    public void comporEmail(String[] enderecos, String assunto, String conteudo/*, Uri attachment*/) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, enderecos);
        intent.putExtra(Intent.EXTRA_SUBJECT, assunto);
        intent.putExtra(Intent.EXTRA_TEXT,conteudo);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}