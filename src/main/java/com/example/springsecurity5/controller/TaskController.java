package com.example.springsecurity5.controller;

import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.example.springsecurity5.dto.TaskListDto;
import com.example.springsecurity5.model.Task;
import com.example.springsecurity5.repository.TaskRepository;

@Controller
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskListDto taskListDto;

    private List<Task> deleteTaskList = new ArrayList<>();

    @GetMapping(value = "/list")
    public String showTaskList(Model model, @AuthenticationPrincipal UserDetails user) { // Principal principal
        // 完了、未完了のフラグを取得し、どちらの一覧を取得するのか判定するのに用いる
        String userName = user.getUsername();
        // 完了タスク、未完了タスクをそれぞれ取得する
        List<Task> inCompleteTaskList = taskRepository.findByUserNameAndCompleteFlg(userName, 0);
        List<Task> completeTaskList = taskRepository.findByUserNameAndCompleteFlg(userName, 1);
        // 取得したリストをセットする
        taskListDto.setInCompleteTaskList(inCompleteTaskList);
        taskListDto.setCompleteTaskList(completeTaskList);
        // 取得したリストの要素数をそれぞれセットする
        taskListDto.setCntInComplete(inCompleteTaskList.size());
        taskListDto.setCntComplete(completeTaskList.size());

        model.addAttribute("taskListDto", taskListDto);
        return "task/list";
    }

    @GetMapping(value = "/add")
    public String insertTask(@ModelAttribute("task") Task task) {
        return "task/add";
    }

    @PostMapping(value = "/add")
    public String submitTask(@Validated @ModelAttribute("task") Task task, BindingResult result,
            @AuthenticationPrincipal UserDetails user, Model model) {
        // ログイン中のユーザ名を取得する
        String userName = user.getUsername();
        task.setUserName(userName);
        task.setCompleteFlg(0);
        // 足りない項目が存在すればタスク追加画面に戻る
        if (result.hasErrors()) {
            model.addAttribute("task", task);
            return "task/add";
        }
        taskRepository.save(task);

        return "redirect:/task/list";
    }

    @PostMapping(value = "/update")
    public String updateTask(@Validated @ModelAttribute("taskList") TaskListDto taskListDto,
            BindingResult result) {

        // 足りない項目あればリスト画面へリダイレクトする
        if (result.hasErrors()) {
            return "redirect:/task/list";
        }
        // 未完了タスクリストがNULLなら処理をスキップ
        if (taskListDto.getInCompleteTaskList() != null) {
            for (Task task : taskListDto.getInCompleteTaskList()) {
                // タスクのcompleteFlgで処理を判定する
                switch (task.getCompleteFlg()) {
                    case -1:
                        deleteTaskList.add(task);
                        break;
                    default:
                        taskRepository.save(task);
                }
            }
        }
        // 完了タスクリストがNULLなら処理をスキップ
        if (taskListDto.getCompleteTaskList() != null) {
            for (Task task : taskListDto.getCompleteTaskList()) {
                // タスクのcompleteFlgで処理を判定する
                switch (task.getCompleteFlg()) {
                    case -1:
                        deleteTaskList.add(task);
                        break;
                    default:
                        taskRepository.save(task);
                }
            }
        }

        // // 削除フラグが立っているタスクをAPI経由で削除する
        // if (deleteTaskList.size() > 0){
        // String url = "http://localhost:8080/api/task/delete/";
        // RestTemplate restTemplate = new RestTemplate();
        // ResponseEntity<Strnig> response = restTemplate.exchange(url, HttpMethod.POST,
        // HttpEntity<List<Task>> deleteTaskList, String.class);
        // }

        return "redirect:/task/list";
    }
}
