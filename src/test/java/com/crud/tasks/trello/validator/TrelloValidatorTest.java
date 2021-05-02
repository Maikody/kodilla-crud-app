package com.crud.tasks.trello.validator;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloCard;
import com.crud.tasks.validator.TrelloValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = {TrelloValidator.class})
class TrelloValidatorTest {

    @Autowired
    private TrelloValidator trelloValidator;


    @Test
    void shouldReturnFalseForNotValidCard() {
        TrelloCard card = new TrelloCard("Test", "Test", "2", "1");

        boolean validateCard = trelloValidator.validateCard(card);

        assertThat(validateCard).isFalse();
    }

    @Test
    void shouldReturnTrueForValidCard() {
        TrelloCard card = new TrelloCard("Postman", "Test controller", "2", "1");

        boolean validateCard = trelloValidator.validateCard(card);

        assertThat(validateCard).isTrue();
    }

    @Test
    void shouldReturnOnlyValidTrelloBoards() {
        List<TrelloBoard> listOfTrelloBoards = getListOfTrelloBoards();

        List<TrelloBoard> validatedTrelloBoards = trelloValidator.validateTrelloBoards(listOfTrelloBoards);

        assertThat(validatedTrelloBoards)
                        .isNotNull()
                        .hasSize(1);
    }

    private List<TrelloBoard> getListOfTrelloBoards() {
        TrelloBoard trelloBoard1 = new TrelloBoard("1", "TEST", List.of());
        TrelloBoard trelloBoard2 = new TrelloBoard("2", "Kodilla Application", List.of());
        return List.of(trelloBoard1, trelloBoard2);
    }
}