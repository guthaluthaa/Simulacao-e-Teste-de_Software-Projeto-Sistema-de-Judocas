package org.fpij.jitakyoei.facade;

import static org.junit.Assert.*;

import java.util.List;

import org.fpij.jitakyoei.model.beans.Aluno;
import org.fpij.jitakyoei.model.beans.Endereco;
import org.fpij.jitakyoei.model.beans.Entidade;
import org.fpij.jitakyoei.model.beans.Filiado;
import org.fpij.jitakyoei.model.beans.Professor;
import org.fpij.jitakyoei.model.dao.DAO;
import org.fpij.jitakyoei.model.dao.DAOImpl;
import org.fpij.jitakyoei.MockAppView;
import org.fpij.jitakyoei.model.validator.AlunoValidator;
import org.fpij.jitakyoei.util.DatabaseManager;
import org.fpij.jitakyoei.view.AppView;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AppFacadeTest {

    private AppFacade appFacade;
    private AppView mockView;

    @Before
    public void setUp() {
        // Set up the test environment
        DatabaseManager.setEnviroment(DatabaseManager.TEST);

        // Create a mock implementation of AppView
        mockView = new MockAppView();

        // Instantiate AppFacadeImpl with the mockView
        appFacade = new AppFacadeImpl(mockView);

        // Clear the database before each test
        clearDatabase();
    }

    @After
    public void tearDown() {
        // Clear the database after each test
        clearDatabase();
    }

    public void clearDatabase() {
        try {
            // Clear Alunos
            List<Aluno> alunos = appFacade.searchAluno(new Aluno());
            for (Aluno aluno : alunos) {
                // Use DAO directly to delete, as there's no deleteAluno method
                DAO<Aluno> dao = new DAOImpl<Aluno>(Aluno.class);
                dao.delete(aluno);
            }
            // Clear Professores
            List<Professor> professors = appFacade.listProfessores();
            for (Professor professor : professors) {
                DAO<Professor> dao = new DAOImpl<Professor>(Professor.class);
                dao.delete(professor);
            }
            // Clear Entidades
            List<Entidade> entidades = appFacade.listEntidade();
            for (Entidade entidade : entidades) {
                DAO<Entidade> dao = new DAOImpl<Entidade>(Entidade.class);
                dao.delete(entidade);
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception during clearDatabase: " + e.getMessage());
        }
    }

    @Test
    public void testCreateProfessor() {
        // Set up data
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

        Professor professor = new Professor();
        professor.setFiliado(filiado);

        // Call the method
        appFacade.createProfessor(professor);

        // Verify
        List<Professor> professors = appFacade.listProfessores();
        assertEquals(1, professors.size());
        Professor retrievedProfessor = professors.get(0);
        assertEquals("Professor Teste", retrievedProfessor.getFiliado().getNome());
        assertEquals("123.456.789-00", retrievedProfessor.getFiliado().getCpf());
    }

    @Test
    public void testSearchProfessor() {
        // First, create a professor
        Filiado filiado = new Filiado();
        filiado.setNome("Professor Teste");
        filiado.setCpf("123.456.789-00");
        Professor professor = new Professor();
        professor.setFiliado(filiado);
        appFacade.createProfessor(professor);

        // Now, search for the professor
        Professor searchCriteria = new Professor();
        Filiado filiadoCriteria = new Filiado();
        filiadoCriteria.setNome("Professor Teste");
        searchCriteria.setFiliado(filiadoCriteria);

        List<Professor> results = appFacade.searchProfessor(searchCriteria);

        // Verify
        assertEquals(1, results.size());
        Professor result = results.get(0);
        assertEquals("Professor Teste", result.getFiliado().getNome());
    }

    @Test
    public void testUpdateProfessor() {
        // First, create a professor
        Filiado filiado = new Filiado();
        filiado.setNome("Professor Teste");
        filiado.setCpf("123.456.789-00");
        Professor professor = new Professor();
        professor.setFiliado(filiado);
        appFacade.createProfessor(professor);

        // Modify the professor
        List<Professor> professors = appFacade.listProfessores();
        Professor toUpdate = professors.get(0);
        toUpdate.getFiliado().setNome("Professor Atualizado");

        // Update
        appFacade.updateProfessor(toUpdate);

        // Verify
        professors = appFacade.listProfessores();
        assertEquals(1, professors.size());
        Professor updatedProfessor = professors.get(0);
        assertEquals("Professor Atualizado", updatedProfessor.getFiliado().getNome());
    }

    @Test
    public void testCreateAluno() {
        // Set up data
        Filiado filiado = new Filiado();
        filiado.setNome("Aluno Teste");
        filiado.setCpf("987.654.321-00");
        Endereco endereco = new Endereco();
        endereco.setRua("Rua Aluno");
        endereco.setBairro("Bairro Aluno");
        endereco.setCep("64000-000");
        endereco.setCidade("Teresina");
        endereco.setEstado("PI");
        filiado.setEndereco(endereco);

        Aluno aluno = new Aluno();
        aluno.setFiliado(filiado);

        // Call the method
        appFacade.createAluno(aluno);

        // Verify
        List<Aluno> alunos = appFacade.searchAluno(aluno);
        assertEquals(1, alunos.size());
        Aluno retrievedAluno = alunos.get(0);
        assertEquals("Aluno Teste", retrievedAluno.getFiliado().getNome());
        assertEquals("987.654.321-00", retrievedAluno.getFiliado().getCpf());
    }

    @Test
    public void testSearchAluno() {
        // Create an Aluno
        Filiado filiado = new Filiado();
        filiado.setNome("Aluno Teste");
        filiado.setCpf("987.654.321-00");
        Aluno aluno = new Aluno();
        aluno.setFiliado(filiado);
        appFacade.createAluno(aluno);

        // Search for the Aluno
        Aluno searchCriteria = new Aluno();
        Filiado filiadoCriteria = new Filiado();
        filiadoCriteria.setNome("Aluno Teste");
        searchCriteria.setFiliado(filiadoCriteria);

        List<Aluno> results = appFacade.searchAluno(searchCriteria);

        // Verify
        assertEquals(1, results.size());
        Aluno result = results.get(0);
        assertEquals("Aluno Teste", result.getFiliado().getNome());
    }

    @Test
    public void testUpdateAluno() {
        // Create an Aluno
        Filiado filiado = new Filiado();
        filiado.setNome("Aluno Teste");
        filiado.setCpf("987.654.321-00");
        Aluno aluno = new Aluno();
        aluno.setFiliado(filiado);
        appFacade.createAluno(aluno);

        // Modify the Aluno
        List<Aluno> alunos = appFacade.searchAluno(aluno);
        assertEquals(1, alunos.size()); // Ensure we have the correct Aluno
        Aluno toUpdate = alunos.get(0);
        toUpdate.getFiliado().setNome("Aluno Atualizado");

        // Update
        appFacade.updateAluno(toUpdate);

        // Verify
        // Create search criteria using CPF, which hasn't changed
        Aluno searchCriteria = new Aluno();
        Filiado filiadoCriteria = new Filiado();
        filiadoCriteria.setCpf("987.654.321-00");
        searchCriteria.setFiliado(filiadoCriteria);

        alunos = appFacade.searchAluno(searchCriteria);
        assertEquals(1, alunos.size()); // Now expecting one result
        Aluno updatedAluno = alunos.get(0);
        assertEquals("Aluno Atualizado", updatedAluno.getFiliado().getNome());
    }

    @Test
    public void testCreateEntidade() {
        // Set up data
        Endereco endereco = new Endereco();
        endereco.setRua("Rua Entidade");
        endereco.setBairro("Bairro Entidade");
        endereco.setCep("64000-000");
        endereco.setCidade("Teresina");
        endereco.setEstado("PI");

        Entidade entidade = new Entidade();
        entidade.setNome("Entidade Teste");
        entidade.setCnpj("12.345.678/0001-00");
        entidade.setEndereco(endereco);

        // Call the method
        appFacade.createEntidade(entidade);

        // Verify
        List<Entidade> entidades = appFacade.listEntidade();
        assertEquals(1, entidades.size());
        Entidade retrievedEntidade = entidades.get(0);
        assertEquals("Entidade Teste", retrievedEntidade.getNome());
        assertEquals("12.345.678/0001-00", retrievedEntidade.getCnpj());
    }

    @Test
    public void testSearchEntidade() {
        // Create an Entidade
        Endereco endereco = new Endereco();
        endereco.setRua("Rua Entidade");
        endereco.setBairro("Bairro Entidade");
        endereco.setCep("64000-000");
        endereco.setCidade("Teresina");
        endereco.setEstado("PI");

        Entidade entidade = new Entidade();
        entidade.setNome("Entidade Teste");
        entidade.setCnpj("12.345.678/0001-00");
        entidade.setEndereco(endereco);
        appFacade.createEntidade(entidade);

        // Search for the Entidade
        Entidade searchCriteria = new Entidade();
        searchCriteria.setNome("Entidade Teste");

        List<Entidade> results = appFacade.searchEntidade(searchCriteria);

        // Verify
        assertEquals(1, results.size());
        Entidade result = results.get(0);
        assertEquals("Entidade Teste", result.getNome());
    }

    @Test
    public void testUpdateEntidade() {
        // Create an Entidade
        Endereco endereco = new Endereco();
        endereco.setRua("Rua Entidade");
        endereco.setBairro("Bairro Entidade");
        endereco.setCep("64000-000");
        endereco.setCidade("Teresina");
        endereco.setEstado("PI");

        Entidade entidade = new Entidade();
        entidade.setNome("Entidade Teste");
        entidade.setCnpj("12.345.678/0001-00");
        entidade.setEndereco(endereco);
        appFacade.createEntidade(entidade);

        // Modify the Entidade
        List<Entidade> entidades = appFacade.searchEntidade(entidade);
        Entidade toUpdate = entidades.get(0);
        toUpdate.setNome("Entidade Atualizada");

        // Update
        appFacade.updateEntidade(toUpdate);

        // Verify
        entidades = appFacade.searchEntidade(toUpdate);
        assertEquals(1, entidades.size());
        Entidade updatedEntidade = entidades.get(0);
        assertEquals("Entidade Atualizada", updatedEntidade.getNome());
    }
}

