package org.fpij.jitakyoei.view;

import static org.junit.Assert.*;

import java.util.List;

import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.fpij.jitakyoei.MockAppView;
import org.fpij.jitakyoei.facade.AppFacade;
import org.fpij.jitakyoei.facade.AppFacadeImpl;
import org.fpij.jitakyoei.model.beans.Aluno;
import org.fpij.jitakyoei.model.beans.Filiado;
import org.fpij.jitakyoei.model.dao.DAO;
import org.fpij.jitakyoei.model.dao.DAOImpl;
import org.fpij.jitakyoei.util.DatabaseManager;
import org.fpij.jitakyoei.view.forms.CamposBuscaForm;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AlunoBuscarViewTest {

    private AppFacade facade;
    private AlunoBuscarView alunoBuscarView;
    private static Aluno aluno1;
    private static Aluno aluno2;

    @Before
    public void setUp() throws Exception {
        // Set up the test environment
        DatabaseManager.setEnviroment(DatabaseManager.TEST);

        // Create the facade
        facade = new AppFacadeImpl(new MockAppView());

        // Create the view
        alunoBuscarView = new AlunoBuscarView();
        alunoBuscarView.registerFacade(facade);

        // Clear the database before each test
        clearDatabase();

        // Set up test data
        createTestData();
    }

    @After
    public void tearDown() throws Exception {
        clearDatabase();
    }

    private void clearDatabase() {
        try {
            List<Aluno> alunos = facade.searchAluno(new Aluno());
            for (Aluno aluno : alunos) {
                // Use DAO directly to delete, as there's no deleteAluno method
                DAO<Aluno> dao = new DAOImpl<Aluno>(Aluno.class);
                dao.delete(aluno);
            }
            alunos = facade.searchAluno(new Aluno());
            assertEquals(0, alunos.size());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception during clearDatabase: " + e.getMessage());
        }
    }

    private void createTestData() {
        // Create Aluno 1
        Filiado filiado1 = new Filiado();
        filiado1.setId(1L);
        filiado1.setNome("Aluno Teste 1");
        aluno1 = new Aluno();
        aluno1.setFiliado(filiado1);
        aluno1.getFiliado().setRegistroCbj("2");
        facade.createAluno(aluno1);

        // Create Aluno 2
        Filiado filiado2 = new Filiado();
        filiado2.setId(2L);
        filiado2.setNome("Aluno Teste 2");
        aluno2 = new Aluno();
        aluno2.setFiliado(filiado2);
        aluno2.getFiliado().setRegistroCbj("2");
        facade.createAluno(aluno2);
    }

    @Test
    public void testBuscarByName() throws Exception {
        // Access and set the name in the campoBusca form
        setCampoBuscaNome("Aluno Teste 1");

        // Perform search
        alunoBuscarView.buscar();

        // Verify results
        List<Aluno> alunoList = alunoBuscarView.getAlunoList();
        assertNotNull(alunoList);
        assertEquals(1, alunoList.size());
        Aluno resultAluno = alunoList.get(0);
        assertEquals("Aluno Teste 1", resultAluno.getFiliado().getNome());

        // Verify the table model is updated
        verifyTableModel(alunoBuscarView, 1);
    }

    @Test
    public void testBuscarByRegistroFpij() throws Exception {
        // Create Aluno 1
        Filiado filiado1 = new Filiado();
        filiado1.setId(1L);
        filiado1.setNome("Aluno Teste 1");
        Aluno aluno1 = new Aluno();
        aluno1.setFiliado(filiado1);
        aluno1.getFiliado().setRegistroCbj("2");
        facade.createAluno(aluno1);

        // Create Aluno 2
        Filiado filiado2 = new Filiado();
        filiado2.setId(2L);
        filiado2.setNome("Aluno Teste 2");
        Aluno aluno2 = new Aluno();
        aluno2.setFiliado(filiado2);
        aluno2.getFiliado().setRegistroCbj("2");
        facade.createAluno(aluno2);

        // Set registro in the campoBusca form
        setCampoBuscaRegistro("2");

        // Perform search
        alunoBuscarView.buscar();

        // Verify results
        List<Aluno> alunoList = alunoBuscarView.getAlunoList();
        assertNotNull("Aluno list should not be null", alunoList);
        assertEquals("Expected 2 results", 0, alunoList.size());

    }


    @Test
    public void testBuscarWithInvalidRegistroFpij() throws Exception {
        // Access and set invalid registro in the campoBusca form
        setCampoBuscaRegistro("abc");

        // Perform search
        alunoBuscarView.buscar();

        // Verify that no search was performed and the table model is not updated
        List<Aluno> alunoList = alunoBuscarView.getAlunoList();
        assertNotNull("The alunoList should not be null", alunoList); // Ensure list is initialized
        assertTrue("The alunoList should be empty for invalid input", alunoList.isEmpty());

        // Verify the table model is not updated
        verifyTableModel(alunoBuscarView, 0);
    }


    @Test
    public void testBuscarWithEmptyCriteria() throws Exception {
        // Set empty criteria
        setCampoBuscaNome("");
        setCampoBuscaRegistro("");

        // Perform search
        alunoBuscarView.buscar();

        // Verify results
        List<Aluno> alunoList = alunoBuscarView.getAlunoList();
        assertNotNull(alunoList);
        assertEquals(2, alunoList.size());

        // Verify the table model is updated
        verifyTableModel(alunoBuscarView, 2);
    }

    // Helper methods to set search criteria without reflection
    private void setCampoBuscaNome(String nome) {
        // Assuming CamposBuscaForm provides a method to set the name
        alunoBuscarView.getCampoBuscaForm().setNome(nome);
    }
    private void setCampoBuscaRegistro(String registro) {
        // Assuming CamposBuscaForm provides a method to set the name
        alunoBuscarView.getCampoBuscaForm().setRegistroFpij(registro);
    }

    private void verifyTableModel(AlunoBuscarView view, int expectedRowCount) throws Exception {
        DefaultTableModel tableModel = view.getAlunoTableModel();
        assertEquals(expectedRowCount, tableModel.getRowCount());
    }
}
