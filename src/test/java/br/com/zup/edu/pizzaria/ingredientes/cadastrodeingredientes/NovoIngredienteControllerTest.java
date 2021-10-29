package br.com.zup.edu.pizzaria.ingredientes.cadastrodeingredientes;

import br.com.zup.edu.pizzaria.ingredientes.Ingrediente;
import br.com.zup.edu.pizzaria.ingredientes.IngredienteRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class NovoIngredienteControllerTest {


    @Autowired
    private MockMvc mvc;

    @Autowired
    private IngredienteRepository ingredienteRepository;

    @Test
    void deveCadastrarNovoIngrediente() throws Exception {

        NovoIngredienteRequest body = new NovoIngredienteRequest("Queijo muçarela", new BigDecimal("2.0"), 200);
        MockHttpServletRequestBuilder request = post("/api/ingredientes")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(new ObjectMapper().writeValueAsString(body));

        mvc.perform(request)
           .andExpect(status().isCreated())
           .andExpect(header().exists("Location"))
                .andExpect(redirectedUrlPattern("/api/ingredientes/*"));

    }

    @Test
    void naoDeveCadastrarNovoIngredienteComNomeRepetido() throws Exception {
        Ingrediente ingrediente = new Ingrediente("Queijo muçarela", 1, new BigDecimal("100"));
        ingredienteRepository.save(ingrediente);

        NovoIngredienteRequest body = new NovoIngredienteRequest("Queijo muçarela", new BigDecimal("2.0"), 200);
        MockHttpServletRequestBuilder request = post("/api/ingredientes")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(new ObjectMapper().writeValueAsString(body));

        mvc.perform(request)
           .andExpect(status().isBadRequest());
    }

    @Test
    @ParameterizedTest
    @NullAndEmptySource
    void naoDeveCadastrarNovoIngredienteComNomeInvalido(String nome) throws Exception {
        NovoIngredienteRequest body = new NovoIngredienteRequest(nome, new BigDecimal("2.0"), 200);
        MockHttpServletRequestBuilder request = post("/api/ingredientes")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(new ObjectMapper().writeValueAsString(body));

        mvc.perform(request)
           .andExpect(status().isBadRequest());
    }
}