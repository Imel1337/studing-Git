import math

one = [12, 25, 3, 48, 71]
two = [5, 18, 40, 62, 98]
three = [4, 21, 37, 56, 84]

def triangle_area(a, b, c):
    if a + b > c and a + c > b and b + c > a:
        p = (a + b + c) / 2
        return math.sqrt(p * (p - a) * (p - b) * (p - c))
    else:
        return None

all_elements = one + two + three
min_elements = sorted(all_elements)[:3]
max_elements = sorted(all_elements, reverse=True)[:3]

print(f"Минимальные элементы: {min_elements}")
print(f"Максимальные элементы: {max_elements}")

area_min = triangle_area(min_elements[0], min_elements[1], min_elements[2])
area_max = triangle_area(max_elements[0], max_elements[1], max_elements[2])

print(f"Площадь треугольника из минимальных элементов: {area_min:.2f}")
print(f"Площадь треугольника из максимальных элементов: {area_max:.2f}")