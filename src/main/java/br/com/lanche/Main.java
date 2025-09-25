import applications.LancheApplication;
import facades.LancheFacade;
import interfaces.*;
import repositories.LancheRepositoryImpl;
import services.LancheServiceImpl;
import models.Lanche; // MAIN -> INTERFACE? -> FACADE -> APPLICATION -> SERVICE -> REPOSITORY -> MODELS

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

//VERIFICAR IMPLEMENTAÇÃO DA OPÇÃO DE EXCLUIR IMAGEM SEM EXCLUIR PRODUTO

public class Main {
    private static LancheRepository lancheRepositoryImpl;
    private static LancheService lancheServiceImpl;
    private static LancheApplication lancheApplication;
    private static LancheFacade lancheFacade;
    private static Scanner scanner;

    public static void injetarDependencias() {
        lancheRepositoryImpl = new LancheRepositoryImpl();
        lancheServiceImpl = new LancheServiceImpl();
        lancheApplication = new LancheApplication(lancheRepositoryImpl, lancheServiceImpl);
        lancheFacade = new LancheFacade(lancheApplication);
        scanner = new Scanner(System.in);
    }

    public static void exibirMenu() {
        System.out.println("1 - Listar Produtos");
        System.out.println("2 - Cadastrar Produto");
        System.out.println("3 - Editar Produto");
        System.out.println("4 - Excluir Produto");
        System.out.println("5 - Vender");
        System.out.println("0 - Sair do sistema");
    }

    public static int solicitaOpcaoMenu() {
        System.out.println("Informe a opção escolha: ");
        return scanner.nextInt();
    }

    public static void listarLanches() throws IOException {
        System.out.println("Lista de Produtos:\n(ID -- Nome -- Preço)\n");
        lancheFacade.buscarTodos().forEach(l -> {
            System.out.println(l);
        });
    }

    public static String solicitaEnderecoImagem() { //VERIFICAR SE QUEBRA O PRINCÍPIO DA RESPONSABILIDADE
        String caminhoImagem = null;
        System.out.println("Deseja adicionar/alterar uma imagem ao produto? (1 - Sim/0- N)");
        if (scanner.nextInt() == 1) {
            do {
                System.out.print("Digite o caminho completo da imagem (ex: C:\\pasta\\hamburguer.jpg): ");
                caminhoImagem = scanner.nextLine();

                if (!new File(caminhoImagem).exists()) {
                    System.out.println("Arquivo não encontrado! Digite novamente.");
                }
            } while (!new File(caminhoImagem).exists());
        }
        return caminhoImagem;
    }

    public static void cadastrarLanche() throws IOException {
        System.out.println("ID do produto: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Nome do produto: ");
        String nome = scanner.nextLine();

        System.out.println("Valor do produto: ");
        double preco = scanner.nextDouble();
        scanner.nextLine();

        String caminhoImagem =  solicitaEnderecoImagem(); //VERIFICAR SE QUEBRA O PRINCÍPIO DA RESPONSABILIDADE ^

        Lanche lanche = new Lanche(id, nome, preco, caminhoImagem);
        lancheFacade.adicionar(lanche);
    }

    public static void atualizarLanche() throws IOException {
        System.out.println("ID do produto que será editado: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Lanche lanche;
        System.out.println("O que deseja alterar:\n" +
                "1 - Nome\n" +
                "2 - Preço\n" +
                "3 - Imagem\n");
        switch (solicitaOpcaoMenu()){
            case 1:
                System.out.println("Novo Nome do produto: ");
                String nome = scanner.nextLine();
                lanche = new Lanche(
                        id,
                        nome,
                        lancheFacade.buscarPorId(id).getPreco(),
                        lancheFacade.buscarPorId(id).getCaminhoImagem());
                break;
            case 2:
                System.out.println("Novo Preco do produto: ");
                double preco = scanner.nextDouble();
                scanner.nextLine();
                lanche = new Lanche(
                        id,
                        lancheFacade.buscarPorId(id).getNome(),
                        preco,
                        lancheFacade.buscarPorId(id).getCaminhoImagem());
                break;
            case 3:
                System.out.println("Deseja enviar nova imagem ou excluir a existente?\n" +
                        "1 - Enviar nova imagem\n" +
                        "2 - Excluir imagem\n");
                if (scanner.nextInt() == 1) {
                    String caminhoImagem = solicitaEnderecoImagem();
                    lanche = new Lanche(
                            id,
                            lancheFacade.buscarPorId(id).getNome(),
                            lancheFacade.buscarPorId(id).getPreco(),
                            caminhoImagem);
                } else {
                    lancheFacade.excluirImagem(lancheFacade.buscarPorId(id));
                    lanche = new Lanche(
                            id,
                            lancheFacade.buscarPorId(id).getNome(),
                            lancheFacade.buscarPorId(id).getPreco(),
                            null);
                }
                break;
            default:
                System.out.println("Opção inválida.");
                return; //Sai sem atualizar
        }
        lancheFacade.atualizar(id, lanche);
    }

    public static void excluirLanche() throws IOException {
        System.out.println("ID do produto que será excluído: ");
        int id = scanner.nextInt();

        lancheFacade.excluir(id);
    }

    public static void venderLanche() throws IOException {
        System.out.println("ID do produto que será vendido: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Quantidade que será vendida: ");
        int quantidade = scanner.nextInt();
        scanner.nextLine();

        Lanche lanche = lancheFacade.buscarPorId(id);

        double total = lancheFacade.calcularTotal(lanche, quantidade);
        System.out.println("Total do lanche: " + total);
    }


    public static void iniciarSistema() throws IOException {
        int opcaoMenu = -1;

        do {
            exibirMenu();

            opcaoMenu = solicitaOpcaoMenu();

            switch (opcaoMenu) {
                case 1:
                    listarLanches();
                    break;
                case 2:
                    cadastrarLanche();
                    break;
                case 3:
                    atualizarLanche();
                    break;
                case 4:
                    excluirLanche();
                    break;
                case 5:
                    venderLanche();
                    break;
                default:
                    break;
            }
        } while (opcaoMenu != 0);
    }

    public static void main(String[] args) throws IOException {
        injetarDependencias();
        iniciarSistema();
    }
}