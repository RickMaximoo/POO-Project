package ManagerProject;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private int id;
    private int clienteId;
    private List<PedidoItem> itens = new ArrayList<>();
    private double total;

    public Pedido(int clienteId) {
        this.clienteId = clienteId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getClienteId() { return clienteId; }

    public List<PedidoItem> getItens() { return itens; }

    public double getTotal() { return total; }

    public void addItem(PedidoItem item) {
        itens.add(item);
        total += item.getSubTotal();
    }
}