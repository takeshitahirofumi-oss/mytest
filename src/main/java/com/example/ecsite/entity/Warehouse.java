package com.example.ecsite.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "warehouse")
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long warehouseId;
    private String warehouseName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id")
    private Area area;
    @OneToMany(mappedBy = "warehouse", fetch = FetchType.LAZY)
    private List<Stock> stocks;
    public Warehouse() {}
    // getter/setter
    public Long getWarehouseId() { return warehouseId; }
    public void setWarehouseId(Long warehouseId) { this.warehouseId = warehouseId; }
    public String getWarehouseName() { return warehouseName; }
    public void setWarehouseName(String warehouseName) { this.warehouseName = warehouseName; }
    public Area getArea() { return area; }
    public void setArea(Area area) { this.area = area; }
    public List<Stock> getStocks() { return stocks; }
    public void setStocks(List<Stock> stocks) { this.stocks = stocks; }
}
