package com.example.ecsite.repository;

// Areaエンティティを操作するためのリポジトリインターフェース
// JpaRepositoryを継承することで、標準的なCRUD操作（検索・登録・更新・削除）が自動的に利用可能になる
import com.example.ecsite.entity.Area;
import org.springframework.data.jpa.repository.JpaRepository;

// AreaRepositoryはAreaエンティティに対するDBアクセスを担当
public interface AreaRepository extends JpaRepository<Area, Long> {
    // 追加のクエリメソッドを定義する場合はここに記述
}
