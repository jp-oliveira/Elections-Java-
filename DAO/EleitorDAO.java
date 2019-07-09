package com.pac.elections.DAO;

import android.content.res.AssetManager;
import android.content.res.Resources;

import java.util.ArrayList;
import java.io.*;
import com.pac.elections.Modelo.eleitor;

public class EleitorDAO {

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

    public String consultaTodos (ArrayList<eleitor> eleitores)
    {
        try {
            AssetManager assetManager = recurso.getAssets();
            InputStream is = assetManager.open(arquivo);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            eleitor e;

            String s = br.readLine(); // primeira linha

            while (s != null) {
                if (!s.equals("**************************"))
                {
                    e = new eleitor();
                    e.setNome(s.substring(9));
                    s = br.readLine();
                    e.setTitulo(Integer.parseInt(s.substring(8)));
                    s = br.readLine();
                    if (s.substring(7).equals("falso"))
                        e.setVotou(false);
                    else e.setVotou(true);
                    eleitores.add(e);
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
        return "Arquivo de eleitores lido com sucesso";
    }
	/*
	public void gravaTodos (ArrayList<Acesso> acessos)
	{
		try
		{
			OutputStream os = new FileOutputStream (arquivo);
			OutputStreamWriter osw = new OutputStreamWriter(os);
			BufferedWriter bw = new BufferedWriter(osw);

			for (int i = 0; i < acessos.size(); i++)
			{
				bw.write("**************************");
				bw.newLine();
				bw.write("nome: " + acessos.get(i).getNome());
				bw.newLine();
				bw.write("url: " + acessos.get(i).getUrl());
				bw.newLine();
				bw.write("usuario: " + acessos.get(i).getUsuario());
				bw.newLine();
				acessos.get(i).criptoSenha();
				bw.write("senha: " + acessos.get(i).getSenha_criptografada());
				bw.newLine();
			}
			bw.write("**************************");
			bw.close();
		}
		catch (IOException x)
		{
			JOptionPane.showMessageDialog(null, "Erro \n" + x);
		}
	}*/

}
