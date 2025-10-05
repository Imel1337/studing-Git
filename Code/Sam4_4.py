def calculate_average(*args):
    """Функция считает среднее арифметическое от аргументов"""
    if len(args) == 0:
        return 0
    
    total = sum(args)
    average = total / len(args)
    return average

if __name__ == '__main__':
    result1 = calculate_average(1, 2, 3, 4, 5)
    print(f"Среднее: {result1}")
    
    result2 = calculate_average(10, 20, 30)
    print(f"Среднее: {result2}")