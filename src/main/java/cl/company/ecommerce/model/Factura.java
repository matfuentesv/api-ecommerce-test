package cl.company.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;



import java.util.List;

@Entity(name = "Factura")
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_factura")
    private String fechaFactura;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Customer cliente;

    @OneToMany(mappedBy = "factura", fetch = FetchType.EAGER)
    private List<Detalle_Factura> detalleFactura;

    @Column(name = "total")
    private Integer total;
    @Column(name = "descuento")
    private Double descuento;
    @Column(name = "total_Descuento")
    private Integer totalDescuento;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFechaFactura() {
        return fechaFactura;
    }

    public void setFechaFactura(String fechaFactura) {
        this.fechaFactura = fechaFactura;
    }



    public Customer getCliente() {
        return cliente;
    }

    public void setCliente(Customer cliente) {
        this.cliente = cliente;
    }

    public List<Detalle_Factura> getDetalleFactura() {
        return detalleFactura;
    }

    public void setDetalleFactura(List<Detalle_Factura> detalleFactura) {
        this.detalleFactura = detalleFactura;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    public Integer getTotalDescuento() {
        return totalDescuento;
    }

    public void setTotalDescuento(Integer totalDescuento) {
        this.totalDescuento = totalDescuento;
    }
}
