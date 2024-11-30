package test.java.org.fpij.jitakyoei.business;

import static org.junit.Assert.*;

import java.util.List;

import org.fpij.jitakyoei.business.EntidadeBO;
import org.fpij.jitakyoei.business.EntidadeBOImpl;
import org.fpij.jitakyoei.facade.AppFacade;
import org.fpij.jitakyoei.model.beans.Endereco;
import org.fpij.jitakyoei.model.beans.Entidade;
import org.fpij.jitakyoei.util.DatabaseManager;
import org.fpij.jitakyoei.view.AppView;
import org.fpij.jitakyoei.model.dao.DAO;
import org.fpij.jitakyoei.model.dao.DAOImpl;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

    public class EntidadeDaoTest {

        private static EntidadeBO entidadeBO;
        private static Entidade entidade;
        private static Endereco endereco;
        private static AppView mockView;

        @BeforeClass
        public static void setUp() {
            // Configura o ambiente de teste
            DatabaseManager.setEnviroment(DatabaseManager.TEST);

            // Cria uma implementação mock de AppView
            mockView = new MockAppView();

            // Instancia o EntidadeBO com o mockView
            entidadeBO = new EntidadeBOImpl(mockView);

            // Configura a entidade para testes
            entidade = new Entidade();
            entidade.setNome("Academia Teste");
            endereco = new Endereco();
            endereco.setBairro("Bairro Teste");
            endereco.setCep("12345-678");
            endereco.setCidade("Cidade Teste");
            endereco.setEstado("ST");
            endereco.setRua("Rua Teste");
            entidade.setEndereco(endereco);
            entidade.setTelefone1("(11)1234-5678");
        }

        public static void clearDatabase() {
            try {
                List<Entidade> all = entidadeBO.listAll();
                for (Entidade each : all) {
                    // Como não há método delete em EntidadeBO, usamos o DAO diretamente
                    entidadeBO = null; // Reinicia o BO para evitar problemas com o DAO estático
                    DatabaseManager.setEnviroment(DatabaseManager.TEST);
                    entidadeBO = new EntidadeBOImpl(mockView);
                    DAO<Entidade> dao = new DAOImpl<Entidade>(Entidade.class);
                    dao.delete(each);
                }
                all = entidadeBO.listAll();
                assertEquals(0, all.size());
            } catch (Exception e) {
                e.printStackTrace();
                fail("Exception during clearDatabase: " + e.getMessage());
            }
        }

        @AfterClass
        public static void tearDown() {
            clearDatabase();
            DatabaseManager.close();
        }

        @Test
        public void testCreateEntidade() throws Exception {
            clearDatabase();

            // Configura a entidade para testes
            Entidade entidadeCreate = new Entidade();
            entidadeCreate.setNome("Academia Teste");
            Endereco endereco2 = new Endereco();
            endereco2.setBairro("Bairro Teste");
            endereco2.setCep("12345-678");
            endereco2.setCidade("Cidade Teste");
            endereco2.setEstado("ST");
            endereco2.setRua("Rua Teste");
            entidadeCreate.setEndereco(endereco2);
            entidadeCreate.setTelefone1("(11)1234-5678");

            try {
                entidadeBO.createEntidade(entidadeCreate);
            } catch (Exception e) {
                e.printStackTrace();
                fail("Exception during createEntidade: " + e.getMessage());
            }
            // Verifica se a entidade foi salva
            List<Entidade> all = entidadeBO.listAll();
            assertEquals(1, all.size());
            Entidade retrievedEntidade = all.get(0);
            assertEquals("Academia Teste", retrievedEntidade.getNome());
            assertEquals("Bairro Teste", retrievedEntidade.getEndereco().getBairro());
            assertEquals("(11)1234-5678", retrievedEntidade.getTelefone1());
        }

        @Test
        public void testListAll() throws Exception {
            clearDatabase();
            // Garante que o banco de dados está vazio
            List<Entidade> all = entidadeBO.listAll();
            assertEquals(0, all.size());

            // Cria e salva uma entidade
            entidadeBO.createEntidade(entidade);

            // Lista todas e verifica
            all = entidadeBO.listAll();
            assertEquals(1, all.size());
            Entidade retrievedEntidade = all.get(0);
            assertEquals("Academia Teste", retrievedEntidade.getNome());
        }

        @Test
        public void testSearchEntidade() throws Exception {
            clearDatabase();

            // Cria e salva uma entidade
            entidadeBO.createEntidade(entidade);

            // Cria um critério de busca
            Entidade searchCriteria = new Entidade();
            searchCriteria.setNome("Academia Teste");

            // Busca
            List<Entidade> results = entidadeBO.searchEntidade(searchCriteria);

            // Verifica
            assertEquals(1, results.size());
            Entidade result = results.get(0);
            assertEquals("Academia Teste", result.getNome());
        }

        @Test
        public void testUpdateEntidade() throws Exception {
            clearDatabase();

            // Cria e salva uma entidade
            entidadeBO.createEntidade(entidade);

            // Modifica a entidade
            List<Entidade> all = entidadeBO.listAll();
            Entidade toUpdate = all.get(0);
            toUpdate.setNome("Academia Atualizada");

            // Atualiza
            entidadeBO.updateEntidade(toUpdate);

            // Verifica a atualização
            all = entidadeBO.listAll();
            assertEquals(1, all.size());
            Entidade updatedEntidade = all.get(0);
            assertEquals("Academia Atualizada", updatedEntidade.getNome());
        }
    }

    // Classe mock para AppView
    class MockAppView implements AppView {
        @Override
        public void handleModelChange(Object obj) {
            // Implementação vazia para testes
        }
        @Override
        public void displayException(Exception e){

        }
        @Override
        public void registerFacade(AppFacade facade){

        }

        // Implemente outros métodos se necessário
    }
