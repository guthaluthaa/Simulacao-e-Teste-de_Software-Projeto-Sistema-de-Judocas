package org.fpij.jitakyoei.model.beans;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class AlunoTest {

    private Aluno aluno1;
    private Aluno aluno2;
    private Filiado filiado1;
    private Filiado filiado2;
    private Entidade entidade1;
    private Entidade entidade2;
    private Professor professor1;
    private Professor professor2;

    @Before
    public void setUp() {
        // Create Filiado instances
        filiado1 = new Filiado();
        filiado1.setId(1L);
        filiado1.setNome("Aluno Teste");

        filiado2 = new Filiado();
        filiado2.setId(2L);
        filiado2.setNome("Outro Aluno");

        // Create Entidade instances
        entidade1 = new Entidade();
        entidade1.setNome("Entidade 1");

        entidade2 = new Entidade();
        entidade2.setNome("Entidade 2");

        // Create Professor instances
        professor1 = new Professor();
        Filiado filiadoProfessor1 = new Filiado();
        filiadoProfessor1.setId(10L);
        filiadoProfessor1.setNome("Professor 1");
        professor1.setFiliado(filiadoProfessor1);

        professor2 = new Professor();
        Filiado filiadoProfessor2 = new Filiado();
        filiadoProfessor2.setId(20L);
        filiadoProfessor2.setNome("Professor 2");
        professor2.setFiliado(filiadoProfessor2);

        // Create Aluno instances
        aluno1 = new Aluno();
        aluno1.setFiliado(filiado1);
        aluno1.setEntidade(entidade1);
        aluno1.setProfessor(professor1);

        aluno2 = new Aluno();
        aluno2.setFiliado(filiado2);
        aluno2.setEntidade(entidade2);
        aluno2.setProfessor(professor2);
    }

    @Test
    public void testGettersSetters() {
        // Test getters
        assertEquals(filiado1, aluno1.getFiliado());
        assertEquals(entidade1, aluno1.getEntidade());
        assertEquals(professor1, aluno1.getProfessor());

        // Test setters
        aluno1.setFiliado(filiado2);
        aluno1.setEntidade(entidade2);
        aluno1.setProfessor(professor2);

        // Verify updated values
        assertEquals(filiado2, aluno1.getFiliado());
        assertEquals(entidade2, aluno1.getEntidade());
        assertEquals(professor2, aluno1.getProfessor());
    }

    @Test
    public void testToString() {
        String expected = filiado1.toString();
        assertEquals(expected, aluno1.toString());
    }

    @Test
    public void testCopyProperties() {
        // Copy properties from aluno2 to aluno1
        aluno1.copyProperties(aluno2);

        // Test that properties have been copied
        assertEquals(aluno2.getFiliado(), aluno1.getFiliado());
        assertEquals(aluno2.getEntidade(), aluno1.getEntidade());
        assertEquals(aluno2.getProfessor(), aluno1.getProfessor());
    }

    @Test(expected = NullPointerException.class)
    public void testEqualsWithNullFiliado() {
        // Aluno with null filiado should throw NullPointerException
        Aluno alunoWithNullFiliado = new Aluno();
        alunoWithNullFiliado.setFiliado(null);
        aluno1.equals(alunoWithNullFiliado);
    }
}
