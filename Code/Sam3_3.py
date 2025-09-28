n = int(input())
if 0 <= n <= 10:
    if n <= 3:
        print("от 0 до 3 включительно")
    elif n <= 6:
        print("от 3 до 6")
    else:
        print("от 6 до 10 включительно")
else:
    print("Число не в диапазоне от 0 до 10")