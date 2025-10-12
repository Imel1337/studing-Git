def fix_grades(grades):
    fixed_grades = []
    for grade in grades:
        if grade == 2:
            continue
        elif grade == 3:
            fixed_grades.append(4)
        else:
            fixed_grades.append(grade)
    return fixed_grades

grades_1 = [2, 3, 4, 5, 3, 4, 5, 2, 2, 5, 3, 4, 3, 5, 4]
grades_2 = [4, 2, 3, 5, 3, 5, 4, 2, 2, 5, 4, 3, 5, 3, 4]
grades_3 = [5, 4, 3, 3, 4, 3, 3, 5, 5, 3, 3, 3, 3, 4, 4]

fixed_1 = fix_grades(grades_1)
fixed_2 = fix_grades(grades_2)
fixed_3 = fix_grades(grades_3)

print("Исправленные оценки:")
print(f"Первый список: {grades_1} -> {fixed_1}")
print(f"Второй список: {grades_2} -> {fixed_2}")
print(f"Третий список: {grades_3} -> {fixed_3}")