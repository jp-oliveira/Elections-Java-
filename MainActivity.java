package com.pac.elections;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pac.elections.Controle.Gerente;
import com.pac.elections.Modelo.candidato;
import com.pac.elections.Modelo.eleitor;

public class MainActivity extends AppCompatActivity {

    Gerente ger = new Gerente();
    static final int ACTIVITY_VOTAR_REQUEST = 1;

    eleitor ele = null;
    EditText edtTitulo;
    TextView tvNomeEleitor;
    Button btVotar;
    boolean fim_votacao = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        edtTitulo = (EditText) findViewById(R.id.edtTitulo);
        final Button btSelecionar = (Button) findViewById(R.id.btSelecionar);
        Button btFinalizar = (Button) findViewById(R.id.btFinalizar);
        btVotar = (Button) findViewById(R.id.btVotar);

        tvNomeEleitor = (TextView) findViewById(R.id.tvNomeEleitor);

        btSelecionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!fim_votacao){
                    if(edtTitulo.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(),"Digite o título de eleitor", Toast.LENGTH_LONG).show();
                    }
                    else{
                        int titulo = Integer.parseInt(edtTitulo.getText().toString());
                        ele = ger.consultaPorTitulo(titulo);
                        if (ele == null)
                            Toast.makeText(getApplicationContext(),"Título inexistente!!!", Toast.LENGTH_LONG).show();
                        else {
                            if(ele.isVotou() == false) {
                                tvNomeEleitor.setText("Eleitor: " + ele.getNome());
                                btVotar.setVisibility(View.VISIBLE);
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Eleitor já votou!!!", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"VOTAÇÃO ENCERRADA, por favor envie o e-mail!!!", Toast.LENGTH_LONG).show();
                    btVotar.setVisibility(View.INVISIBLE);
                }
            }
        });

        btFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fim_votacao = true;
                Toast.makeText(getApplicationContext(),"Fim da votação, por favor mande o e-mail!!!", Toast.LENGTH_LONG).show();
            }
        });

        btVotar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                telaVotar(v);
                }

            });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(fim_votacao){
                    Toast.makeText(getApplicationContext(),"Indo pra tela de e-mail!!!", Toast.LENGTH_LONG).show();
                    telaEnviarEmail(view);
                }
                else{
                    Toast.makeText(getApplicationContext(),"A votação ainda não foi finalizada!!!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void telaEnviarEmail(View view){
        Intent intent = new Intent(this, ActivityEnviarEmail.class);
        intent.putExtra("candidatos",ger.getCandidatos());
        startActivity(intent);
    }

    public void telaVotar(View view){
        edtTitulo.setText("");
        tvNomeEleitor.setText("Eleitor: ");
        btVotar.setVisibility(View.INVISIBLE);
        Intent i = new Intent(this, ActivityVotar.class);
        i.putExtra("candidatos",ger.getCandidatos());
        i.putExtra("eleitor",ele);
        startActivityForResult(i, ACTIVITY_VOTAR_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == ACTIVITY_VOTAR_REQUEST) {
            if(resultCode == RESULT_OK){
                eleitor ele = (eleitor) data.getSerializableExtra("eleitor");
                candidato cand = (candidato) data.getSerializableExtra("candidato");

                ger.atualizarEleitor(ele);
                ger.atualizarCandidato(cand);
                fim_votacao = ger.verificar();
                if(fim_votacao){
                    Toast.makeText(getApplicationContext(),"Fim da votação, por favor mande o e-mail!!!", Toast.LENGTH_LONG).show();
                }

            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_eleitores) {

            Resources res = getResources();
            String result = ger.consultaEleitores(res);
            Toast.makeText(getApplicationContext(),result, Toast.LENGTH_LONG).show();
            //Toast.makeText(getApplicationContext(),ger.getEleitores().get(0).getNome(), Toast.LENGTH_LONG).show();
            return true;
        }

        if (id == R.id.action_candidatos) {

            Resources res = getResources();
            String result = ger.consultaCandidatos(res);
            Toast.makeText(getApplicationContext(),result, Toast.LENGTH_LONG).show();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
