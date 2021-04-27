package com.crud.tasks.trello.mapper;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TrelloMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

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

        assertEquals(1, trelloBoards.size());
        assertEquals("Kodilla Application", trelloBoards.get(0).getName());
        assertEquals("1", trelloBoards.get(0).getId());
        assertEquals(1, trelloBoards.get(0).getLists().size());
    }

    @Test
    void shouldMapListOfTrelloBoardsToListOfTrelloBoardDtos() {
        List<TrelloList> trelloLists = getListOfTrelloLists();
        List<TrelloBoard> trelloBoard = getListOfTrelloBoards(trelloLists);

        List<TrelloBoardDto> trelloBoardDtos = trelloMapper.mapToBoardsDto(trelloBoard);

        assertEquals(1, trelloBoardDtos.size());
        assertEquals("Kodilla Application", trelloBoardDtos.get(0).getName());
        assertEquals("1", trelloBoardDtos.get(0).getId());
        assertEquals(1, trelloBoardDtos.get(0).getLists().size());
    }

    @Test
    void shouldMapListOfTrelloListDtosToListOfTrelloLists() {
        List<TrelloListDto> trelloListDtos = getListOfTrelloListDtos();

        List<TrelloList> trelloLists = trelloMapper.mapToList(trelloListDtos);

        assertEquals(1, trelloLists.size());
        assertEquals("TODO", trelloLists.get(0).getName());
        assertEquals("1", trelloLists.get(0).getId());
        assertFalse(trelloLists.get(0).isClosed());
    }

    @Test
    void shouldMapListOfTrelloListsToListOfTrelloListDtos() {
        List<TrelloList> trelloLists = getListOfTrelloLists();

        List<TrelloListDto> trelloListDtos = trelloMapper.mapToListDto(trelloLists);

        assertEquals(1, trelloListDtos.size());
        assertEquals("TODO", trelloListDtos.get(0).getName());
        assertEquals("1", trelloListDtos.get(0).getId());
        assertFalse(trelloListDtos.get(0).isClosed());
    }

    @Test
    void shouldMapCardDtoToCard() {
        TrelloCardDto cardDto = new TrelloCardDto("Postman", "Test controllers", "1", "1");

        TrelloCard trelloCard = trelloMapper.mapToCard(cardDto);

        assertEquals("Postman", trelloCard.getName());
        assertEquals("Test controllers", trelloCard.getDescription());
        assertEquals("1", trelloCard.getPos());
        assertEquals("1", trelloCard.getListId());
    }

    @Test
    void shouldMapCardToCardDto() {
        TrelloCard card = new TrelloCard("Postman", "Test controllers", "1", "1");

        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(card);

        assertEquals("Postman", trelloCardDto.getName());
        assertEquals("Test controllers", trelloCardDto.getDescription());
        assertEquals("1", trelloCardDto.getPos());
        assertEquals("1", trelloCardDto.getListId());
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
