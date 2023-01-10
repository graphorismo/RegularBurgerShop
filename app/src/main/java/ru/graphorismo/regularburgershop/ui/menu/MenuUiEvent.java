package ru.graphorismo.regularburgershop.ui.menu;

import ru.graphorismo.regularburgershop.data.Product;

public interface MenuUiEvent {

    class Refresh implements MenuUiEvent {}

    class AddProductToCart implements MenuUiEvent{
        private final Product product;

        public AddProductToCart(Product product) {
            this.product = product;
        }

        public Product getProduct() {
            return product;
        }
    }




}
