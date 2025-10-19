user_input = input("Введите числа через пробел: ")

numbers_list = [int(x) for x in user_input.split()]

numbers_tuple = tuple(numbers_list)

print(numbers_list)
print(numbers_tuple)