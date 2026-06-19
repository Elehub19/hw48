package org.skypro.skyshop.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.model.search.Searchable;
import org.skypro.skyshop.service.SearchService;
import org.skypro.skyshop.service.StorageService;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SearchServiceTest {

    @Mock
    private StorageService storageService;

    @InjectMocks
    private SearchService searchService;

    @Test
    void emptyStorage_returnsEmpty() {
        when(storageService.getAllSearchables()).thenReturn(Collections.emptyList());
        assertEquals(0, searchService.search("test").size());
    }

    @Test
    void noMatch_returnsEmpty() {
        Searchable s = new TestSearchable("banana");
        when(storageService.getAllSearchables()).thenReturn(List.of(s));
        assertEquals(0, searchService.search("apple").size());
    }

    @Test
    void match_returnsResult() {
        Searchable s = new TestSearchable("apple");
        when(storageService.getAllSearchables()).thenReturn(List.of(s));
        assertEquals(1, searchService.search("apple").size());
    }

    static class TestSearchable implements Searchable {
        private final String term;

        TestSearchable(String term) {
            this.term = term;
        }

        @Override
        public UUID getId() {
            return UUID.randomUUID();
        }

        @Override
        public String getSearchTerm() {
            return term;
        }

        @Override
        public String getName() {
            return term;
        }

        @Override
        public String getContentType() {
            return "TEST";
        }
    }
}