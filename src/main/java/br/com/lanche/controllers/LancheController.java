package controllers;

import applications.LancheApplication;
import services.LancheServiceImpl;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import interfaces.*;
import repositories.LancheRepositoryImpl;
import facades.LancheFacade;
import models.Lanche;

@RestController
@RequestMapping("/api/lanches")
public class LancheController {

    private LancheRepository lancheRepositoryImpl;
    private LancheService lancheService;
    private LancheApplication lancheApplication;
    private LancheFacade lancheFacade;
    private Scanner scanner;

    public void injetarDependencias() {
        lancheRepositoryImpl = new LancheRepositoryImpl();
        lancheService = new LancheServiceImpl();
        lancheApplication = new LancheApplication(lancheRepositoryImpl, lancheService);
        lancheFacade = new LancheFacade(lancheApplication);
        scanner = new Scanner(System.in);
    }

    public LancheController() throws IOException {
        injetarDependencias();
    }

    @GetMapping("/")
    public List<Lanche> getLanches() throws IOException {
        return this.lancheFacade.buscarTodos();
    }

    @GetMapping("/{id}")
    public Lanche getLanche(@PathVariable int id) throws IOException{
        return this.lancheFacade.buscarPorId(id);
    }

    @PostMapping("/adicionar/produto")
    public void adicionarProduto(@RequestBody Lanche lanche) throws IOException{
        this.lancheFacade.adicionar(lanche);
    }

    @PostMapping("/venda/{idProduto}/quantidade/{quantidade}")
    public String vender(@PathVariable int idProduto,
                         @PathVariable int quantidade) throws IOException {
        Lanche lanche = this.lancheFacade.buscarPorId(idProduto);

        return String.valueOf(this.lancheFacade.calcularTotal(lanche, quantidade));
    }

    @PutMapping("/alterar/{id}")
    public void alterarProduto(@PathVariable int id, @RequestBody Lanche lanche) throws IOException{
        this.lancheFacade.atualizar(id, lanche);
    }

    @DeleteMapping("/{id}")
    public void deletarLanche(@PathVariable int id) throws IOException{
        this.lancheFacade.excluir(id);
    }
}
