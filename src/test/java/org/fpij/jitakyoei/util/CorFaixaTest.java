package org.fpij.jitakyoei.util;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class CorFaixaTest {

    @Test
    public void testGetDescricao() {
        for (CorFaixa cor : CorFaixa.values()) {
            assertNotNull("Descricao should not be null", cor.getDescricao());
            assertFalse("Descricao should not be empty", cor.getDescricao().isEmpty());
        }
    }

    @Test
    public void testToString() {
        for (CorFaixa cor : CorFaixa.values()) {
            assertEquals("toString should return descricao", cor.getDescricao(), cor.toString());
        }
    }

    @Test
    public void testGetCoresFaixa() {
        List<CorFaixa> coresFaixa = CorFaixa.getCoresFaixa();
        assertNotNull("CoresFaixa list should not be null", coresFaixa);
        assertEquals("CoresFaixa list size should match enum values", CorFaixa.values().length, coresFaixa.size());

        // Ensure all enum values are included in the list
        for (CorFaixa cor : CorFaixa.values()) {
            assertTrue("CoresFaixa should contain " + cor, coresFaixa.contains(cor));
        }
    }

    @Test
    public void testEnumValues() {
        // Test specific enum values and their descriptions
        assertEquals("Branca", CorFaixa.BRANCA.getDescricao());
        assertEquals("Cinza", CorFaixa.CINZA.getDescricao());
        assertEquals("Azul", CorFaixa.AZUL.getDescricao());
        assertEquals("Amarela", CorFaixa.AMARELA.getDescricao());
        assertEquals("Laranja", CorFaixa.LARANJA.getDescricao());
        assertEquals("Verde", CorFaixa.VERDE.getDescricao());
        assertEquals("Roxa", CorFaixa.ROXA.getDescricao());
        assertEquals("Marrom", CorFaixa.MARROM.getDescricao());
        assertEquals("Preta 1º Dan", CorFaixa.PRETA1DAN.getDescricao());
        assertEquals("Preta 2º Dan", CorFaixa.PRETA2DAN.getDescricao());
        assertEquals("Preta 3º Dan", CorFaixa.PRETA3DAN.getDescricao());
        assertEquals("Preta 4º Dan", CorFaixa.PRETA4DAN.getDescricao());
        assertEquals("Preta 5º Dan", CorFaixa.PRETA5DAN.getDescricao());
        assertEquals("Coral 6º Dan", CorFaixa.CORAL6DAN.getDescricao());
        assertEquals("Coral 7º Dan", CorFaixa.CORAL7DAN.getDescricao());
        assertEquals("Vermelha 8º Dan", CorFaixa.VERMELHA8DAN.getDescricao());
        assertEquals("Vermelha 9º Dan", CorFaixa.VERMELHA9DAN.getDescricao());
        assertEquals("Vermelha 10º Dan", CorFaixa.VERMELHA10DAN.getDescricao());
    }
}
