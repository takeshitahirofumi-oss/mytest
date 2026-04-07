package com.example.ecsite.repository;

// Userエンティティを操作するためのリポジトリインターフェース
// JpaRepositoryを継承することで、標準的なCRUD操作を自動で利用できる
import com.example.ecsite.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

// Userに対するDBアクセスを担当する
public interface UserRepository extends JpaRepository<User, String> {
    // ログインID（userId）でユーザーを1件取得する
    User findByUserId(String userId);
}
