package br.ce.wcaquino.taskbackend.controller;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.ce.wcaquino.taskbackend.model.Task;
import br.ce.wcaquino.taskbackend.repo.TaskRepo;
import br.ce.wcaquino.taskbackend.utils.ValidationException;

public class TaskControllerTest {

    @InjectMocks
    private TaskController controller;

    @Mock
    private TaskRepo taskRepo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void naodeveSalvarTarefaSemDescricao() {
        Task todo= new Task();
        //todo.setTask("Descrição");
        todo.setDueDate(LocalDate.now());
        
        try {
			controller.save(todo);
		} catch (ValidationException e) {
            assertEquals("Fill the task description", e.getMessage());
			e.printStackTrace();
		}
    }
    
    @Test
    public void naodeveSalvarTarefaSemData() {
        Task todo= new Task();
        todo.setTask("Descrição");
        //todo.setDueDate(LocalDate.now());
        
        try {
            controller.save(todo);
        } catch (ValidationException e) {
            assertEquals("Fill the due date", e.getMessage());
            e.printStackTrace();
        }
    }
    
    @Test
    public void naodeveSalvarTarefaComDataPassada() {
        Task todo= new Task();
        todo.setTask("Descrição");
        todo.setDueDate(LocalDate.of(2010, 01, 01));
        
        try {
            controller.save(todo);
        } catch (ValidationException e) {
            assertEquals("Due date must not be in past", e.getMessage());
            e.printStackTrace();
        }
        
    }
    
    @Test
    public void deveSalvarTarefaComSucesso() {
        Task todo= new Task();
        todo.setTask("Descrição");
        todo.setDueDate(LocalDate.now());
        
        try {
            controller.save(todo);
        } catch (ValidationException e) {            
            e.printStackTrace();
        }

        Mockito.verify(taskRepo).save(todo); // Verifica se repositório foi corretamente invocado

    }
}
