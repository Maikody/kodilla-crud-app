package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;

@Service
public class MailCreatorService {

    @Autowired
    private AdminConfig adminConfig;
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;
    @Value("${info.company.name}")
    private String companyName;
    @Value("${info.company.email}")
    private String companyEmail;

    public String buildTrelloCardEmail(String message) {
        List<String> functionalities = new ArrayList<>();
        functionalities.add("You can manage your tasks");
        functionalities.add("Provides connection with Trello Account");
        functionalities.add("Application allows sending tasks to Trello");

        Context context = new Context();
        context.setVariable("preview", "New task appeared in your account");
        context.setVariable("message", message);
        context.setVariable("tasks_url", "http://maikody.github.io");
        context.setVariable("button", "Visit website");
        context.setVariable("goodbye_message","Thanks,\nYour CrudApp Team");
        context.setVariable("company_name", companyName);
        context.setVariable("company_email", companyEmail);
        context.setVariable("show_button", false);
        context.setVariable("is_friend", true);
        context.setVariable("admin_config", adminConfig);
        context.setVariable("application_functionality", functionalities);

        return templateEngine.process("mail/created-trello-card-mail", context);
    }

    public String buildDailyInfoEmail(String message) {
        long tasksNumber = taskRepository.count();

        Context context = new Context();
        context.setVariable("preview", "Daily information about your tasks");
        context.setVariable("message", message);
        context.setVariable("tasks_url", "http://maikody.github.io");
        context.setVariable("button", "Visit website");
        context.setVariable("goodbye_message","Thanks,\nYour CrudApp Team");
        context.setVariable("company_name", companyName);
        context.setVariable("company_email", companyEmail);
        context.setVariable("show_button", true);
        context.setVariable("is_friend", true);
        context.setVariable("admin_config", adminConfig);

        if (tasksNumber == 0)
            context.setVariable("zero_tasks",true);

        return templateEngine.process("mail/daily-info-mail", context);
    }
}
