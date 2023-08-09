package com.example.springsecurity5.dto;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

import com.example.springsecurity5.model.Task;

@Getter
@Setter
@Component
public class TaskListDto {
    // タスクを格納する配列
    private List<Task> inCompleteTaskList;
    private List<Task> completeTaskList;

    // 完了未完了のタスク数を管理する変数
    private Integer cntInComplete;
    private Integer cntComplete;
}
