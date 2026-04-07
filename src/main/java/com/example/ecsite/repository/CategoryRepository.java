package com.example.ecsite.repository;

// Categoryエンティティを操作するためのリポジトリインターフェース
// JpaRepositoryを継承することで、標準的なCRUD操作を自動で利用できる
import com.example.ecsite.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

// Categoryに対するDBアクセスを担当する
public interface CategoryRepository extends JpaRepository<Category, Long> {
	// 必要に応じて独自クエリメソッドを追加する
}
