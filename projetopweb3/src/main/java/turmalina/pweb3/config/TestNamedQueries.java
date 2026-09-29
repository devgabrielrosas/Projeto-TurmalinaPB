package turmalina.pweb3.config;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import turmalina.pweb3.model.entity.Coleta;
import turmalina.pweb3.model.entity.Participacao;
import turmalina.pweb3.model.enums.SituacaoExpedicao;

public class TestNamedQueries {

    public static void main(String[] args) {
        try (EntityManagerFactory emf =
                     Persistence.createEntityManagerFactory("turmalinaPU");
             EntityManager em = emf.createEntityManager()) {

            List<Long> expedicaoIds = em.createQuery(
                            "select e.id from Expedicao e where e.codigo = :codigo",
                            Long.class)
                    .setParameter("codigo", "TURM-E002")
                    .getResultList();

            if (expedicaoIds.isEmpty()) {
                System.out.println("A carga inicial nao foi encontrada. Execute CreateInstances primeiro.");
                return;
            }

            Long expedicaoId = expedicaoIds.get(0);

            System.out.println("\n=== Expedicoes autorizadas em 2026 ===");
            List<Object[]> expedicoes = em.createNamedQuery(
                            "Expedicao.listarPorPeriodoESituacao", Object[].class)
                    .setParameter("inicio", LocalDateTime.of(2026, 1, 1, 0, 0))
                    .setParameter("fim", LocalDateTime.of(2026, 12, 31, 23, 59, 59))
                    .setParameter("situacao", SituacaoExpedicao.AUTORIZADA)
                    .getResultList();

            for (Object[] expedicao : expedicoes) {
                System.out.printf(
                        "codigo=%s | titulo=%s | caverna=%s | inicio=%s | termino=%s | situacao=%s%n",
                        expedicao[0], expedicao[1], expedicao[2],
                        expedicao[3], expedicao[4], expedicao[5]);
            }

            System.out.println("\n=== Participacoes da expedicao TURM-E002 ===");
            List<Participacao> participacoes = em.createNamedQuery(
                            "Participacao.listarPorExpedicaoComPessoa", Participacao.class)
                    .setParameter("expedicaoId", expedicaoId)
                    .getResultList();

            for (Participacao participacao : participacoes) {
                System.out.printf(
                        "id=%d | pessoa=%s | cpf=%s | papel=%s | presencaConfirmada=%s%n",
                        participacao.getId(),
                        participacao.getPessoa().getNome(),
                        participacao.getPessoa().getCpf(),
                        participacao.getPapel(),
                        participacao.isPresencaConfirmada());
            }

            System.out.println("\n=== Coletas da expedicao TURM-E002 ===");
            List<Coleta> coletas = em.createNamedQuery(
                            "Coleta.listarPorExpedicao", Coleta.class)
                    .setParameter("expedicaoId", expedicaoId)
                    .getResultList();

            for (Coleta coleta : coletas) {
                System.out.printf(
                        "id=%d | dataHora=%s | metodo=%s | setor=%s | pesquisador=%s%n",
                        coleta.getId(),
                        coleta.getDataHora(),
                        coleta.getMetodo(),
                        coleta.getSetor().getDenominacao(),
                        coleta.getPesquisador().getNome());
            }

            if (coletas.isEmpty()) {
                System.out.println("Nao ha coleta da seed para demonstrar Amostra.listarPorColeta.");
                return;
            }

            Long coletaId = coletas.get(0).getId();

            System.out.println("\n=== Amostras da primeira coleta da expedicao TURM-E002 ===");
            List<Object[]> amostras = em.createNamedQuery(
                            "Amostra.listarPorColeta", Object[].class)
                    .setParameter("coletaId", coletaId)
                    .getResultList();

            for (Object[] amostra : amostras) {
                System.out.printf(
                        "id=%s | codigoCampo=%s | categoria=%s | quantidade=%s | unidade=%s "
                                + "| acondicionamento=%s | conservacao=%s | perigoso=%s | observacoes=%s%n",
                        amostra[0], amostra[1], amostra[2], amostra[3], amostra[4],
                        amostra[5], amostra[6], amostra[7], amostra[8]);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Nao foi possivel testar as consultas nomeadas.", e);
        }
    }
}
