package interfaces;

import models.Lanche;

import java.util.List;

public interface LancheRepository {
    public List<Lanche> buscarTodos();
    public Lanche buscarPorId(int id);
    public void adicionar(Lanche lanche);
    public void excluir(int id);
    public void atualizar(int id, Lanche lanche);
}
