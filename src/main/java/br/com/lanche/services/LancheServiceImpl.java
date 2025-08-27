package br.com.lanche.services;

import br.com.lanche.interfaces.LancheService;
import br.com.lanche.models.Lanche;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static java.nio.file.Files.exists;

public class LancheServiceImpl implements LancheService {
    private static final String PASTA_IMAGENS = "produto_imagens/";

    @Override
    public String salvarImagem(Lanche lanche) throws IOException {
        String caminhoImagem = lanche.getCaminhoImagem();

        Files.createDirectories(Paths.get(PASTA_IMAGENS));

        String nomeArquivo = Paths.get(caminhoImagem).getFileName().toString();

        Path destino = Paths.get(PASTA_IMAGENS + nomeArquivo);

        Files.copy(Paths.get(caminhoImagem), destino, StandardCopyOption.REPLACE_EXISTING);

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
