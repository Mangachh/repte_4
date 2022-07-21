package cbs.controller;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import cbs.models.Book;
import cbs.serveis.BookImpl;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc(addFilters = false)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookImpl impl;

    private final static String NAME = "MockName";
    private final static String ID = "5447884";

    @Test
    void insertBook() throws Exception {
        Book book = new Book(ID, NAME);
        String request = String.format("%s?%s=%s&%s=%s", BookController.PREFIX, BookController.NAME_PARAM, NAME,
        BookController.ID_PARAM, ID);

        Mockito.when(impl.insert(book)).thenReturn(book);

        this.mockMvc.perform(MockMvcRequestBuilders.post(request)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("@.name").value(NAME));

    }

    @Test
    void getAll() throws Exception {
        final String request = String.format("%s/%s", BookController.PREFIX, "all");

        Book a = new Book(ID, NAME + "_A");
        Book b = new Book(ID + "l" + "_B");
        List<Book> answer = List.of(a, b);

        Mockito.when(this.impl.findAll()).thenReturn(answer);

        this.mockMvc.perform(MockMvcRequestBuilders.get(request)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpectAll(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("@.[0].name").value(a.getName()))
            .andExpect(MockMvcResultMatchers.jsonPath("@.[1].name").value(b.getName()));

    }

    @Test
    void getByIdSuccess() throws Exception {
        final Book book = new Book(ID, NAME);
        final String request = String.format("%s/%s", BookController.PREFIX, ID);

        Mockito.when(impl.findById(ID)).thenReturn(Optional.of(book));

        this.mockMvc.perform(MockMvcRequestBuilders.get(request)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("@.name").value(book.getName()));
    }

    @Test
    void getByIdFail() throws Exception {
        final String request = String.format("%s/%s", BookController.PREFIX, ID);

        Mockito.when(impl.findById(ID)).thenReturn(Optional.empty());

        this.mockMvc.perform(MockMvcRequestBuilders.get(request)
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
            .andExpect(MockMvcResultMatchers.status().isNotFound());

    }
    
    // aqui faltaria el update
    @Test
    void updateNameSuccess() throws Exception{
        final Book original = new Book(ID, NAME);
        final String newName = "New_" + NAME;
        final Book newAuth = new Book(ID, newName);

        final String request = String.format("%s/%s?%s=%s", BookController.PREFIX, ID, BookController.NAME_PARAM,
                newName);
        System.out.println(request);

        Mockito.when(impl.findById(ID)).thenReturn(Optional.of(original));
        Mockito.when(impl.updateFromId(newAuth, ID)).thenReturn(Optional.of(newAuth));

        this.mockMvc.perform(MockMvcRequestBuilders.put(request)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(newName));;        
    }   
    
    @Test
    void updateFullSuccess() throws Exception {
        final Book original = new Book(ID, NAME);
        final String newName = "New_" + NAME;
        final Book newAuth = new Book(ID, newName);

        final String request = String.format("%s/%s?%s=%s", BookController.PREFIX, ID, BookController.NAME_PARAM, newName);
        System.out.println(request);

        Mockito.when(impl.findById(ID)).thenReturn(Optional.of(original));
        Mockito.when(impl.updateFromId(newAuth, ID)).thenReturn(Optional.of(newAuth));

        this.mockMvc.perform(MockMvcRequestBuilders.put(request)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(newName));
    }
    
    @Test
    void updateNameFailure() throws Exception {
        final String request = String.format("%s/%s?%s=%s", BookController.PREFIX, ID, BookController.NAME_PARAM,
                NAME);
        Mockito.when(impl.findById(ID)).thenReturn(Optional.empty());

        this.mockMvc.perform(MockMvcRequestBuilders.put(request)
                .content(""))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }   
    
    
    @Test
    void updateNoParamsFailure() throws Exception {
        final String request = String.format("%s/%s", BookController.PREFIX, ID);
        System.out.println(request);
        Mockito.when(impl.findById(ID)).thenReturn(Optional.empty());

        this.mockMvc.perform(MockMvcRequestBuilders.put(request)
                .content(""))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }
    
    @Test
    void updateFakeParamFailure() throws Exception {
        final String request = String.format("%s/%s?%s=%s", BookController.PREFIX, ID, "FAKE_PARAM", NAME);
        System.out.println(request);
        Mockito.when(impl.findById(ID)).thenReturn(Optional.empty());

        this.mockMvc.perform(MockMvcRequestBuilders.put(request)
                .content(""))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());           

    }

    @Test
    void deleteModelSuccess() throws Exception {
        final String request = String.format("%s/%s", BookController.PREFIX, ID);
        final String response = "true";
        Mockito.when(impl.existById(ID)).thenReturn(true);

        this.mockMvc.perform(MockMvcRequestBuilders.delete(request)
                .contentType(MediaType.TEXT_PLAIN_VALUE)
                .content(response))
            .andExpect(MockMvcResultMatchers.status().isCreated());

    }
    
    @Test
    void deleteModelFailure() throws Exception{
        final String request = String.format("%s/%s", BookController.PREFIX, ID);
        final String response = "false";
        Mockito.when(impl.existById(ID)).thenReturn(false);

        this.mockMvc.perform(MockMvcRequestBuilders.delete(request)
                .contentType(MediaType.TEXT_PLAIN_VALUE)
                .content(response))
            .andExpect(MockMvcResultMatchers.status().isNotFound());       

    }
    
}
