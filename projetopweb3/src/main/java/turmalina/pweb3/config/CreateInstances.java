package turmalina.pweb3.config;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.arjuna.ats.arjuna.coordinator.TransactionReaper;
import com.arjuna.ats.arjuna.coordinator.TxControl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.transaction.UserTransaction;
import turmalina.pweb3.model.embeddable.Endereco;
import turmalina.pweb3.model.embeddable.Localizacao;
import turmalina.pweb3.model.entity.Amostra;
import turmalina.pweb3.model.entity.AutorizacaoAmbiental;
import turmalina.pweb3.model.entity.Caverna;
import turmalina.pweb3.model.entity.Coleta;
import turmalina.pweb3.model.entity.Equipamento;
import turmalina.pweb3.model.entity.Expedicao;
import turmalina.pweb3.model.entity.GuiaEspeleologia;
import turmalina.pweb3.model.entity.MovimentacaoEquipamento;
import turmalina.pweb3.model.entity.Participacao;
import turmalina.pweb3.model.entity.Pesquisador;
import turmalina.pweb3.model.entity.PlanoSeguranca;
import turmalina.pweb3.model.entity.RelatorioFinal;
import turmalina.pweb3.model.entity.Setor;
import turmalina.pweb3.model.enums.CategoriaAmostra;
import turmalina.pweb3.model.enums.CondicaoConservacao;
import turmalina.pweb3.model.enums.CondicaoSetor;
import turmalina.pweb3.model.enums.Datum;
import turmalina.pweb3.model.enums.EstadoEquipamento;
import turmalina.pweb3.model.enums.NivelCertificado;
import turmalina.pweb3.model.enums.NivelDificuldade;
import turmalina.pweb3.model.enums.PapelParticipante;
import turmalina.pweb3.model.enums.SituacaoAutorizacao;
import turmalina.pweb3.model.enums.SituacaoColeta;
import turmalina.pweb3.model.enums.SituacaoExpedicao;
import turmalina.pweb3.model.enums.SituacaoOperacionalEquipamento;
import turmalina.pweb3.model.enums.SituacaoRelatorio;
import turmalina.pweb3.model.enums.TipoEquipamento;
import turmalina.pweb3.model.enums.Titulacao;
import turmalina.pweb3.model.enums.UnidadeMedida;

public class CreateInstances {

