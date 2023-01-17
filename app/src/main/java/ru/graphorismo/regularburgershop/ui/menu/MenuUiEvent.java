package ru.graphorismo.regularburgershop.ui.menu;

import ru.graphorismo.regularburgershop.data.Product;
import ru.graphorismo.regularburgershop.ui.MainUiEvent;

public interface MenuUiEvent extends MainUiEvent {

    class AddProductToCart implements MenuUiEvent{
        private final Product product;

        public AddProductToCart(Product product) {
            this.product = product;
        }

        public Product getProduct() {
            return product;
        }
    }

    class ChangeOfShowedProductsTitle implements MenuUiEvent{
        private final String title;

        public ChangeOfShowedProductsTitle(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }




}
