package org.fpij.jitakyoei.model.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.fpij.jitakyoei.MockAppView;
import org.fpij.jitakyoei.business.ProfessorEntidadeBO;
import org.fpij.jitakyoei.business.ProfessorEntidadeBOImpl;
import org.fpij.jitakyoei.model.beans.Endereco;
import org.fpij.jitakyoei.model.beans.Entidade;
import org.fpij.jitakyoei.model.beans.Filiado;
import org.fpij.jitakyoei.model.beans.Professor;
import org.fpij.jitakyoei.model.beans.ProfessorEntidade;
import org.fpij.jitakyoei.util.DatabaseManager;
import org.fpij.jitakyoei.view.AppView;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ProfessorEntidadeDaoTest {

    private static ProfessorEntidadeBO professorEntidadeBO;
    private static List<ProfessorEntidade> relacionamentos;
    private static Professor professor;
    private static Entidade entidade;
    private static AppView mockView;

    @Before
    public void setUp() {
        // Configura o ambiente de teste
        DatabaseManager.setEnviroment(DatabaseManager.TEST);

        // Cria uma implementação mock de AppView
        mockView = new MockAppView();

        // Instancia o ProfessorEntidadeBO com o mockView
        professorEntidadeBO = new ProfessorEntidadeBOImpl(mockView);

        // Configura o professor
        Filiado filiadoProfessor = new Filiado();
        filiadoProfessor.setNome("Professor Teste");
        filiadoProfessor.setCpf("123.456.789-00");
        filiadoProfessor.setDataNascimento(new Date());
        filiadoProfessor.setDataCadastro(new Date());
        filiadoProfessor.setId(1L);

        professor = new Professor();
        professor.setFiliado(filiadoProfessor);

        // Configura a entidade
        entidade = new Entidade();
        entidade.setNome("Entidade Teste");
        Endereco endereco = new Endereco();
        endereco.setBairro("Bairro Teste");
        endereco.setCep("12345-678");
        endereco.setCidade("Cidade Teste");
        endereco.setEstado("ST");
        endereco.setRua("Rua Teste");
        entidade.setEndereco(endereco);
        entidade.setTelefone1("(11)1234-5678");

        // Configura os relacionamentos
        relacionamentos = new ArrayList<>();
        ProfessorEntidade professorEntidade = new ProfessorEntidade(professor,entidade);
        relacionamentos.add(professorEntidade);
    }

    public static void clearDatabase() {
        try {
            // Como não há método para listar ou deletar ProfessorEntidade, usamos o DAO diretamente
            DAO<ProfessorEntidade> dao = new DAOImpl<ProfessorEntidade>(ProfessorEntidade.class);
            List<ProfessorEntidade> all = dao.list();
            for (ProfessorEntidade each : all) {
                dao.delete(each);
            }
            all = dao.list();
            assertEquals(0, all.size());

            // Também precisamos limpar as tabelas relacionadas
            DAO<Professor> professorDao = new DAOImpl<Professor>(Professor.class);
            List<Professor> allProfessors = professorDao.list();
            for (Professor each : allProfessors) {
                professorDao.delete(each);
            }

            DAO<Entidade> entidadeDao = new DAOImpl<Entidade>(Entidade.class);
            List<Entidade> allEntidades = entidadeDao.list();
            for (Entidade each : allEntidades) {
                entidadeDao.delete(each);
            }

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
    public void testCreateProfessorEntidade() throws Exception {
        clearDatabase();

        // Salva o professor e a entidade primeiro
        DAO<Professor> professorDao = new DAOImpl<Professor>(Professor.class);
        professorDao.save(professor);

        DAO<Entidade> entidadeDao = new DAOImpl<Entidade>(Entidade.class);
        entidadeDao.save(entidade);

        // Tenta criar os relacionamentos
        try {
            professorEntidadeBO.createProfessorEntidade(relacionamentos);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception during createProfessorEntidade: " + e.getMessage());
        }

        // Verifica se os relacionamentos foram salvos
        DAO<ProfessorEntidade> dao = new DAOImpl<ProfessorEntidade>(ProfessorEntidade.class);
        List<ProfessorEntidade> all = dao.list();
        assertEquals(1, all.size());
        ProfessorEntidade savedProfessorEntidade = all.get(0);
        assertEquals("Professor Teste", savedProfessorEntidade.getProfessor().getFiliado().getNome());
        assertEquals("Entidade Teste", savedProfessorEntidade.getEntidade().getNome());
    }
}


