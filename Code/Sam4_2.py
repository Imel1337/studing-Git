import random

def dice_game():
    """Функция имитирует бросок игральной кости"""
    roll = random.randint(1, 6)
    print(f"Выпало: {roll}")
    
    if roll == 5 or roll == 6:
        print("Вы победили")
    elif roll == 3 or roll == 4:
        dice_game()
    else:
        print("Вы проиграли")

if __name__ == '__main__':
    dice_game()