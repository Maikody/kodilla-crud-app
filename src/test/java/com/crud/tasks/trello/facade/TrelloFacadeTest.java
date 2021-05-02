package com.crud.tasks.trello.facade;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TrelloMapper;
import com.crud.tasks.service.TrelloService;
import com.crud.tasks.validator.TrelloValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrelloFacadeTest {

    @InjectMocks
    private TrelloFacade trelloFacade;

    @Mock
    private TrelloService trelloService;

    @Mock
    private TrelloValidator trelloValidator;

    @Mock
    private TrelloMapper trelloMapper;


    @Test
    void shouldFetchEmptyList() {
        List<TrelloListDto> trelloLists = List.of(new TrelloListDto("1", "test_list", false));
        List<TrelloBoardDto> trelloBoards = List.of(new TrelloBoardDto("1", "test", trelloLists));
        List<TrelloList> mappedTrelloLists = List.of(new TrelloList("1", "test_list", false));
        List<TrelloBoard> mappedTrelloBoards = List.of(new TrelloBoard("1", "test", mappedTrelloLists));

        when(trelloService.fetchTrelloBoards()).thenReturn(trelloBoards);
        when(trelloMapper.mapToBoards(trelloBoards)).thenReturn(mappedTrelloBoards);
        when(trelloValidator.validateTrelloBoards(mappedTrelloBoards)).thenReturn(List.of());
        when(trelloMapper.mapToBoardsDto(anyList())).thenReturn(List.of());

        List<TrelloBoardDto> trelloBoardDtos = trelloFacade.fetchTrelloBoards();

        assertThat(trelloBoardDtos).isNotNull();
        assertThat(trelloBoardDtos).isEmpty();
    }

    @Test
    void shouldFetchTrelloBoardsList() {
        List<TrelloListDto> trelloLists = List.of(new TrelloListDto("1", "test_list", false));
        List<TrelloBoardDto> trelloBoards = List.of(new TrelloBoardDto("1", "test", trelloLists));
        List<TrelloList> mappedTrelloLists = List.of(new TrelloList("1", "test_list", false));
        List<TrelloBoard> mappedTrelloBoards = List.of(new TrelloBoard("1", "test", mappedTrelloLists));

        when(trelloService.fetchTrelloBoards()).thenReturn(trelloBoards);
        when(trelloMapper.mapToBoards(trelloBoards)).thenReturn(mappedTrelloBoards);
        when(trelloValidator.validateTrelloBoards(anyList())).thenReturn(mappedTrelloBoards);
        when(trelloMapper.mapToBoardsDto(mappedTrelloBoards)).thenReturn(trelloBoards);

        List<TrelloBoardDto> trelloBoardDtos = trelloFacade.fetchTrelloBoards();

        assertThat(trelloBoardDtos).isNotNull();
        assertThat(trelloBoardDtos).hasSize(1);

        trelloBoardDtos.forEach(trelloBoardDto -> {
            assertThat(trelloBoardDto.getId()).isEqualTo("1");
            assertThat(trelloBoardDto.getName()).isEqualTo("test");
            trelloBoardDto.getLists().forEach(trelloListDto -> {
                assertThat(trelloListDto.getId()).isEqualTo("1");
                assertThat(trelloListDto.getName()).isEqualTo("test_list");
                assertThat(trelloListDto.isClosed()).isFalse();
            });
        });
    }

    @Test
    void shouldCreateTrelloCard() {
        TrelloCardDto cardDto = new TrelloCardDto("Test", "Write unit tests", "1", "1");
        TrelloCard card = new TrelloCard("Test", "Write unit tests", "1", "1");
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto("1", "Test", "/cards");

        when(trelloMapper.mapToCard(cardDto)).thenReturn(card);
        when(trelloMapper.mapToCardDto(card)).thenReturn(cardDto);
        when(trelloService.createTrelloCard(cardDto)).thenReturn(createdTrelloCardDto);

        CreatedTrelloCardDto createdCard = trelloFacade.createCard(cardDto);

        verify(trelloValidator, times(1)).validateCard(card);
        assertThat(createdCard).isNotNull();
        assertThat(createdCard.getId()).isEqualTo("1");
        assertThat(createdCard.getName()).isEqualTo("Test");
        assertThat(createdCard.getShortUrl()).isEqualTo("/cards");
    }
}