def remove_first_occurrence(tuple_data, element):
    temp_list = list(tuple_data)
    if element in temp_list:
        temp_list.remove(element)
    return tuple(temp_list)

print("Введите элементы кортежа через пробел (числа):")
user_input = input()

try:
    user_tuple = tuple(map(int, user_input.split()))
    print(f"Ваш кортеж: {user_tuple}")
except ValueError:
    print("Ошибка! Вводите только числа, разделенные пробелами.")
    exit()

print("Введите число, которое нужно удалить (первое вхождение):")
try:
    number_to_remove = int(input())
except ValueError:
    print("Ошибка! Введите целое число.")
    exit()

result = remove_first_occurrence(user_tuple, number_to_remove)
print(f"\nИсходный кортеж: {user_tuple}")
print(f"Число для удаления: {number_to_remove}")
print(f"Результат: {result}")