    public static void main(String[] args) {
        UserTransaction tx =
                com.arjuna.ats.jta.UserTransaction.userTransaction();

        try (EntityManagerFactory emf =
                     Persistence.createEntityManagerFactory("turmalinaPU");
             EntityManager em = emf.createEntityManager()) {

            tx.begin();

            Long quantidadeSeed = em.createQuery(
                            "select count(e) from Expedicao e where e.codigo = :codigo",
                            Long.class)
                    .setParameter("codigo", "TURM-E001")
                    .getSingleResult();

            if (quantidadeSeed > 0) {
                System.out.println("A carga inicial ja existe. Nenhum registro foi duplicado.");
                tx.commit();
                return;
            }

            // ---------- Cavernas ----------
            Caverna cavernaPedra = new Caverna();
            cavernaPedra.setNome("Caverna da Pedra Branca");
            cavernaPedra.setCodigoAmbiental("SEED-CAV-001");
            cavernaPedra.setMunicipio("Pedra Lavrada");
            cavernaPedra.setUf("PB");
            cavernaPedra.setLocalizacao(new Localizacao(
                    new BigDecimal("-36.757100"),
                    new BigDecimal("-6.756400"),
                    Datum.SIRGAS2000));
            cavernaPedra.setAltitude(new BigDecimal("615.40"));
            cavernaPedra.setExtensaoConhecida(new BigDecimal("1280.50"));
            cavernaPedra.setDataUltimaInspecao(LocalDate.of(2026, 2, 10));
            cavernaPedra.setAcessoPermitido(true);
            em.persist(cavernaPedra);

            Caverna cavernaLajedo = new Caverna();
            cavernaLajedo.setNome("Gruta do Lajedo Azul");
            cavernaLajedo.setCodigoAmbiental("SEED-CAV-002");
            cavernaLajedo.setMunicipio("Cabaceiras");
            cavernaLajedo.setUf("PB");
            cavernaLajedo.setLocalizacao(new Localizacao(
                    new BigDecimal("-36.287800"),
                    new BigDecimal("-7.489300"),
                    Datum.WGS84));
            cavernaLajedo.setAltitude(new BigDecimal("472.10"));
            cavernaLajedo.setExtensaoConhecida(new BigDecimal("890.75"));
            cavernaLajedo.setDataUltimaInspecao(LocalDate.of(2026, 3, 15));
            cavernaLajedo.setAcessoPermitido(true);
            em.persist(cavernaLajedo);

            // ---------- Setores ----------
            Setor salaoPrincipal = new Setor();
            salaoPrincipal.setDenominacao("Salao Principal");
            salaoPrincipal.setDescricao("Entrada ampla com formacoes calcareas.");
            salaoPrincipal.setNivelDificuldade(NivelDificuldade.BAIXO);
            salaoPrincipal.setProfundidadeMaxima(new BigDecimal("18.50"));
            salaoPrincipal.setExtensaoAproximada(new BigDecimal("310.00"));
            salaoPrincipal.setRiscoInundacao(false);
            salaoPrincipal.setCondicao(CondicaoSetor.BOM);
            salaoPrincipal.setCaverna(cavernaPedra);
            cavernaPedra.getSetores().add(salaoPrincipal);
            em.persist(salaoPrincipal);

            Setor galeriaLeste = new Setor();
            galeriaLeste.setDenominacao("Galeria Leste");
            galeriaLeste.setDescricao("Galeria estreita com trechos de progressao vertical.");
            galeriaLeste.setNivelDificuldade(NivelDificuldade.ALTO);
            galeriaLeste.setProfundidadeMaxima(new BigDecimal("54.20"));
            galeriaLeste.setExtensaoAproximada(new BigDecimal("485.60"));
            galeriaLeste.setRiscoInundacao(true);
            galeriaLeste.setCondicao(CondicaoSetor.REGULAR);
            galeriaLeste.setCaverna(cavernaPedra);
            cavernaPedra.getSetores().add(galeriaLeste);
            em.persist(galeriaLeste);

            Setor salaoAzul = new Setor();
            salaoAzul.setDenominacao("Salao Azul");
            salaoAzul.setDescricao("Salao seco com depositos minerais azulados.");
            salaoAzul.setNivelDificuldade(NivelDificuldade.MODERADO);
            salaoAzul.setProfundidadeMaxima(new BigDecimal("26.80"));
            salaoAzul.setExtensaoAproximada(new BigDecimal("275.30"));
            salaoAzul.setRiscoInundacao(false);
            salaoAzul.setCondicao(CondicaoSetor.BOM);
            salaoAzul.setCaverna(cavernaLajedo);
            cavernaLajedo.getSetores().add(salaoAzul);
            em.persist(salaoAzul);

            Setor corredorUmido = new Setor();
            corredorUmido.setDenominacao("Corredor Umido");
            corredorUmido.setDescricao("Conduto inferior sujeito a elevacao do nivel da agua.");
            corredorUmido.setNivelDificuldade(NivelDificuldade.EXTREMO);
            corredorUmido.setProfundidadeMaxima(new BigDecimal("72.00"));
            corredorUmido.setExtensaoAproximada(new BigDecimal("198.40"));
            corredorUmido.setRiscoInundacao(true);
            corredorUmido.setCondicao(CondicaoSetor.REGULAR);
            corredorUmido.setCaverna(cavernaLajedo);
            cavernaLajedo.getSetores().add(corredorUmido);
            em.persist(corredorUmido);

            // ---------- Pessoas ----------
            Pesquisador pesquisadoraAna = new Pesquisador();
            pesquisadoraAna.setNome("Ana Beatriz Lima");
            pesquisadoraAna.setCpf("11122233344");
            pesquisadoraAna.setDataNascimento(LocalDate.of(1987, 4, 12));
            pesquisadoraAna.setEmail("ana.lima@turmalina.test");
            pesquisadoraAna.setTelefone("83999990001");
            pesquisadoraAna.setAtiva(true);
            pesquisadoraAna.setEndereco(new Endereco(
                    "Rua das Acacias", "120", "Apto 301", "Centro",
                    "Joao Pessoa", "PB", "58000001"));
            pesquisadoraAna.setRegistroInstitucional("PESQ-001");
            pesquisadoraAna.setAreaPrincipal("Geologia");
            pesquisadoraAna.setValorDiarioBolsa(new BigDecimal("420.00"));
            pesquisadoraAna.setTitulacao(Titulacao.DOUTORADO);
            em.persist(pesquisadoraAna);

            Pesquisador pesquisadorBruno = new Pesquisador();
            pesquisadorBruno.setNome("Bruno Costa Alves");
            pesquisadorBruno.setCpf("22233344455");
            pesquisadorBruno.setDataNascimento(LocalDate.of(1991, 9, 3));
            pesquisadorBruno.setEmail("bruno.alves@turmalina.test");
            pesquisadorBruno.setTelefone("83999990002");
            pesquisadorBruno.setAtiva(true);
            pesquisadorBruno.setEndereco(new Endereco(
                    "Avenida do Sol", "845", null, "Catole",
                    "Campina Grande", "PB", "58400002"));
            pesquisadorBruno.setRegistroInstitucional("PESQ-002");
            pesquisadorBruno.setAreaPrincipal("Biologia subterranea");
            pesquisadorBruno.setValorDiarioBolsa(new BigDecimal("380.00"));
            pesquisadorBruno.setTitulacao(Titulacao.MESTRADO);
            em.persist(pesquisadorBruno);

            GuiaEspeleologia guiaCarlos = new GuiaEspeleologia();
            guiaCarlos.setNome("Carlos Eduardo Nunes");
            guiaCarlos.setCpf("33344455566");
            guiaCarlos.setDataNascimento(LocalDate.of(1982, 1, 25));
            guiaCarlos.setEmail("carlos.nunes@turmalina.test");
            guiaCarlos.setTelefone("83999990003");
            guiaCarlos.setAtiva(true);
            guiaCarlos.setEndereco(new Endereco(
                    "Rua do Sertao", "31", null, "Alto Branco",
                    "Campina Grande", "PB", "58400003"));
            guiaCarlos.setNumeroCredenciamento("GUIA-001");
            guiaCarlos.setValidadeCertificado(LocalDate.of(2028, 12, 31));
            guiaCarlos.setExpedicoesConcluidas(48);
            guiaCarlos.setNivelCertificado(NivelCertificado.ESPECIALISTA);
            em.persist(guiaCarlos);

            GuiaEspeleologia guiaDiana = new GuiaEspeleologia();
            guiaDiana.setNome("Diana Martins Rocha");
            guiaDiana.setCpf("44455566677");
            guiaDiana.setDataNascimento(LocalDate.of(1989, 7, 18));
            guiaDiana.setEmail("diana.rocha@turmalina.test");
            guiaDiana.setTelefone("83999990004");
            guiaDiana.setAtiva(true);
            guiaDiana.setEndereco(new Endereco(
                    "Rua das Pedras", "77", "Casa", "Centro",
                    "Cabaceiras", "PB", "58480000"));
            guiaDiana.setNumeroCredenciamento("GUIA-002");
            guiaDiana.setValidadeCertificado(LocalDate.of(2027, 8, 20));
            guiaDiana.setExpedicoesConcluidas(27);
            guiaDiana.setNivelCertificado(NivelCertificado.AVANCADO);
            em.persist(guiaDiana);

            // ---------- Equipamentos ----------
            Equipamento detector = new Equipamento();
            detector.setCodigoPatrimonial("SEED-EQP-001");
            detector.setNome("Detector multigas");
            detector.setTipo(TipoEquipamento.MEDICAO);
            detector.setFabricante("CaveSafe");
            detector.setValorAquisicao(new BigDecimal("4850.00"));
            detector.setDataCompra(LocalDate.of(2025, 1, 20));
            detector.setDataUltimaManutencao(LocalDate.of(2026, 4, 2));
            detector.setSituacao(SituacaoOperacionalEquipamento.DISPONIVEL);
            detector.setExigeCalibracao(true);
            em.persist(detector);

            Equipamento radio = new Equipamento();
            radio.setCodigoPatrimonial("SEED-EQP-002");
            radio.setNome("Radio subterraneo");
            radio.setTipo(TipoEquipamento.COMUNICACAO);
            radio.setFabricante("GeoCom");
            radio.setValorAquisicao(new BigDecimal("7200.00"));
            radio.setDataCompra(LocalDate.of(2025, 5, 8));
            radio.setDataUltimaManutencao(LocalDate.of(2026, 5, 10));
            radio.setSituacao(SituacaoOperacionalEquipamento.EM_USO);
            radio.setExigeCalibracao(false);
            em.persist(radio);

            Equipamento iluminacao = new Equipamento();
            iluminacao.setCodigoPatrimonial("SEED-EQP-003");
            iluminacao.setNome("Kit de iluminacao de capacete");
            iluminacao.setTipo(TipoEquipamento.ILUMINACAO);
            iluminacao.setFabricante("Lumen Cave");
            iluminacao.setValorAquisicao(new BigDecimal("1350.00"));
            iluminacao.setDataCompra(LocalDate.of(2024, 11, 14));
            iluminacao.setDataUltimaManutencao(LocalDate.of(2026, 3, 28));
            iluminacao.setSituacao(SituacaoOperacionalEquipamento.DISPONIVEL);
            iluminacao.setExigeCalibracao(false);
            em.persist(iluminacao);

            Equipamento cordas = new Equipamento();
            cordas.setCodigoPatrimonial("SEED-EQP-004");
            cordas.setNome("Conjunto de cordas estaticas");
            cordas.setTipo(TipoEquipamento.SEGURANCA);
            cordas.setFabricante("Vertical Pro");
            cordas.setValorAquisicao(new BigDecimal("2100.00"));
            cordas.setDataCompra(LocalDate.of(2025, 8, 3));
            cordas.setDataUltimaManutencao(LocalDate.of(2026, 6, 1));
            cordas.setSituacao(SituacaoOperacionalEquipamento.DISPONIVEL);
            cordas.setExigeCalibracao(false);
            em.persist(cordas);

            // ---------- Expedicoes ----------
            Expedicao expedicaoConcluida = new Expedicao();
            expedicaoConcluida.setCodigo("TURM-E001");
            expedicaoConcluida.setTitulo("Mapeamento da Pedra Branca");
            expedicaoConcluida.setObjetivo("Atualizar o mapa e coletar amostras geologicas.");
            expedicaoConcluida.setInicioPrevisto(LocalDateTime.of(2026, 5, 12, 7, 0));
            expedicaoConcluida.setTerminoPrevisto(LocalDateTime.of(2026, 5, 15, 18, 0));
            expedicaoConcluida.setOrcamentoAprovado(new BigDecimal("28500.00"));
            expedicaoConcluida.setCustoRealizado(new BigDecimal("27180.50"));
            expedicaoConcluida.setMaxParticipantes(8);
            expedicaoConcluida.setSituacao(SituacaoExpedicao.CONCLUIDA);
            expedicaoConcluida.setCancelamentoEmergencial(false);
            expedicaoConcluida.setCaverna(cavernaPedra);
            expedicaoConcluida.getSetores().add(salaoPrincipal);
            expedicaoConcluida.getSetores().add(galeriaLeste);
            em.persist(expedicaoConcluida);

            Expedicao expedicaoAutorizada = new Expedicao();
            expedicaoAutorizada.setCodigo("TURM-E002");
            expedicaoAutorizada.setTitulo("Biodiversidade do Lajedo Azul");
            expedicaoAutorizada.setObjetivo("Catalogar fauna subterranea e parametros ambientais.");
            expedicaoAutorizada.setInicioPrevisto(LocalDateTime.of(2026, 10, 14, 6, 30));
            expedicaoAutorizada.setTerminoPrevisto(LocalDateTime.of(2026, 10, 18, 17, 0));
            expedicaoAutorizada.setOrcamentoAprovado(new BigDecimal("34600.00"));
            expedicaoAutorizada.setCustoRealizado(BigDecimal.ZERO);
            expedicaoAutorizada.setMaxParticipantes(10);
            expedicaoAutorizada.setSituacao(SituacaoExpedicao.AUTORIZADA);
            expedicaoAutorizada.setCancelamentoEmergencial(false);
            expedicaoAutorizada.setCaverna(cavernaLajedo);
            expedicaoAutorizada.getSetores().add(salaoAzul);
            expedicaoAutorizada.getSetores().add(corredorUmido);
            em.persist(expedicaoAutorizada);

            Expedicao expedicaoPlanejada = new Expedicao();
            expedicaoPlanejada.setCodigo("TURM-E003");
            expedicaoPlanejada.setTitulo("Monitoramento da Galeria Leste");
            expedicaoPlanejada.setObjetivo("Monitorar pontos de inundacao e estabilidade da galeria.");
            expedicaoPlanejada.setInicioPrevisto(LocalDateTime.of(2026, 12, 3, 8, 0));
            expedicaoPlanejada.setTerminoPrevisto(LocalDateTime.of(2026, 12, 5, 16, 0));
            expedicaoPlanejada.setOrcamentoAprovado(new BigDecimal("19200.00"));
            expedicaoPlanejada.setCustoRealizado(BigDecimal.ZERO);
            expedicaoPlanejada.setMaxParticipantes(6);
            expedicaoPlanejada.setSituacao(SituacaoExpedicao.PLANEJADA);
            expedicaoPlanejada.setCancelamentoEmergencial(false);
            expedicaoPlanejada.setCaverna(cavernaPedra);
            expedicaoPlanejada.getSetores().add(galeriaLeste);
            em.persist(expedicaoPlanejada);

            // ---------- Planos de seguranca ----------
            PlanoSeguranca planoConcluida = new PlanoSeguranca();
            planoConcluida.setVersao("1.0");
            planoConcluida.setDataElaboracao(LocalDate.of(2026, 4, 20));
            planoConcluida.setProcedimentosEmergencia("Acionar resgate e conduzir a equipe ao ponto externo.");
            planoConcluida.setPontosEncontro("Entrada principal e base de apoio municipal.");
            planoConcluida.setContatoEmergencia("Defesa Civil: 199");
            planoConcluida.setTempoMaxSemComunicacao(60);
            planoConcluida.setEquipeMedicaNecessaria(true);
            planoConcluida.setMapaRota("mapa ficticio E001".getBytes(StandardCharsets.UTF_8));
            planoConcluida.setExpedicao(expedicaoConcluida);
            expedicaoConcluida.setPlanoSeguranca(planoConcluida);
            em.persist(planoConcluida);

            PlanoSeguranca planoAutorizada = new PlanoSeguranca();
            planoAutorizada.setVersao("1.1");
            planoAutorizada.setDataElaboracao(LocalDate.of(2026, 8, 25));
            planoAutorizada.setProcedimentosEmergencia("Suspender a atividade em caso de chuva e evacuar pelo Salao Azul.");
            planoAutorizada.setPontosEncontro("Salao Azul e acampamento externo.");
            planoAutorizada.setContatoEmergencia("SAMU: 192");
            planoAutorizada.setTempoMaxSemComunicacao(45);
            planoAutorizada.setEquipeMedicaNecessaria(true);
            planoAutorizada.setMapaRota("mapa ficticio E002".getBytes(StandardCharsets.UTF_8));
            planoAutorizada.setExpedicao(expedicaoAutorizada);
            expedicaoAutorizada.setPlanoSeguranca(planoAutorizada);
            em.persist(planoAutorizada);

            PlanoSeguranca planoPlanejada = new PlanoSeguranca();
            planoPlanejada.setVersao("0.9");
            planoPlanejada.setDataElaboracao(LocalDate.of(2026, 9, 18));
            planoPlanejada.setProcedimentosEmergencia("Retornar ao Salao Principal e comunicar a coordenacao.");
            planoPlanejada.setPontosEncontro("Salao Principal e estacionamento da reserva.");
            planoPlanejada.setContatoEmergencia("Bombeiros: 193");
            planoPlanejada.setTempoMaxSemComunicacao(40);
            planoPlanejada.setEquipeMedicaNecessaria(false);
            planoPlanejada.setMapaRota("mapa ficticio E003".getBytes(StandardCharsets.UTF_8));
            planoPlanejada.setExpedicao(expedicaoPlanejada);
            expedicaoPlanejada.setPlanoSeguranca(planoPlanejada);
            em.persist(planoPlanejada);

            // ---------- Autorizacoes ----------
            AutorizacaoAmbiental autorizacaoConcluida = new AutorizacaoAmbiental();
            autorizacaoConcluida.setNumero("AUT-SEED-001");
            autorizacaoConcluida.setOrgaoEmissor("ICMBio");
            autorizacaoConcluida.setDataEmissao(LocalDate.of(2026, 3, 25));
            autorizacaoConcluida.setDataValidade(LocalDate.of(2026, 8, 31));
            autorizacaoConcluida.setSituacaoAutorizacao(SituacaoAutorizacao.APROVADA);
            autorizacaoConcluida.setObservacoes("Autorizacao usada na expedicao concluida.");
            autorizacaoConcluida.setArquivoPdf(
                    "autorizacao ficticia E001".getBytes(StandardCharsets.UTF_8));
            autorizacaoConcluida.setExpedicao(expedicaoConcluida);
            em.persist(autorizacaoConcluida);

            AutorizacaoAmbiental autorizacaoAtual = new AutorizacaoAmbiental();
            autorizacaoAtual.setNumero("AUT-SEED-002");
            autorizacaoAtual.setOrgaoEmissor("SUDEMA");
            autorizacaoAtual.setDataEmissao(LocalDate.of(2026, 8, 30));
            autorizacaoAtual.setDataValidade(LocalDate.of(2027, 2, 28));
            autorizacaoAtual.setSituacaoAutorizacao(SituacaoAutorizacao.APROVADA);
            autorizacaoAtual.setObservacoes("Acesso condicionado ao monitoramento meteorologico.");
            autorizacaoAtual.setArquivoPdf(
                    "autorizacao ficticia E002".getBytes(StandardCharsets.UTF_8));
            autorizacaoAtual.setExpedicao(expedicaoAutorizada);
            em.persist(autorizacaoAtual);

            // ---------- Participacoes ----------
            Participacao participacao1 = new Participacao();
            participacao1.setPapel(PapelParticipante.PESQUISADOR);
            participacao1.setDataConfirmacao(LocalDate.of(2026, 4, 15));
            participacao1.setValorDiaria(new BigDecimal("420.00"));
            participacao1.setQuantidadeDiasPrevista(4);
            participacao1.setPresencaConfirmada(true);
            participacao1.setObservacoes("Coordenadora cientifica.");
            participacao1.setExpedicao(expedicaoConcluida);
            participacao1.setPessoa(pesquisadoraAna);
            em.persist(participacao1);

            Participacao participacao2 = new Participacao();
            participacao2.setPapel(PapelParticipante.GUIA_ESPELEOLOGIA);
            participacao2.setDataConfirmacao(LocalDate.of(2026, 4, 16));
            participacao2.setValorDiaria(new BigDecimal("500.00"));
            participacao2.setQuantidadeDiasPrevista(4);
            participacao2.setPresencaConfirmada(true);
            participacao2.setObservacoes("Responsavel pela progressao vertical.");
            participacao2.setExpedicao(expedicaoConcluida);
            participacao2.setPessoa(guiaCarlos);
            em.persist(participacao2);

            Participacao participacao3 = new Participacao();
            participacao3.setPapel(PapelParticipante.PESQUISADOR);
            participacao3.setDataConfirmacao(LocalDate.of(2026, 9, 2));
            participacao3.setValorDiaria(new BigDecimal("380.00"));
            participacao3.setQuantidadeDiasPrevista(5);
            participacao3.setPresencaConfirmada(true);
            participacao3.setObservacoes("Responsavel pelas coletas biologicas.");
            participacao3.setExpedicao(expedicaoAutorizada);
            participacao3.setPessoa(pesquisadorBruno);
            em.persist(participacao3);

            Participacao participacao4 = new Participacao();
            participacao4.setPapel(PapelParticipante.GUIA_ESPELEOLOGIA);
            participacao4.setDataConfirmacao(LocalDate.of(2026, 9, 3));
            participacao4.setValorDiaria(new BigDecimal("460.00"));
            participacao4.setQuantidadeDiasPrevista(5);
            participacao4.setPresencaConfirmada(true);
            participacao4.setObservacoes("Guia principal.");
            participacao4.setExpedicao(expedicaoAutorizada);
            participacao4.setPessoa(guiaDiana);
            em.persist(participacao4);

            Participacao participacao5 = new Participacao();
            participacao5.setPapel(PapelParticipante.PESQUISADOR);
            participacao5.setDataConfirmacao(LocalDate.of(2026, 9, 4));
            participacao5.setValorDiaria(new BigDecimal("420.00"));
            participacao5.setQuantidadeDiasPrevista(5);
            participacao5.setPresencaConfirmada(true);
            participacao5.setObservacoes("Apoio na analise ambiental.");
            participacao5.setExpedicao(expedicaoAutorizada);
            participacao5.setPessoa(pesquisadoraAna);
            em.persist(participacao5);

            Participacao participacao6 = new Participacao();
            participacao6.setPapel(PapelParticipante.PESQUISADOR);
            participacao6.setDataConfirmacao(LocalDate.of(2026, 9, 20));
            participacao6.setValorDiaria(new BigDecimal("420.00"));
            participacao6.setQuantidadeDiasPrevista(3);
            participacao6.setPresencaConfirmada(true);
            participacao6.setObservacoes("Responsavel pelo monitoramento geologico.");
            participacao6.setExpedicao(expedicaoPlanejada);
            participacao6.setPessoa(pesquisadoraAna);
            em.persist(participacao6);

            Participacao participacao7 = new Participacao();
            participacao7.setPapel(PapelParticipante.GUIA_ESPELEOLOGIA);
            participacao7.setDataConfirmacao(LocalDate.of(2026, 9, 21));
            participacao7.setValorDiaria(new BigDecimal("500.00"));
            participacao7.setQuantidadeDiasPrevista(3);
            participacao7.setPresencaConfirmada(true);
            participacao7.setObservacoes("Apoio de seguranca.");
            participacao7.setExpedicao(expedicaoPlanejada);
            participacao7.setPessoa(guiaCarlos);
            em.persist(participacao7);

            // ---------- Coletas ----------
            Coleta coletaRocha = new Coleta();
            coletaRocha.setDataHora(Instant.parse("2026-05-12T14:30:00Z"));
            coletaRocha.setMetodo("Coleta manual controlada");
            coletaRocha.setDescricaoPonto("Parede norte do Salao Principal");
            coletaRocha.setTemperatura(new BigDecimal("22.40"));
            coletaRocha.setUmidadeRelativa(new BigDecimal("76.50"));
            coletaRocha.setProfundidade(new BigDecimal("12.30"));
            coletaRocha.setObservacoes("Fragmentos soltos, sem intervencao na formacao.");
            coletaRocha.setSituacaoValidacao(SituacaoColeta.VALIDADA);
            coletaRocha.setExpedicao(expedicaoConcluida);
            coletaRocha.setSetor(salaoPrincipal);
            coletaRocha.setPesquisador(pesquisadoraAna);
            em.persist(coletaRocha);

            Coleta coletaAgua = new Coleta();
            coletaAgua.setDataHora(Instant.parse("2026-05-13T16:10:00Z"));
            coletaAgua.setMetodo("Amostragem esteril de agua");
            coletaAgua.setDescricaoPonto("Poco temporario da Galeria Leste");
            coletaAgua.setTemperatura(new BigDecimal("19.80"));
            coletaAgua.setUmidadeRelativa(new BigDecimal("91.20"));
            coletaAgua.setProfundidade(new BigDecimal("41.00"));
            coletaAgua.setObservacoes("Material mantido sob refrigeracao.");
            coletaAgua.setSituacaoValidacao(SituacaoColeta.VALIDADA);
            coletaAgua.setExpedicao(expedicaoConcluida);
            coletaAgua.setSetor(galeriaLeste);
            coletaAgua.setPesquisador(pesquisadoraAna);
            em.persist(coletaAgua);

            Coleta coletaBiologica = new Coleta();
            coletaBiologica.setDataHora(Instant.parse("2026-10-15T13:45:00Z"));
            coletaBiologica.setMetodo("Armadilha nao letal");
            coletaBiologica.setDescricaoPonto("Fenda oeste do Salao Azul");
            coletaBiologica.setTemperatura(new BigDecimal("23.10"));
            coletaBiologica.setUmidadeRelativa(new BigDecimal("82.70"));
            coletaBiologica.setProfundidade(new BigDecimal("20.60"));
            coletaBiologica.setObservacoes("Amostras previstas para identificacao laboratorial.");
            coletaBiologica.setSituacaoValidacao(SituacaoColeta.PENDENTE);
            coletaBiologica.setExpedicao(expedicaoAutorizada);
            coletaBiologica.setSetor(salaoAzul);
            coletaBiologica.setPesquisador(pesquisadorBruno);
            em.persist(coletaBiologica);

            // ---------- Amostras ----------
            Amostra amostraRocha1 = new Amostra();
            amostraRocha1.setCodigoCampo("SEED-AMO-001");
            amostraRocha1.setCategoria(CategoriaAmostra.GEOLOGICA);
            amostraRocha1.setQuantidade(new BigDecimal("425.5000"));
            amostraRocha1.setUnidade(UnidadeMedida.GRAMA);
            amostraRocha1.setDataAcondicionamento(LocalDateTime.of(2026, 5, 12, 12, 0));
            amostraRocha1.setCondicaoConservacao(CondicaoConservacao.EXCELENTE);
            amostraRocha1.setMaterialPerigoso(false);
            amostraRocha1.setFotografia("fotografia ficticia AMO-001".getBytes(StandardCharsets.UTF_8));
            amostraRocha1.setObservacoes("Rocha clara com veios de quartzo.");
            amostraRocha1.setColeta(coletaRocha);
            coletaRocha.getAmostras().add(amostraRocha1);
            em.persist(amostraRocha1);

            Amostra amostraRocha2 = new Amostra();
            amostraRocha2.setCodigoCampo("SEED-AMO-002");
            amostraRocha2.setCategoria(CategoriaAmostra.MINERALOGICA);
            amostraRocha2.setQuantidade(new BigDecimal("185.2500"));
            amostraRocha2.setUnidade(UnidadeMedida.GRAMA);
            amostraRocha2.setDataAcondicionamento(LocalDateTime.of(2026, 5, 12, 12, 15));
            amostraRocha2.setCondicaoConservacao(CondicaoConservacao.BOA);
            amostraRocha2.setMaterialPerigoso(false);
            amostraRocha2.setFotografia("fotografia ficticia AMO-002".getBytes(StandardCharsets.UTF_8));
            amostraRocha2.setObservacoes("Cristais preservados em recipiente rigido.");
            amostraRocha2.setColeta(coletaRocha);
            coletaRocha.getAmostras().add(amostraRocha2);
            em.persist(amostraRocha2);

            Amostra amostraAgua = new Amostra();
            amostraAgua.setCodigoCampo("SEED-AMO-003");
            amostraAgua.setCategoria(CategoriaAmostra.HIDROLOGICA);
            amostraAgua.setQuantidade(new BigDecimal("750.0000"));
            amostraAgua.setUnidade(UnidadeMedida.MILILITRO);
            amostraAgua.setDataAcondicionamento(LocalDateTime.of(2026, 5, 13, 13, 30));
            amostraAgua.setCondicaoConservacao(CondicaoConservacao.EXCELENTE);
            amostraAgua.setMaterialPerigoso(false);
            amostraAgua.setFotografia("fotografia ficticia AMO-003".getBytes(StandardCharsets.UTF_8));
            amostraAgua.setObservacoes("Frasco esteril lacrado.");
            amostraAgua.setColeta(coletaAgua);
            coletaAgua.getAmostras().add(amostraAgua);
            em.persist(amostraAgua);

            Amostra amostraBiologica = new Amostra();
            amostraBiologica.setCodigoCampo("SEED-AMO-004");
            amostraBiologica.setCategoria(CategoriaAmostra.BIOLOGICA);
            amostraBiologica.setQuantidade(new BigDecimal("12.0000"));
            amostraBiologica.setUnidade(UnidadeMedida.MILIGRAMA);
            amostraBiologica.setDataAcondicionamento(LocalDateTime.of(2026, 10, 15, 11, 20));
            amostraBiologica.setCondicaoConservacao(CondicaoConservacao.BOA);
            amostraBiologica.setMaterialPerigoso(false);
            amostraBiologica.setFotografia("fotografia ficticia AMO-004".getBytes(StandardCharsets.UTF_8));
            amostraBiologica.setObservacoes("Material para identificacao taxonomica.");
            amostraBiologica.setColeta(coletaBiologica);
            coletaBiologica.getAmostras().add(amostraBiologica);
            em.persist(amostraBiologica);

            // ---------- Relatorios ----------
            RelatorioFinal relatorio = new RelatorioFinal();
            relatorio.setTitulo("Relatorio de mapeamento da Caverna da Pedra Branca");
            relatorio.setResumo("Resultados cartograficos e caracterizacao das amostras coletadas.");
            relatorio.setDataSubmissao(LocalDate.of(2026, 6, 30));
            relatorio.setTotalPaginas(64);
            relatorio.setSituacao(SituacaoRelatorio.APROVADO);
            relatorio.setArquivoCompleto(
                    "relatorio final ficticio E001".getBytes(StandardCharsets.UTF_8));
            relatorio.setPublicacaoAutorizada(true);
            relatorio.setExpedicao(expedicaoConcluida);
            em.persist(relatorio);

            // ---------- Movimentacoes ----------
            MovimentacaoEquipamento movimentacao1 = new MovimentacaoEquipamento();
            movimentacao1.setRetiradaEm(Instant.parse("2026-05-11T17:00:00Z"));
            movimentacao1.setDevolucaoPrevista(LocalDateTime.of(2026, 5, 16, 12, 0));
            movimentacao1.setDevolucaoEfetiva(Instant.parse("2026-05-16T13:15:00Z"));
            movimentacao1.setEstadoSaida(EstadoEquipamento.OTIMO);
            movimentacao1.setEstadoRetorno(EstadoEquipamento.BOM);
            movimentacao1.setCustoAvaria(BigDecimal.ZERO);
            movimentacao1.setExpedicao(expedicaoConcluida);
            movimentacao1.setEquipamento(detector);
            movimentacao1.setResponsavel(pesquisadoraAna);
            em.persist(movimentacao1);

            MovimentacaoEquipamento movimentacao2 = new MovimentacaoEquipamento();
            movimentacao2.setRetiradaEm(Instant.parse("2026-05-11T17:10:00Z"));
            movimentacao2.setDevolucaoPrevista(LocalDateTime.of(2026, 5, 16, 12, 0));
            movimentacao2.setDevolucaoEfetiva(Instant.parse("2026-05-16T12:50:00Z"));
            movimentacao2.setEstadoSaida(EstadoEquipamento.BOM);
            movimentacao2.setEstadoRetorno(EstadoEquipamento.REGULAR);
            movimentacao2.setCustoAvaria(new BigDecimal("180.00"));
            movimentacao2.setExpedicao(expedicaoConcluida);
            movimentacao2.setEquipamento(iluminacao);
            movimentacao2.setResponsavel(guiaCarlos);
            em.persist(movimentacao2);

            MovimentacaoEquipamento movimentacao3 = new MovimentacaoEquipamento();
            movimentacao3.setRetiradaEm(Instant.parse("2026-10-13T18:00:00Z"));
            movimentacao3.setDevolucaoPrevista(LocalDateTime.of(2026, 10, 19, 10, 0));
            movimentacao3.setEstadoSaida(EstadoEquipamento.OTIMO);
            movimentacao3.setCustoAvaria(BigDecimal.ZERO);
            movimentacao3.setExpedicao(expedicaoAutorizada);
            movimentacao3.setEquipamento(radio);
            movimentacao3.setResponsavel(guiaDiana);
            em.persist(movimentacao3);

            MovimentacaoEquipamento movimentacao4 = new MovimentacaoEquipamento();
            movimentacao4.setRetiradaEm(Instant.parse("2026-10-13T18:15:00Z"));
            movimentacao4.setDevolucaoPrevista(LocalDateTime.of(2026, 10, 19, 10, 0));
            movimentacao4.setEstadoSaida(EstadoEquipamento.BOM);
            movimentacao4.setCustoAvaria(BigDecimal.ZERO);
            movimentacao4.setExpedicao(expedicaoAutorizada);
            movimentacao4.setEquipamento(cordas);
            movimentacao4.setResponsavel(guiaDiana);
            em.persist(movimentacao4);

            tx.commit();
            System.out.println("Carga inicial criada com sucesso.");

        } catch (Exception e) {
            try {
                tx.rollback();
            } catch (Exception rollbackException) {
                e.addSuppressed(rollbackException);
            }

            e.printStackTrace();
            throw new RuntimeException("Nao foi possivel criar a carga inicial.", e);
        } finally {
            TransactionReaper.terminate(false);
            TxControl.disable(true);
        }
    }
}
