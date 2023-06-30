package ru.inno.market;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.inno.market.model.Category;
import ru.inno.market.model.Client;
import ru.inno.market.model.Item;
import ru.inno.market.model.Order;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OrderTest {
    private final static Client CLIENT = new Client(1, "Клиент");
    private final static Item ITEM = new Item(1, "Xiaomi POCO M5s", Category.SMARTPHONES, 19490);

    @Test
    @DisplayName("Создание заказа с валидными данными")
    public void shouldCreateOrder () {
        Order order = new Order(1, CLIENT);
        assertEquals(1, order.getId());
    }

    @Test
    @DisplayName("Добавление товара к заказу")
    public void shouldAddItemPositive() {
        Order order = new Order(1, CLIENT);
        order.addItem(ITEM);
        order.addItem(ITEM);
        assertEquals(38980, order.getTotalPrice());
    }

    @Test
    @DisplayName("Применение скидки")
    public void shouldApplyDiscount() {
        Order order = new Order(1, CLIENT);
        order.addItem(ITEM);
        order.applyDiscount(0.5);
        assertTrue(order.isDiscountApplied());
        assertEquals(9745, order.getTotalPrice());
    }


    @DisplayName("Применение скидки. Негативные тесты")
    @ParameterizedTest(name = "{index} => Применение скидки {0}")
    @ValueSource(ints = {-10, -1, 0, 1, 10})
    public void shouldApplyDiscountNegative(int discount) {
        Order order = new Order(1, CLIENT);
        order.addItem(ITEM);
        assertThrows(IllegalArgumentException.class, () -> order.applyDiscount(discount));
    }



}
