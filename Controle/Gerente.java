package com.pac.elections.Controle;

import android.content.res.Resources;

import com.pac.elections.DAO.EleitorDAO;
import com.pac.elections.DAO.CandidatoDAO;
import com.pac.elections.Modelo.eleitor;
import com.pac.elections.Modelo.candidato;

import java.util.ArrayList;

public class Gerente {

    ArrayList<eleitor> eleitores = new ArrayList<eleitor>();

    ArrayList<candidato> candidatos = new ArrayList<candidato>();

    public ArrayList<candidato> getCandidatos() {
        return candidatos;
    }

    public void setCandidatos(ArrayList<candidato> candidatos) {
        this.candidatos = candidatos;
    }


    public ArrayList<eleitor> getEleitores() {
        return eleitores;
    }

    public void setEleitores(ArrayList<eleitor> eleitores) {
        this.eleitores = eleitores;
    }

    public String consultaEleitores (Resources rec){

        EleitorDAO edao = new EleitorDAO();

        edao.setArquivo("eleitores.txt");
        edao.setRecurso(rec);
        return edao.consultaTodos(eleitores);
    }
    public String consultaCandidatos (Resources rec){

        CandidatoDAO cdao = new CandidatoDAO();

        cdao.setArquivo("candidatos.txt");
        cdao.setRecurso(rec);
        return cdao.consultaTodos(candidatos);
    }
    public eleitor consultaPorTitulo (int titulo) {
        for (int i = 0; i < eleitores.size(); i++) {
            if (eleitores.get(i).getTitulo() == titulo) {
                return eleitores.get(i);
            }
        }
        return null;
    }

    public candidato consultaPorNumero (int num){

        for(int i = 0; i < candidatos.size(); i++)
        {
            if (candidatos.get(i).getNumero() == num){

                return candidatos.get(i);
            }
        }
        return null;
    }

    public void atualizarEleitor (eleitor ele)
    {
        boolean achei = false;
        int i = 0;

        while (!achei && (i < eleitores.size()))
        {
            if (eleitores.get(i).getNome().equals(ele.getNome())) achei = true;
            i++;
        }
        if (achei)
        {
            i--;
            eleitores.set(i,ele);
        }
    }

    public void atualizarCandidato (candidato cand)
    {
        boolean achei = false;
        int i = 0;

        while (!achei && (i < candidatos.size()))
        {
            if (candidatos.get(i).getNome().equals(cand.getNome())) achei = true;
            i++;
        }
        if (achei)
        {
            i--;
            candidatos.set(i,cand);
        }
    }

    public boolean verificar(){
        boolean votou = true;
        for (int i = 0; votou && i < eleitores.size(); i++ ){
            votou = eleitores.get(i).isVotou();
        }
        return votou;
    }
}
