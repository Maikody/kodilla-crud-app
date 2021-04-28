package com.crud.tasks.trello.mapper;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TrelloMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = {TrelloMapper.class})
class TrelloMapperTest {

    @Autowired
    private TrelloMapper trelloMapper;

    @Test
    void shouldMapListOfTrelloBoardDtosToListOfTrelloBoards() {
        List<TrelloListDto> trelloListDtos = getListOfTrelloListDtos();
        List<TrelloBoardDto> trelloBoardDtos = getListOfTrelloBoardDtos(trelloListDtos);

        List<TrelloBoard> trelloBoards = trelloMapper.mapToBoards(trelloBoardDtos);

        assertThat(trelloBoards).hasSize(1);
        assertThat(trelloBoards).extracting("id").containsOnly("1");
        assertThat(trelloBoards).extracting("name").containsOnly("Kodilla Application");
        assertThat(trelloBoards).extracting("lists").hasSize(1);
    }

    @Test
    void shouldMapListOfTrelloBoardsToListOfTrelloBoardDtos() {
        List<TrelloList> trelloLists = getListOfTrelloLists();
        List<TrelloBoard> trelloBoard = getListOfTrelloBoards(trelloLists);

        List<TrelloBoardDto> trelloBoardDtos = trelloMapper.mapToBoardsDto(trelloBoard);

        assertThat(trelloBoardDtos).hasSize(1);
        assertThat(trelloBoardDtos).extracting("id").containsOnly("1");
        assertThat(trelloBoardDtos).extracting("name").containsOnly("Kodilla Application");
        assertThat(trelloBoardDtos).extracting("lists").hasSize(1);
    }

    @Test
    void shouldMapListOfTrelloListDtosToListOfTrelloLists() {
        List<TrelloListDto> trelloListDtos = getListOfTrelloListDtos();

        List<TrelloList> trelloLists = trelloMapper.mapToList(trelloListDtos);

        assertThat(trelloLists).hasSize(1);
        assertThat(trelloLists).extracting("id").containsOnly("1");
        assertThat(trelloLists).extracting("name").containsOnly("TODO");
        assertThat(trelloLists).extracting("isClosed").containsOnly(false);
    }

    @Test
    void shouldMapListOfTrelloListsToListOfTrelloListDtos() {
        List<TrelloList> trelloLists = getListOfTrelloLists();

        List<TrelloListDto> trelloListDtos = trelloMapper.mapToListDto(trelloLists);

        assertThat(trelloListDtos).hasSize(1);
        assertThat(trelloListDtos).extracting("id").containsOnly("1");
        assertThat(trelloListDtos).extracting("name").containsOnly("TODO");
        assertThat(trelloListDtos).extracting("isClosed").containsOnly(false);
    }

    @Test
    void shouldMapCardDtoToCard() {
        TrelloCardDto cardDto = new TrelloCardDto("Postman", "Test controllers", "1", "1");

        TrelloCard trelloCard = trelloMapper.mapToCard(cardDto);

        assertThat(trelloCard).extracting("name").containsExactly("Postman");
        assertThat(trelloCard).extracting("description").containsExactly("Test controllers");
        assertThat(trelloCard).extracting("pos").containsExactly("1");
        assertThat(trelloCard).extracting("listId").containsExactly("1");
    }

    @Test
    void shouldMapCardToCardDto() {
        TrelloCard card = new TrelloCard("Postman", "Test controllers", "1", "1");

        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(card);

        assertThat(trelloCardDto).extracting("name").containsExactly("Postman");
        assertThat(trelloCardDto).extracting("description").containsExactly("Test controllers");
        assertThat(trelloCardDto).extracting("pos").containsExactly("1");
        assertThat(trelloCardDto).extracting("listId").containsExactly("1");
    }

    private List<TrelloBoard> getListOfTrelloBoards(List<TrelloList> trelloLists) {
        TrelloBoard trelloBoard1 = new TrelloBoard("Kodilla Application", "1", trelloLists);
        return List.of(trelloBoard1);
    }

    private List<TrelloBoardDto> getListOfTrelloBoardDtos(List<TrelloListDto> trelloListDtos) {
        TrelloBoardDto trelloBoardDto1 = new TrelloBoardDto("Kodilla Application", "1", trelloListDtos);
        return List.of(trelloBoardDto1);
    }

    private List<TrelloList> getListOfTrelloLists() {
        TrelloList trelloList1 = new TrelloList("1", "TODO", false);
        return List.of(trelloList1);
    }

    private List<TrelloListDto> getListOfTrelloListDtos() {
        TrelloListDto trelloListDto1 = new TrelloListDto("1","TODO",false);
        return List.of(trelloListDto1);
    }
}
