package com.example.ecsite.repository;

// Productエンティティを操作するためのリポジトリインターフェース
// JpaRepositoryを継承することで、標準的なCRUD操作を自動で利用できる
import com.example.ecsite.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

// 商品情報（Product）に対するDBアクセスを担当する
public interface ProductRepository extends JpaRepository<Product, Long> {
	// 必要に応じて独自クエリメソッドを追加する
}
