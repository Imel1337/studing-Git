from Sam4_5_tri import triangle_heron

def main():
    print("Вычисление площади треугольника по формуле Герона")
    
    a = float(input("Введите первую сторону: "))
    b = float(input("Введите вторую сторону: "))
    c = float(input("Введите третью сторону: "))
    
    area = triangle_heron(a, b, c)
    print(f"Площадь треугольника: {area:.2f}")

if __name__ == '__main__':
    main()