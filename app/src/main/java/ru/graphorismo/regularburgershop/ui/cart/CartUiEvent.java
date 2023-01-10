package ru.graphorismo.regularburgershop.ui.cart;

import ru.graphorismo.regularburgershop.data.Product;

public interface CartUiEvent {

    class RemoveProductFromCart implements CartUiEvent{
        private final Product product;

        public RemoveProductFromCart(Product product) {
            this.product = product;
        }

        public Product getProduct() {
            return product;
        }
    }

    class ClearCart implements CartUiEvent {}
    class BuyCart implements CartUiEvent{}
}
