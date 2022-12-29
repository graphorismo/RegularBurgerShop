package ru.graphorismo.regularburgershop.ui.menu;

import ru.graphorismo.regularburgershop.data.Menu;

public interface MenuUiEvent {

    class Load implements MenuUiEvent {

        private final Menu menu;

        Load(Menu menu){this.menu = menu;}

        Menu getLoadedMenu() {return menu;}
    }


}
