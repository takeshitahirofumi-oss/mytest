package com.example.ecsite.repository;

// Stockエンティティを操作するためのリポジトリインターフェース
// JpaRepositoryを継承することで、標準的なCRUD操作を自動で利用できる
import com.example.ecsite.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

// 在庫情報（Stock）に対するDBアクセスを担当する
public interface StockRepository extends JpaRepository<Stock, Long> {
	// 必要に応じて独自クエリメソッドを追加する
}
