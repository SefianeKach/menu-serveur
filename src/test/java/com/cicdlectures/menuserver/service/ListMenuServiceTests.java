package com.cicdlectures.menuserver.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import com.cicdlectures.menuserver.dto.MenuDto;
import com.cicdlectures.menuserver.dto.DishDto;
import com.cicdlectures.menuserver.model.Dish;
import com.cicdlectures.menuserver.model.Menu;
import com.cicdlectures.menuserver.repository.MenuRepository;
import com.cicdlectures.menuserver.service.ListMenuService;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class ListMenuServiceTests {

    private MenuRepository menuRepository;

    private ListMenuService subject;

    @BeforeEach
    public void init() {
        this.menuRepository = mock(MenuRepository.class);
        this.subject = new ListMenuService(this.menuRepository);
    }
  
    @Test
    @DisplayName("lists all known menus")
    public void listsKnownMenus() {
        // Défini une liste de menus avec un menus.
        Iterable<Menu> existingMenus = Arrays.asList(
            new Menu(
            Long.valueOf(1),
            "Christmas menu",
            new HashSet<>(
                Arrays.asList(
                new Dish(Long.valueOf(1), "Turkey", null),
                new Dish(Long.valueOf(2), "Pecan Pie", null)
                )
            )
        )
    );
    
    // configure le mock pour qu'il retourne une instance de menu
    when(menuRepository.save(any(Menu.class))).thenReturn(storedMenu);

    // On appelle notre sujet
    List<MenuDto> gotMenus = subject.listMenus();

    // On défini wantMenus, les résultats attendus
    Iterable<MenuDto> wantMenus = Arrays.asList(
        new MenuDto(
            Long.valueOf(1),
            "Christmas menu",
            new HashSet<>(
            Arrays.asList(
                new DishDto(Long.valueOf(1), "Turkey"),
                new DishDto(Long.valueOf(2), "Pecan Pie")
            )
            )
        )
        );

        // On compare la valeur obtenue avec la valeur attendue.
        assertEquals(wantMenus, gotMenus);
    }

    
    
    // On déclare un ArgumentCaptor<Menu> (qui sert a capturer un argument)
    ArgumentCaptor<Menu> savedMenuCaptor = ArgumentCaptor.forClass(Menu.class);

    // On vérifie que la méthode `save` du menu repository à été appelée une seule fois
    // et on capture l'argument avec lequel elle a été appelée (le menu).
    verify(menuRepository, times(1)).save(savedMenuCaptor.capture());

    // On récupère la valeur capturée pour pouvoir faire des assertions dessus.
    savedMenu = savedMenuCaptor.getValue()

}
