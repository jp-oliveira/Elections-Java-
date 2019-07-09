package com.pac.elections.DAO;

import android.content.res.AssetManager;
import android.content.res.Resources;

import com.pac.elections.Modelo.candidato;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CandidatoDAO {

    String arquivo = "";
    Resources recurso;

    public Resources getRecurso() {
        return recurso;
    }

    public void setRecurso(Resources recurso) {
        this.recurso = recurso;
    }

    public String getArquivo() {
        return arquivo;
    }

    public void setArquivo(String arquivo) {
        this.arquivo = arquivo;
    }

    public String consultaTodos (ArrayList<candidato> candidatos)
    {
        try {
            AssetManager assetManager = recurso.getAssets();
            InputStream is = assetManager.open(arquivo);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            candidato c;

            String s = br.readLine(); // primeira linha

            while (s != null) {
                if (!s.equals("**************************"))
                {
                    c = new candidato();
                    c.setNome(s.substring(11));
                    s = br.readLine();
                    c.setNumero(Integer.parseInt(s.substring(8)));
                    s = br.readLine();
                    c.setPartido(s.substring(9));
                    s = br.readLine();
                    c.setVotos(Integer.parseInt(s.substring(7)));
                    candidatos.add(c);
                }
                s = br.readLine();
            }
            br.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            return "Arquivo não encontrado";
        } catch (IOException e) {
            // TODO Auto-generated catch block
            return "Erro na leitura do arquivo";
        }
        return "Arquivo de candidatos lido com sucesso";
    }

    public String montaConteudoEmail (ArrayList<candidato> candidatos)
    {
        String conteudo = "";
        for (int i = 0; i < candidatos.size(); i++)
        {
            conteudo = conteudo + "**************************\n";
            conteudo = conteudo + "candidato: " + candidatos.get(i).getNome() + "\n";
            conteudo = conteudo + "numero: " + candidatos.get(i).getNumero() + "\n";
            conteudo = conteudo + "partido: " + candidatos.get(i).getPartido() + "\n";
            conteudo = conteudo + "votos: " + candidatos.get(i).getVotos() + "\n";

        }
        conteudo = conteudo + "**************************";
        return conteudo;
    }
}
