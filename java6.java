Лістинг коду: Адаптер Служба доставки їжі.
interface BurgerSellingService {
    void sellBurger(double price);
}
class DollarBurgerSeller {
    void sellBurgerInDollars(double dollarPrice) {
        System.out.println("Burger sold in dollars for $" + dollarPrice);
    }
}
class DollarBurgerAdapter implements BurgerSellingService {
    private DollarBurgerSeller seller;

    public DollarBurgerAdapter(DollarBurgerSeller seller) {
        this.seller = seller;
    }

    @Override
    public void sellBurger(double price) {
        // Перетворення ціни з гривень  1 долар = 36 гривень
        double dollarPrice = price / 36.0;
        seller.sellBurgerInDollars(dollarPrice);
    }
}
class UahBurgerSeller {
    void sellBurgerInUah(double uahPrice) {
        System.out.println("Burger sold in UAH for " + uahPrice + " UAH");
    }
}

public class Main {
    public static void main(String[] args) {

        DollarBurgerSeller dollarSeller = new DollarBurgerSeller();

        BurgerSellingService dollarAdapter = new DollarBurgerAdapter(dollarSeller);

        dollarAdapter.sellBurger(70); // Вказуємо ціну в гривнях


        UahBurgerSeller uahSeller = new UahBurgerSeller();

        uahSeller.sellBurgerInUah(70);
    }
}
	Лістинг коду: 2 Спостерігач Служба доставки їжі.
import java.util.ArrayList;
import java.util.List;


interface Observer {
    void update(String message);
}

// Суб'єкт  
class FoodDeliveryService {
    private List<Observer> observers = new ArrayList<>();

    public void attach(Observer observer) {
        observers.add(observer);
    }

    public void detach(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }

    // Метод для відправки повідомлення про статус доставки
    public void deliveryStatusChanged(String status) {
        notifyObservers("Статус доставки: " + status);
    }
}

// Клас для клієнта, який слідкує за статусом доставки
class Customer implements Observer {
    private String name;

    public Customer(String name) {
        this.name = name;
    }

    @Override
    public void update(String message) {
        System.out.println(name + ": " + message);
    }
}


public class Main {
    public static void main(String[] args) {

        FoodDeliveryService deliveryService = new FoodDeliveryService();

        Customer customer1 = new Customer("Клієнт 1");
        Customer customer2 = new Customer("Клієнт 2");


        deliveryService.attach(customer1);
        deliveryService.attach(customer2);

        // Змінюємо статус доставки і спостерігаємо за реакцією клієнтів
        deliveryService.deliveryStatusChanged("В дорозі");
        deliveryService.deliveryStatusChanged("Доставлено");
    }
}

Лістинг коду: 3 Стан служба доставки їжі.

interface DeliveryState {
    void updateState();
}


class WaitingState implements DeliveryState {
    @Override
    public void updateState() {
        System.out.println("Замовлення очікує на обробку");
    }
}

class InTransitState implements DeliveryState {
    @Override
    public void updateState() {
        System.out.println("Замовлення в процесі доставки");
    }
}

class DeliveredState implements DeliveryState {
    @Override
    public void updateState() {
        System.out.println("Замовлення доставлено");
    }
}


class FoodDeliveryService {
    private DeliveryState currentState;

    public FoodDeliveryService() {
        this.currentState = new WaitingState(); // Початковий стан - "Очікування"
    }

    public void setState(DeliveryState state) {
        this.currentState = state;
    }

    public void updateDeliveryState() {
        currentState.updateState();
    }
}

public class Main {
    public static void main(String[] args) {
        FoodDeliveryService deliveryService = new FoodDeliveryService();

        deliveryService.updateDeliveryState();


        deliveryService.setState(new InTransitState());
        deliveryService.updateDeliveryState();

    
        deliveryService.setState(new DeliveredState());
        deliveryService.updateDeliveryState();
    }
}

