def get_office_sequence(tuple_data, employee_id):
    if employee_id not in tuple_data:
        return ()
    
    first_index = tuple_data.index(employee_id)
    
    try:
        second_index = tuple_data.index(employee_id, first_index + 1)
        return tuple_data[first_index:second_index + 1]
    except ValueError:
        return tuple_data[first_index:]

def manual_input():

    print("\n" + "="*50)
    print("Ручной ввод данных")
    print("="*50)
    
    print("Введите ID сотрудников через пробел (числа):")
    try:
        user_input = input()
        user_tuple = tuple(map(int, user_input.split()))
        print(f"Ваш кортеж: {user_tuple}")
    except ValueError:
        print("Ошибка! Вводите только числа, разделенные пробелами.")
        return
    
    print("Введите ID сотрудника для поиска:")
    try:
        employee_id = int(input())
    except ValueError:
        print("Ошибка! Введите целое число.")
        return
    
    result = get_office_sequence(user_tuple, employee_id)
    
    print(f"\nРезультат анализа:")
    print(f"Исходная последовательность: {user_tuple}")
    print(f"ID сотрудника: {employee_id}")
    print(f"Количество вхождений ID: {user_tuple.count(employee_id)}")
    
    if employee_id in user_tuple:
        first_index = user_tuple.index(employee_id)
        print(f"Первое вхождение на позиции: {first_index}")
        
        try:
            second_index = user_tuple.index(employee_id, first_index + 1)
            print(f"Второе вхождение на позиции: {second_index}")
            print(f"Последовательность от {first_index} до {second_index} позиции")
        except ValueError:
            print("Второе вхождение не найдено")
            print(f"Последовательность от {first_index} позиции до конца")
    
    print(f"Итоговая последовательность: {result}")

manual_input()