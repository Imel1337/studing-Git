def count_top_three_digits(digit_string):
    digit_count = {}
    
    for char in digit_string:
        if char.isdigit():
            digit = int(char)
            digit_count[digit] = digit_count.get(digit, 0) + 1
    
    sorted_digits = sorted(digit_count.items(), key=lambda x: (-x[1], x[0]))
    
    top_three = dict(sorted_digits[:3])
    
    result = dict(sorted(top_three.items()))
    
    return result

print("Введите последовательность цифр (минимум 15 символов):")
user_input = input().strip()

if len(user_input) < 15:
    print("Ошибка! Строка должна содержать минимум 15 символов.")
else:
    result_dict = count_top_three_digits(user_input)
    
    print(f"\nВведенная строка: {user_input}")
    print("Топ-3 самых часто встречаемых чисел:")
    
    for digit, count in sorted(result_dict.items()):
        print(f"Цифра {digit}: {count} раз(а)")
    
    print(f"\nСловарь: {result_dict}")