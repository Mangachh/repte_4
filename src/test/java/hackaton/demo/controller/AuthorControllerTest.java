package hackaton.demo.controller;

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

import hackaton.controller.AuthorController;
import hackaton.models.Author;
import hackaton.serveis.AuthorImpl;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc(addFilters = false)
public class AuthorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorImpl impl;

    private final static String NAME = "MockName";
    private final static String SURNAME = "MockSurname";
    private final static Long ID = 5L;

    @Test
    void insertAuthor() throws Exception {
        Author auth = new Author(NAME, SURNAME);
        String request = String.format("%s?%s=%s&%s=%s", AuthorController.PREFIX, AuthorController.NAME_PARAM, NAME,
                AuthorController.SURNAME_PARAM, SURNAME);

        Mockito.when(impl.insert(auth)).thenReturn(auth);

        this.mockMvc.perform(MockMvcRequestBuilders.post(request)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("@.name").value(NAME))
            .andExpect(MockMvcResultMatchers.jsonPath("@.surname").value(SURNAME));

    }

    @Test
    void getAll() throws Exception {
        final String request = String.format("%s/%s", AuthorController.PREFIX, "all");

        Author a = new Author(NAME + "_A", SURNAME + "_A");
        Author b = new Author(NAME + "_B", SURNAME + "_B");
        List<Author> answer = List.of(a, b);

        Mockito.when(this.impl.findAll()).thenReturn(answer);

        this.mockMvc.perform(MockMvcRequestBuilders.get(                request)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpectAll(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("@.[0].name").value(a.getName()))
            .andExpect(MockMvcResultMatchers.jsonPath("@.[0].surname").value(a.getSurname()))
            .andExpect(MockMvcResultMatchers.jsonPath("@.[1].name").value(b.getName()))
            .andExpect(MockMvcResultMatchers.jsonPath("@.[1].surname").value(b.getSurname()));

    }

    @Test
    void getByIdSuccess() throws Exception {
        final Author auth = new Author(ID, NAME, SURNAME);
        final String request = String.format("%s/%d", AuthorController.PREFIX, ID);

        Mockito.when(impl.findById(ID)).thenReturn(Optional.of(auth));

        this.mockMvc.perform(MockMvcRequestBuilders.get(request)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("@.name").value(auth.getName()))
            .andExpect(MockMvcResultMatchers.jsonPath("@.surname").value(auth.getSurname()));
    }

    @Test
    void getByIdFail() throws Exception {
        final String request = String.format("%s/%d", AuthorController.PREFIX, ID);

        Mockito.when(impl.findById(ID)).thenReturn(Optional.empty());

        this.mockMvc.perform(MockMvcRequestBuilders.get(request)
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
            .andExpect(MockMvcResultMatchers.status().isNotFound());

    }
    
    // aqui faltaria el update
    @Test
    void updateNameSuccess() throws Exception{
        final Author original = new Author(ID, NAME, SURNAME);
        final String newName = "New_" + NAME;
        final Author newAuth = new Author(ID, newName, SURNAME);

        final String request = String.format("%s/%d?%s=%s", AuthorController.PREFIX, ID, AuthorController.NAME_PARAM,
                newName);
        System.out.println(request);

        Mockito.when(impl.findById(ID)).thenReturn(Optional.of(original));
        Mockito.when(impl.updateFromId(newAuth, ID)).thenReturn(Optional.of(newAuth));

        this.mockMvc.perform(MockMvcRequestBuilders.put(request)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(newName))
            .andExpect(MockMvcResultMatchers.jsonPath("$.surname").value(SURNAME));        
    }

    @Test
    void updateSurnameSuccess() throws Exception {
        final Author original = new Author(ID, NAME, SURNAME);
        final String newSurname = "New_" + SURNAME;
        final Author newAuth = new Author(ID, NAME, newSurname);

        final String request = String.format("%s/%d?%s=%s", AuthorController.PREFIX, ID, AuthorController.SURNAME_PARAM,
                newSurname);
        System.out.println(request);

        Mockito.when(impl.findById(ID)).thenReturn(Optional.of(original));
        Mockito.when(impl.updateFromId(newAuth, ID)).thenReturn(Optional.of(newAuth));

        this.mockMvc.perform(MockMvcRequestBuilders.put(request)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(NAME))
            .andExpect(MockMvcResultMatchers.jsonPath("$.surname").value(newSurname));
    }
    
    @Test
    void updateFullSuccess() throws Exception {
        final Author original = new Author(ID, NAME, SURNAME);
        final String newName = "New_" + NAME;
        final String newSurname = "New_" + SURNAME;
        final Author newAuth = new Author(ID, newName, newSurname);

        final String request = String.format("%s/%d?%s=%s&%s=%s", AuthorController.PREFIX, ID, AuthorController.SURNAME_PARAM,
                newSurname, AuthorController.NAME_PARAM, newName);
        System.out.println(request);

        Mockito.when(impl.findById(ID)).thenReturn(Optional.of(original));
        Mockito.when(impl.updateFromId(newAuth, ID)).thenReturn(Optional.of(newAuth));

        this.mockMvc.perform(MockMvcRequestBuilders.put(request)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(newName))
            .andExpect(MockMvcResultMatchers.jsonPath("$.surname").value(newSurname));
    }
    
    @Test
    void updateNameFailure() throws Exception {
        final String request = String.format("%s/%d?%s=%s", AuthorController.PREFIX, ID, AuthorController.NAME_PARAM,
                NAME);
        Mockito.when(impl.findById(ID)).thenReturn(Optional.empty());

        this.mockMvc.perform(MockMvcRequestBuilders.put(request)
                .content(""))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }
    
    @Test
    void updateSurnameFailure() throws Exception {
        final String request = String.format("%s/%d?%s=%s", AuthorController.PREFIX, ID, AuthorController.SURNAME_PARAM,
                SURNAME);
        System.out.println(request);
        Mockito.when(impl.findById(ID)).thenReturn(Optional.empty());

        this.mockMvc.perform(MockMvcRequestBuilders.put(request)
                .content(""))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }
    
    @Test
    void updateNoParamsFailure() throws Exception {
        final String request = String.format("%s/%d", AuthorController.PREFIX, ID);
        System.out.println(request);
        Mockito.when(impl.findById(ID)).thenReturn(Optional.empty());

        this.mockMvc.perform(MockMvcRequestBuilders.put(request)
                .content(""))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }
    
    @Test
    void updateFakeParamFailure() throws Exception {
        final String request = String.format("%s/%d?%s=%s", AuthorController.PREFIX, ID, "FAKE_PARAM", NAME);
        System.out.println(request);
        Mockito.when(impl.findById(ID)).thenReturn(Optional.empty());

        this.mockMvc.perform(MockMvcRequestBuilders.put(request)
                .content(""))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());           

    }

    @Test
    void deleteModelSuccess() throws Exception {
        final String request = String.format("%s/%d", AuthorController.PREFIX, ID);
        final String response = "true";
        Mockito.when(impl.existById(ID)).thenReturn(true);

        this.mockMvc.perform(MockMvcRequestBuilders.delete(request)
                .contentType(MediaType.TEXT_PLAIN_VALUE)
                .content(response))
            .andExpect(MockMvcResultMatchers.status().isCreated());

    }
    
    @Test
    void deleteModelFailure() throws Exception{
        final String request = String.format("%s/%d", AuthorController.PREFIX, ID);
        final String response = "false";
        Mockito.when(impl.existById(ID)).thenReturn(false);

        this.mockMvc.perform(MockMvcRequestBuilders.delete(request)
                .contentType(MediaType.TEXT_PLAIN_VALUE)
                .content(response))
            .andExpect(MockMvcResultMatchers.status().isNotFound());       

    }
}
