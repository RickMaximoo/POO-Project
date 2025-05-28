package ManagerProject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class CodigoPrincipal {
    private static Scanner sc = new Scanner(System.in);
    private static Usuario usuarioLogado;

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== Bem-vindo ao Sistema de Pedidos ===");
            System.out.println("1. Cadastrar usuário");
            System.out.println("2. Login");
            System.out.println("0. Sair");
            System.out.print("Opção: ");
            String op = sc.nextLine();

            if (op.equals("1")) {
                cadastrarUsuario();
            } else if (op.equals("2")) {
                if (login()) {
                    menuPrincipal();
                }
            } else if (op.equals("0")) {
                System.out.println("Encerrando...");
                break;
            } else {
                System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }

    private static boolean login() {
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Senha: ");
        String senha = sc.nextLine();

        String sql = "SELECT id,nome,email,senha FROM Usuario WHERE email=? AND senha=?";
        try (Connection c = ConexaoBd.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, senha);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                usuarioLogado = new Usuario();
                usuarioLogado.setId(rs.getInt("id"));
                usuarioLogado.setNome(rs.getString("nome"));
                usuarioLogado.setEmail(rs.getString("email"));
                usuarioLogado.setSenha(rs.getString("senha"));

                System.out.println("Bem-vindo, " + usuarioLogado.getNome() + "!\n");
                return true;
            } else {
                System.out.println("Credenciais inválidas.\n");
            }
        } catch (SQLException e) {
            System.out.println("Erro no login: " + e.getMessage());
        }
        return false;
    }

    private static void menuPrincipal() {
        while (true) {
            System.out.println("--- Menu Principal (Usuário Logado) ---");
            System.out.println("Usuário Logado: " + usuarioLogado.getNome());
            System.out.println("1. Cadastrar cliente");
            System.out.println("2. Listar clientes");
            System.out.println("3. Cadastrar produto");
            System.out.println("4. Listar produtos");
            System.out.println("5. Criar novo pedido");
            System.out.println("6. Listar pedidos realizados");
            System.out.println("0. Logout");
            System.out.print("Digite sua opção: ");
            String escolha = sc.nextLine();

            switch (escolha) {
                case "1": cadastrarCliente(); break;
                case "2": listarClientes(); break;
                case "3": cadastrarProduto(); break;
                case "4": listarProdutos(); break;
                case "5": criarPedido(); break;
                case "6": listarPedidos(); break;
                case "0": System.out.println("Logout efetuado.\n"); return;
                default: System.out.println("Opção inválida! Tente novamente.\n");
            }
        }
    }

    private static void cadastrarUsuario() {
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Senha: ");
        String senha = sc.nextLine();

        String sql = "INSERT INTO Usuario(nome,email,senha) VALUES(?,?,?)";
        try (Connection c = ConexaoBd.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, nome);
            ps.setString(2, email);
            ps.setString(3, senha);
            ps.executeUpdate();
            System.out.println("Usuário cadastrado!\n");
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar usuário: " + e.getMessage());
        }
    }

    private static void cadastrarCliente() {
        System.out.println("\n-- Cadastro de Cliente (0 para voltar) --");
        System.out.print("Nome do cliente: ");
        String nome = sc.nextLine(); if (nome.equals("0")) { System.out.println("Retornando ao menu...\n"); return; }
        System.out.print("Email do cliente: ");
        String email = sc.nextLine();

        String sql = "INSERT INTO Cliente(nome,email) VALUES(?,?)";
        try (Connection c = ConexaoBd.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, nome);
            ps.setString(2, email);
            ps.executeUpdate();
            System.out.println("Cliente cadastrado!\n");
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar cliente: " + e.getMessage());
        }
    }

    private static void listarClientes() {
        String sql = "SELECT id,nome,email FROM Cliente";
        try (Connection c = ConexaoBd.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            System.out.println("ID | Nome | Email");
            while (rs.next()) {
                System.out.printf("%d | %s | %s%n", rs.getInt(1), rs.getString(2), rs.getString(3));
            }
            System.out.println();
            System.out.print("Pressione ENTER para continuar..."); sc.nextLine();
            clearScreen();
        } catch (SQLException e) {
            System.out.println("Erro ao listar clientes: " + e.getMessage());
        }
    }

    private static void cadastrarProduto() {
        System.out.println("\n-- Cadastro de Produto (0 para voltar) --");
        System.out.print("Nome do produto: ");
        String nome = sc.nextLine(); if (nome.equals("0")) { System.out.println("Retornando ao menu...\n"); return; }
        System.out.print("Descrição: "); String desc = sc.nextLine();
        System.out.print("Valor (ex: 10,50 ou 3.000): ");
        String txt = sc.nextLine().trim().replace(".", "").replace(",", ".");
        double valor = Double.parseDouble(txt);
        System.out.print("Quantidade em estoque: "); int qt = Integer.parseInt(sc.nextLine());

        String sql = "INSERT INTO Produto(nome,descricao,valor,quantidade_estoque) VALUES(?,?,?,?)";
        try (Connection c = ConexaoBd.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, nome); ps.setString(2, desc); ps.setDouble(3, valor); ps.setInt(4, qt);
            ps.executeUpdate(); System.out.println("Produto cadastrado!\n");
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar produto: " + e.getMessage());
        }
    }

    private static void listarProdutos() {
        String sql = "SELECT id,nome,valor,quantidade_estoque FROM Produto";
        try (Connection c = ConexaoBd.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            System.out.println("ID | Nome | Valor | Estoque");
            while (rs.next()) {
                System.out.printf("%d | %s | %.2f | %d%n",
                    rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getInt(4));
            }
            System.out.println();
            System.out.print("Pressione ENTER para continuar..."); sc.nextLine();
            clearScreen();
        } catch (SQLException e) {
            System.out.println("Erro ao listar produtos: " + e.getMessage());
        }
    }

    private static void criarPedido() {
        try (Connection c = ConexaoBd.getConnection()) {
            listarClientes();
            System.out.print("ID do cliente: "); int clienteId = Integer.parseInt(sc.nextLine());

            String insPedido = "INSERT INTO Pedido(cliente_id, total) VALUES(?, 0)";
            PreparedStatement psP = c.prepareStatement(insPedido, Statement.RETURN_GENERATED_KEYS);
            psP.setInt(1, clienteId); psP.executeUpdate();
            ResultSet rsKey = psP.getGeneratedKeys(); rsKey.next(); int pedidoId = rsKey.getInt(1);

            double total = 0; boolean adicionar = true;
            while (adicionar) {
                listarProdutos();
                System.out.print("ID do produto (0 para encerrar): ");
                int prodId = Integer.parseInt(sc.nextLine()); if (prodId == 0) break;
                System.out.print("Quantidade: "); int qtd = Integer.parseInt(sc.nextLine());
                String sel = "SELECT quantidade_estoque, valor FROM Produto WHERE id=?";
                PreparedStatement psSel = c.prepareStatement(sel); psSel.setInt(1, prodId);
                ResultSet rs = psSel.executeQuery();
                if (!rs.next() || qtd > rs.getInt("quantidade_estoque")) {
                    System.out.println("Estoque insuficiente ou produto não existe!");
                } else {
                    double val = rs.getDouble("valor"); double subTotal = val * qtd; total += subTotal;
                    String insItem = "INSERT INTO PedidoItem(pedido_id, produto_id, quantidade, sub_total) VALUES(?,?,?,?)";
                    PreparedStatement psI = c.prepareStatement(insItem);
                    psI.setInt(1, pedidoId); psI.setInt(2, prodId); psI.setInt(3, qtd); psI.setDouble(4, subTotal); psI.executeUpdate();
                    String updEst = "UPDATE Produto SET quantidade_estoque=? WHERE id=?";
                    PreparedStatement psU = c.prepareStatement(updEst);
                    psU.setInt(1, rs.getInt("quantidade_estoque") - qtd); psU.setInt(2, prodId); psU.executeUpdate();
                }
                System.out.print("Deseja adicionar outro produto? (S/N): ");
                if (!sc.nextLine().trim().equalsIgnoreCase("S")) adicionar = false;
            }
            String updTot = "UPDATE Pedido SET total=? WHERE id=?";
            PreparedStatement psT = c.prepareStatement(updTot);
            psT.setDouble(1, total); psT.setInt(2, pedidoId); psT.executeUpdate();

            System.out.printf("Pedido %d criado! Total = R$ %.2f%n%n", pedidoId, total);
            System.out.print("Pressione ENTER para continuar..."); sc.nextLine();
            clearScreen();
        } catch (SQLException e) {
            System.out.println("Erro ao criar pedido: " + e.getMessage());
        }
    }

    private static void listarPedidos() {
        String sql = "SELECT p.id, c.nome AS cliente, p.data_pedido, p.total " +
                     "FROM Pedido p JOIN Cliente c ON p.cliente_id = c.id";
        try (Connection c = ConexaoBd.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            System.out.println("ID | Cliente | Data | Total");
            while (rs.next()) {
                System.out.printf("%d | %s | %s | R$ %.2f%n",
                    rs.getInt("id"), rs.getString("cliente"), rs.getTimestamp("data_pedido").toString(), rs.getDouble("total"));
            }
            System.out.println();
            System.out.print("Pressione ENTER para continuar..."); sc.nextLine();
            clearScreen();
        } catch (SQLException e) {
            System.out.println("Erro ao listar pedidos: " + e.getMessage());
        }
    }

    private static void clearScreen() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            for (int i = 0; i < 50; i++) System.out.println();
        }
    }
}