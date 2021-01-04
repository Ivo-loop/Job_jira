package main;

import com.atlassian.jira.rest.client.api.IssueRestClient;
import com.atlassian.jira.rest.client.api.domain.BasicIssue;
import com.atlassian.jira.rest.client.api.domain.input.IssueInputBuilder;
import entities.JiraEntity;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static tools.FileTools.getStringByFilePath;

public class Executavel {

    private static final String EPIC_LINK = "customfield_10715";

    public static void main(String[] args) throws IOException {
        String jiraUrl = "https://jira.hbsis.com.br/";
        String username = args[0];
        String password = args[1];
        String issueTypeId = args[2];
        String epicLink = args[3];
        String filePathTemplate = args[4];
        String filePathCSV = args[5];

        Logger logger = Logger.getLogger("Executavel");

        JiraEntity jiraEntity = new JiraEntity(username, password, jiraUrl);
        IssueRestClient issueClient = jiraEntity.getRestClient().getIssueClient();

        String fileTemplate = getStringByFilePath(filePathTemplate);
        String fileCSV = getStringByFilePath(filePathCSV);

        String[] split = fileTemplate.split("Descrição:");
        String[] servicos = fileCSV.split(";");

        for (String servico : servicos) {
            String titulo = split[0].replaceAll("\\$\\{NOME_DO_SERVIÇO}", servico).replace("Titulo:", "");
            String descricao = split[1].replaceAll("\\$\\{NOME_DO_SERVIÇO}", servico);

            IssueInputBuilder builder = new IssueInputBuilder();
            builder.setProjectKey("HER");
            builder.setIssueTypeId(Long.parseLong(issueTypeId));
            builder.setSummary(titulo);
            builder.setDescription(descricao);
            builder.setFieldValue(EPIC_LINK, epicLink);

            BasicIssue claim = issueClient.createIssue(builder.build()).claim();
            String msg = "jira criado:" + claim.getKey();
            logger.log(Level.OFF, msg);
        }
    }
}
