package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author rival
 * @since 2024-01-10
 */

@WebMvcTest(TodoController.class)
class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoService todoService;

    private Todo todo;

    @BeforeEach
    public void setup(){
        this.todo = Todo.builder().title("Test Todo").build();
    }


    @Test
    public void testGetAllTodos() throws Exception {

        TodoDTO el1 = new TodoDTO();
        TodoDTO el2 = new TodoDTO();
        el1.setTitle("Sample 1");
        el1.setId(1L);
        el1.setCompleted(false);

        el2.setTitle("Sample 1");
        el2.setId(1L);
        el2.setCompleted(false);
        List<TodoDTO> todos = List.of(el1,el2);

        TodoListResponse res = new TodoListResponse();
        res.setTodos(todos);



        given(todoService.getAllTodos()).willReturn(res);


        mockMvc.perform(
            get("/v1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.todos").isArray())
            .andExpect(jsonPath("$.todos.size()").value(2));
    }
}