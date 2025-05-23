package ManagerProject;

public class PedidoItem {
    private int produtoId;
    private int quantidade;
    private double subTotal;

    public PedidoItem(int produtoId, int quantidade, double subTotal) {
        this.produtoId = produtoId;
        this.quantidade = quantidade;
        this.subTotal = subTotal;
    }

    public int getProdutoId() { return produtoId; }
    public int getQuantidade() { return quantidade; }
    public double getSubTotal() { return subTotal; }
}