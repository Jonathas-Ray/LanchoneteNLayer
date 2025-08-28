package br.com.lanche.services;

import br.com.lanche.interfaces.LancheService;
import br.com.lanche.models.Lanche;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class LancheServiceImpl implements LancheService {

    @Override
    public String salvarImagem(Lanche lanche) throws IOException {
        Path pastaDestino = Paths.get("src", "main", "java", "br", "com", "lanche", "imagens");

        String extensao = "";
        int i = lanche.getCaminhoImagem().lastIndexOf('.');
        if (i > 0 && i < lanche.getCaminhoImagem().length() - 1) {
            extensao = lanche.getCaminhoImagem().substring(i).toLowerCase();
        } else {
            extensao = ".img";
        }

        String nomeLanche = lanche.getNome().trim().replaceAll("[^a-zA-Z0-9_-]", "_");

        Path destino = pastaDestino.resolve(nomeLanche + extensao);

        Files.copy(Paths.get(lanche.getCaminhoImagem()), destino, StandardCopyOption.REPLACE_EXISTING);
        return destino.toString();
    }

    @Override
    public void excluirImagem(Lanche lanche) {
        try {
            String caminhoImagem = lanche.getCaminhoImagem();
            if (caminhoImagem != null && !caminhoImagem.isEmpty()) {
                File arquivo = new File(caminhoImagem);
                if (arquivo.exists()) {
                    arquivo.delete();
                    lanche.setCaminhoImagem(null);
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao excluir imagem: " + e.getMessage());
        }

    }
}
