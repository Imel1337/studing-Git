def add_item(cart, item, price):
    """Добавляет товар в корзину"""
    cart.append((item, price))
    print(f"Добавлен: {item} - {price} руб")

def total_cost(cart):
    """Считает общую стоимость корзины"""
    total = sum(price for item, price in cart)
    return total


cart = []
print(f"Общая стоимость: {total_cost(cart)} руб")


add_item(cart, "Хлеб", 50)
add_item(cart, "Молоко", 80)
add_item(cart, "Сыр", 200)
print(f"Общая стоимость: {total_cost(cart)} руб")


add_item(cart, "Шоколад", 120)
add_item(cart, "Вода", 40)
print(f"Общая стоимость: {total_cost(cart)} руб")