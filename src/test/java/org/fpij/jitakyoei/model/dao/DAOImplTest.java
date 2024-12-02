package org.fpij.jitakyoei.model.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.fpij.jitakyoei.model.beans.Endereco;
import org.fpij.jitakyoei.model.beans.Filiado;
import org.fpij.jitakyoei.model.beans.Professor;
import org.fpij.jitakyoei.util.DatabaseManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DAOImplTest {

    private DAO<Professor> dao;

    @Before
    public void setUp() {
        // Set up the test environment
        DatabaseManager.setEnviroment(DatabaseManager.TEST);
        dao = new DAOImpl<Professor>(Professor.class, true);

        // Clear the database before each test
        clearDatabase();
    }

    @After
    public void tearDown() {
        // Clear the database after each test
        clearDatabase();

    }

    private void clearDatabase() {
        List<Professor> all = dao.list();
        for (Professor each : all) {
            dao.delete(each);
        }
    }

    @Test
    public void testSave() {
        Professor professor = createProfessor("Professor Teste", "123.456.789-00");
        boolean saved = dao.save(professor);
        assertTrue(saved);

        List<Professor> list = dao.list();
        assertEquals(1, list.size());
        Professor retrieved = list.get(0);
        assertEquals("Professor Teste", retrieved.getFiliado().getNome());
    }

    @Test
    public void testDelete() {
        Professor professor = createProfessor("Professor Teste", "123.456.789-00");
        dao.save(professor);

        List<Professor> list = dao.list();
        assertEquals(1, list.size());

        dao.delete(professor);

        list = dao.list();
        assertEquals(0, list.size());
    }

    @Test
    public void testList() {
        Professor professor1 = createProfessor("Professor 1", "123.456.789-00");
        Professor professor2 = createProfessor("Professor 2", "987.654.321-00");
        dao.save(professor1);
        dao.save(professor2);

        List<Professor> list = dao.list();
        assertEquals(2, list.size());
    }

    @Test
    public void testGet() {
        Professor professor = createProfessor("Professor Teste", "123.456.789-00");
        dao.save(professor);

        Professor toGet = createProfessor("Professor Teste", "123.456.789-00");
        Professor retrieved = dao.get(toGet);
        assertNotNull(retrieved);
        assertEquals("Professor Teste", retrieved.getFiliado().getNome());
    }

    @Test
    public void testSearch() {
        Professor professor = createProfessor("Professor Teste", "123.456.789-00");
        dao.save(professor);

        Professor searchCriteria = new Professor();
        Filiado filiadoCriteria = new Filiado();
        filiadoCriteria.setNome("Professor Teste");
        searchCriteria.setFiliado(filiadoCriteria);

        List<Professor> results = dao.search(searchCriteria);
        assertEquals(1, results.size());
        Professor result = results.get(0);
        assertEquals("Professor Teste", result.getFiliado().getNome());
    }

    private Professor createProfessor(String nome, String cpf) {
        Filiado filiado = new Filiado();
        filiado.setNome(nome);
        filiado.setCpf(cpf);
        Endereco endereco = new Endereco();
        endereco.setRua("Rua Teste");
        filiado.setEndereco(endereco);

        Professor professor = new Professor();
        professor.setFiliado(filiado);
        return professor;
    }
}
