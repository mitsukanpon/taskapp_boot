package com.example.springsecurity5.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "タスク名を入力してください")
    @JsonIgnore
    @Column(name = "title")
    private String title;

    @NotBlank(message = "期限を選択してください")
    @JsonIgnore
    @Column(name = "due_date")
    private String dueDate;

    @JsonIgnore
    @Column(name = "complete_flg")
    private Integer completeFlg;

    @Column(name = "username")
    private String userName;

}
