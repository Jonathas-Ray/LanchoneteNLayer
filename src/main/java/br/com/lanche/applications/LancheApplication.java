package br.com.lanche.applications;

import br.com.lanche.interfaces.LancheRepository;
import br.com.lanche.interfaces.LancheService;
import br.com.lanche.models.Lanche;

import java.io.IOException;
import java.util.List;

public class LancheApplication {
    private LancheService lancheService;
    private LancheRepository lancheRepository;

    public LancheApplication(LancheRepository lancheRepository, LancheService lancheService) {
        this.lancheService = lancheService;
        this.lancheRepository = lancheRepository;
    }

    public List<Lanche> buscarTodos() throws IOException {
        return this.lancheRepository.buscarTodos();
    }

    public Lanche buscarPorId(int id) throws IOException {
        return this.lancheRepository.buscarPorId(id);
    }

    public void adicionar(Lanche lanche) throws IOException {
        this.lancheRepository.adicionar(lanche);
        if(lanche.getCaminhoImagem() != null) {this.lancheService.salvarImagem(lanche);}
    }

    public void excluir(int id) throws IOException {
        this.lancheService.excluirImagem(lancheRepository.buscarPorId(id));
        this.lancheRepository.excluir(id);
    }

    public void excluirImagem(Lanche lanche) {
        this.lancheService.excluirImagem(lanche);
    }

    public void atualizar(int id, Lanche lanche) throws IOException {
        this.lancheRepository.atualizar(id, lanche);
    }

    public double calcularTotal(Lanche lanche, int quantidade) {
        return lanche.calcularLanche(quantidade);
    }
}
