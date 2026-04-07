package com.example.ecsite.repository;

// ProductImageエンティティを操作するためのリポジトリインターフェース
// JpaRepositoryを継承することで、標準的なCRUD操作を自動で利用できる
import com.example.ecsite.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

// 商品画像情報（ProductImage）に対するDBアクセスを担当する
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
	// 必要に応じて独自クエリメソッドを追加する
}
