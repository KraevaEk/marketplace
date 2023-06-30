package ru.inno.market;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import ru.inno.market.core.MarketService;
import ru.inno.market.model.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MarketServiceTest {
    private MarketService service;
    private final static Client CLIENT = new Client(1, "Клиент");
    private final static Item ITEM = new Item(1, "Xiaomi POCO M5s", Category.SMARTPHONES, 19490);

    @BeforeEach
    public void setUp() {
        service = new MarketService();
    }

    @Test
    @DisplayName("Создание заказа")
    public void shouldCreateOrderFor() {
        int newId = service.createOrderFor(CLIENT);
        assertEquals(0, newId);
    }

    @Test
    @DisplayName("Добавление товара к заказу")
    public void shouldAddItemToOrder() {
        int newId = service.createOrderFor(CLIENT);
        service.addItemToOrder(ITEM,newId);
        service.addItemToOrder(ITEM,newId);
        assertEquals(38980, service.getOrderInfo(newId).getTotalPrice());
    }

    @DisplayName("Применение промокода")
    @ParameterizedTest(name = "{index} => Добавление промокода {0}")
    @EnumSource(PromoCodes.class)
    public void shouldApplyDiscountForOrder(PromoCodes codes) {
        int newId = service.createOrderFor(CLIENT);
        service.addItemToOrder(ITEM,newId);
        double sum = service.getOrderInfo(newId).getTotalPrice() * (1 - codes.getDiscount());
        service.applyDiscountForOrder(newId, codes);
        assertTrue(service.getOrderInfo(newId).isDiscountApplied());
        assertEquals(sum, service.getOrderInfo(newId).getTotalPrice());
    }

}
