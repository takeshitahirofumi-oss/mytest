package com.example.ecsite.repository;

// Warehouseエンティティを操作するためのリポジトリインターフェース
// JpaRepositoryを継承することで、標準的なCRUD操作を自動で利用できる
import com.example.ecsite.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

// 倉庫情報（Warehouse）に対するDBアクセスを担当する
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
	// 必要に応じて独自クエリメソッドを追加する
}
