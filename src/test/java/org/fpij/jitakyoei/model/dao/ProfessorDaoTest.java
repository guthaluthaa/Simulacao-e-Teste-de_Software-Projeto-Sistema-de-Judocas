package org.fpij.jitakyoei.model.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.fpij.jitakyoei.MockAppView;
import org.fpij.jitakyoei.business.ProfessorBO;
import org.fpij.jitakyoei.business.ProfessorBOImpl;
import org.fpij.jitakyoei.facade.AppFacade;
import org.fpij.jitakyoei.model.beans.Endereco;
import org.fpij.jitakyoei.model.beans.Filiado;
import org.fpij.jitakyoei.model.beans.Professor;
import org.fpij.jitakyoei.util.DatabaseManager;
import org.fpij.jitakyoei.util.FiliadoID;
import org.fpij.jitakyoei.view.AppView;
import org.fpij.jitakyoei.model.dao.DAO;
import org.fpij.jitakyoei.model.dao.DAOImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ProfessorDaoTest {

    private static ProfessorBO professorBO;
    private static Professor professor;
    private static AppView mockView;

    @Before
    public void setUp() {
        // Configura o ambiente de teste
        DatabaseManager.setEnviroment(DatabaseManager.TEST);

        // Cria uma implementação mock de AppView
        mockView = new MockAppView();

        // Instancia o ProfessorBO com o mockView
        professorBO = new ProfessorBOImpl(mockView);

        // Configura o professor para testes
        Filiado filiado = new Filiado();
        filiado.setNome("Professor Teste");
        filiado.setCpf("123.456.789-00");
        Endereco endereco = new Endereco();
        endereco.setRua("Rua Teste");
        endereco.setBairro("Centro");
        endereco.setCep("64000-000");
        endereco.setCidade("Teresina");
        endereco.setEstado("PI");
        filiado.setEndereco(endereco);

        professor = new Professor();
        professor.setFiliado(filiado);
    }

    public static void clearDatabase() {
        try {
            List<Professor> all = professorBO.listAll();
            for (Professor each : all) {
                // Como não há método delete em ProfessorBO, usamos o DAO diretamente
                DAO<Professor> dao = new DAOImpl<Professor>(Professor.class);
                dao.delete(each);
            }
            all = professorBO.listAll();
            assertEquals(0, all.size());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception during clearDatabase: " + e.getMessage());
        }
    }

    @After
    public void tearDown() {
        clearDatabase();
    }

    @Test
    public void testCreateProfessor() throws Exception {
        clearDatabase();

        // Configura o professor para testes
        Filiado filiadoCreate = new Filiado();
        filiadoCreate.setNome("Professor Teste");
        filiadoCreate.setCpf("123.456.789-00");
        Endereco enderecoCreate = new Endereco();
        enderecoCreate.setRua("Rua Teste");
        enderecoCreate.setBairro("Centro");
        enderecoCreate.setCep("64000-000");
        enderecoCreate.setCidade("Teresina");
        enderecoCreate.setEstado("PI");
        filiadoCreate.setEndereco(enderecoCreate);

        Professor professorCreate = new Professor();
        professorCreate.setFiliado(filiadoCreate);

        try {
            professorBO.createProfessor(professorCreate);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception during createProfessor: " + e.getMessage());
        }

        // Verifica se o professor foi salvo
        List<Professor> all = professorBO.listAll();
        assertEquals(1, all.size());
        Professor retrievedProfessor = all.get(0);
        assertEquals("Professor Teste", retrievedProfessor.getFiliado().getNome());
        assertEquals("123.456.789-00", retrievedProfessor.getFiliado().getCpf());
    }

    @Test
    public void testListAll() throws Exception {
        clearDatabase();
        // Garante que o banco de dados está vazio
        List<Professor> all = professorBO.listAll();
        assertEquals(0, all.size());

        // Cria e salva um professor
        professorBO.createProfessor(professor);

        // Lista todos e verifica
        all = professorBO.listAll();
        assertEquals(1, all.size());
        Professor retrievedProfessor = all.get(0);
        assertEquals("Professor Teste", retrievedProfessor.getFiliado().getNome());
    }

    @Test
    public void testSearchProfessor() throws Exception {
        clearDatabase();

        Professor searchCriteria = new Professor();
        Filiado filiadoCriteria = new Filiado();
        filiadoCriteria.setNome("Professor Teste");
        searchCriteria.setFiliado(filiadoCriteria);

        // Cria e salva um professor
        professorBO.createProfessor(searchCriteria);

        // Busca
        List<Professor> results = professorBO.searchProfessor(searchCriteria);

        // Verifica
        assertEquals(1, results.size());
        Professor result = results.get(0);
        assertEquals("Professor Teste", result.getFiliado().getNome());
    }

    @Test
    public void testUpdateProfessor() throws Exception {
        clearDatabase();

        // Cria e salva um professor
        professorBO.createProfessor(professor);

        // Modifica o professor
        List<Professor> all = professorBO.listAll();
        Professor toUpdate = all.get(0);
        toUpdate.getFiliado().setNome("Professor Atualizado");

        // Atualiza
        professorBO.updateProfessor(toUpdate);

        // Verifica a atualização
        all = professorBO.listAll();
        assertEquals(1, all.size());
        Professor updatedProfessor = all.get(0);
        assertEquals("Professor Atualizado", updatedProfessor.getFiliado().getNome());
    }
}
