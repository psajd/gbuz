package com.psajd.gbuz;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import com.psajd.gbuz.entities.KeyCard;
import com.psajd.gbuz.repositories.KeyCardRepository;
import com.psajd.gbuz.services.KeyCardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class KeyCardServiceTest {

    @Mock
    private KeyCardRepository keyCardRepository;

    @InjectMocks
    private KeyCardService keyCardService;

    private KeyCard keyCard;

    @BeforeEach
    void setUp() {
        keyCard = new KeyCard();
        keyCard.setId(1L);
        keyCard.setSerialNumber("1234");
    }

    @Test
    void testGetAllKeyCards() {
        when(keyCardRepository.findAll()).thenReturn(Arrays.asList(keyCard));

        List<KeyCard> keyCards = keyCardService.getAllKeyCards();

        assertThat(keyCards).isNotEmpty();
        assertThat(keyCards.size()).isEqualTo(1);
        assertThat(keyCards.get(0).getId()).isEqualTo(keyCard.getId());
    }

    @Test
    void testGetKeyCardById() {
        when(keyCardRepository.findById(1L)).thenReturn(Optional.of(keyCard));

        Optional<KeyCard> result = keyCardService.getKeyCardById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(1L);
    }

    @Test
    void testSaveKeyCard() {
        when(keyCardRepository.save(keyCard)).thenReturn(keyCard);

        KeyCard savedKeyCard = keyCardService.saveKeyCard(keyCard);

        assertThat(savedKeyCard).isNotNull();
        assertThat(savedKeyCard.getId()).isEqualTo(keyCard.getId());
    }

    @Test
    void testDeleteKeyCard() {
        doNothing().when(keyCardRepository).deleteById(1L);

        keyCardService.deleteKeyCard(1L);

        verify(keyCardRepository, times(1)).deleteById(1L);
    }

    @Test
    void testSearchBySerialNumber() {
        when(keyCardRepository.findBySerialNumberContainingIgnoreCase("1234")).thenReturn(Arrays.asList(keyCard));

        List<KeyCard> result = keyCardService.searchBySerialNumber("1234");

        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getSerialNumber()).isEqualTo("1234");
    }
}
