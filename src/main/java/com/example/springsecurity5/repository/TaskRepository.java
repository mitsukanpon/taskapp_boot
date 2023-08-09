package com.example.springsecurity5.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springsecurity5.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, String> {

    // ユーザIDと一致するタスク一覧
    List<Task> findByUserName(String userName);

    // 完了フラグ（１⇨完了、０⇨未完了）で検索
    List<Task> findByUserNameAndCompleteFlg(String userName, int completeFlg);

}
