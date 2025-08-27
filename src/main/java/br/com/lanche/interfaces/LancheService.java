package br.com.lanche.interfaces;

import br.com.lanche.models.Lanche;

import java.io.IOException;

public interface LancheService {
    public String salvarImagem(Lanche lanche) throws IOException;
    public void excluirImagem(Lanche lanche);
}
