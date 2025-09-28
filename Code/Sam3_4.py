sentence = input("Введите предложение на английском: ")

print("Длина предложения:", len(sentence))
print("В нижнем регистре:", sentence.lower())

kolvo = sum(1 for char in sentence.lower() if char in 'aeiou')
print("Количество гласных:", kolvo)

new_sentence = sentence.replace('ugly', 'beauty').replace('Ugly', 'Beauty')
print("После замены 'ugly' на 'beauty':", new_sentence)

starts_with_the = sentence.startswith('The')
ends_with_end = sentence.endswith('end')
print("Начинается с 'The':", starts_with_the)
print("Заканчивается на 'end':", ends_with_end)
print()