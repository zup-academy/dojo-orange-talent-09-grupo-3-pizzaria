package br.com.zup.edu.pizzaria.pizzas;

import br.com.zup.edu.pizzaria.ingredientes.Ingrediente;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PizzaTest {

    private Pizza pizza;

    @Test
    public void deveCalcularPrecoDaPizzaComUmIngrediente() {
        Ingrediente ingrediente1 = new Ingrediente("Muçarela", 10, new BigDecimal("15.0"));
        List<Ingrediente> ingredientes = List.of(ingrediente1);
        Pizza pizza1 = new Pizza("Calabresa", ingredientes);

        BigDecimal esperado = new BigDecimal("35.0");
        Assertions.assertEquals(esperado, pizza1.getPreco());
    }

    @Test
    public void deveCalcularPizzaSemIngrediente() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        Pizza pizza1 = new Pizza("Calabresa", ingredientes);

        BigDecimal esperado = new BigDecimal("20.0");
        Assertions.assertEquals(esperado, pizza1.getPreco());
    }

    @Test
    public void deveCalcularPizzaComMaisDeUmIngrediente() {
        Ingrediente ingrediente1 = new Ingrediente("Muçarela", 10, new BigDecimal("15.0"));
        Ingrediente ingrediente2 = new Ingrediente("Brocolis", 10, new BigDecimal("15.0"));
        List<Ingrediente> ingredientes = new ArrayList<>();
        Pizza pizza1 = new Pizza("Calabresa", ingredientes);

        BigDecimal esperado = new BigDecimal("50.0");
        Assertions.assertEquals(esperado, pizza1.getPreco());
    }

}