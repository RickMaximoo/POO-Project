package ManagerProject;

import java.sql.*;
import java.util.Scanner;

public class CodigoPrincipal {
    private static Scanner sc = new Scanner(System.in);
    private static Usuario usuarioLogado;

    public static void main(String[] args) {
        while (true) {
            System.out.println("1. Cadastrar usuário\n2. Login\n0. Sair");
            String op = sc.nextLine();
            if (op.equals("1")) cadastrarUsuario();
            else if (op.equals("2") && login()) menuPrincipal();
            else if (op.equals("0")) break;
            else System.out.println("Opção inválida!\n");
        }
    }

    private static void cadastrarUsuario() {
        try (Connection c = ConexaoBd.getConnection()) {
            System.out.print("Nome: "); String nome = sc.nextLine();
            System.out.print("Email: ");String email = sc.nextLine();
            System.out.print("Senha: ");String senha = sc.nextLine();
            PreparedStatement ps = c.prepareStatement(
                "INSERT INTO Usuario(nome,email,senha) VALUES(?,?,?)");
            ps.setString(1, nome);
            ps.setString(2, email);
            ps.setString(3, senha);
            ps.executeUpdate();
            System.out.println("Usuário cadastrado!\n");
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static boolean login() {
        try (Connection c = ConexaoBd.getConnection()) {
            System.out.print("Email: ");String email = sc.nextLine();
            System.out.print("Senha: ");String senha = sc.nextLine();
            PreparedStatement ps = c.prepareStatement(
                "SELECT * FROM Usuario WHERE email=? AND senha=?");
            ps.setString(1, email);
            ps.setString(2, senha);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                usuarioLogado = new Usuario();
                usuarioLogado.setId(rs.getInt("id"));
                usuarioLogado.setNome(rs.getString("nome"));
                System.out.println("Bem-vindo, " + usuarioLogado.getNome() + "!\n");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
        System.out.println("Credenciais inválidas!\n");
        return false;
    }

    private static void menuPrincipal() {
        System.out.println("--- Menu Principal ---");
    }
}
