package com.crud.tasks.trello.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.service.SimpleEmailService;
import com.crud.tasks.service.TrelloService;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrelloServiceTest {

    @InjectMocks
    private TrelloService trelloService;

    @Mock
    private TrelloClient trelloClient;

    @Mock
    private SimpleEmailService emailService;

    @Mock
    private AdminConfig adminConfig;

    @Test
    void shouldFetchTrelloBoards() {
        when(trelloClient.getTrelloBoards()).thenReturn(List.of());

        List<TrelloBoardDto> trelloBoardDtos = trelloService.fetchTrelloBoards();

        assertThat(trelloBoardDtos).isEmpty();
    }

    @Test
    void shouldCreateTrelloCard() {
        TrelloCardDto cardDto = new TrelloCardDto("Test", "Write unit tests", "1", "1");
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto("1", "Test", "/cards");
        Mail mail = Mail.builder()
                .mailTo("admin@example.com")
                .subject("Tasks: New Trello Card")
                .message("New Card: Test has been created on your account.")
                .build();

        when(trelloClient.createNewCard(cardDto)).thenReturn(createdTrelloCardDto);
        when(adminConfig.getMail()).thenReturn("admin@example.com");

        CreatedTrelloCardDto trelloCard = trelloService.createTrelloCard(cardDto);

        verify(emailService, times(1)).send(mail);
        assertThat(trelloCard).isNotNull();
        assertThat(trelloCard.getId()).isEqualTo("1");
        assertThat(trelloCard.getName()).isEqualTo("Test");
        assertThat(trelloCard.getShortUrl()).isEqualTo("/cards");
    }
}