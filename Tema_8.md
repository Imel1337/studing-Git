# Тема 8. Основы объектно-ориентированного программирования
Отчет по Теме 8 выполнил:

- Мельников Иван Александрович 
- ИВТ-23-2

| Задание | Лаб_раб | Сам_раб |
|---|---|---|
| Задание 1 | + | + |
| Задание 2 | + | + |
| Задание 3 | + | + |
| Задание 4 | + | + |
| Задание 5 | + | + |


## Лабораторная работа №1.  
### Создайте класс “Car” с атрибутами производитель и модель. Создайте объект этого класса. Напишите комментарии для кода, объясняющие его работу. Результатом выполнения задания будет листинг кода с комментариями.

```python
class Car:
    def __init__(self, make, model):
        self.make = make
        self.model = model

my_car = Car("Toyota", "Corolla")
```

### Результат.
![Меню](https://github.com/Imel1337/studing-Git/blob/Tema_8/pic/Lab8_1.png)

## Лабораторная работа №2. 
### Дополните код из первого задания, добавив в него атрибуты и методы класса, заставьте машину “поехать”. Напишите комментарии для кода, объясняющие его работу. Результатом выполнения задания будет листинг кода с комментариями и получившийся вывод в консоль.

```python
class Car:
    def __init__(self, make, model) :
        self.make = make
        self.model = model
        
    def drive(self):
        print(f"Driving the {self.make} {self.model}")


my_car = Car ("Toyota", "Corolla")
my_car.drive()
```

### Результат.
![Меню](https://github.com/Imel1337/studing-Git/blob/Tema_8/pic/Lab8_2.png)

## Лабораторная работа №3. 
### Создайте новый класс “ElectricCar” с методом “charge” и атрибутом емкость батареи. Реализуйте его наследование от класса, созданного в первом задании. Заставьте машину поехать, а потом заряжаться. Михаил А. Панов Напишите комментарии для кода, объясняющие его работу. Результатом выполнения задания будет листинг кода с комментариями и получившийся вывод в консоль.

```python
class Car:
    def __init__(self, make, model):
        self.make = make
        self.model = model
        
    def drive(self):
        print(f"Driving the {self.make} {self.model}")

class ElectricCar(Car):
    def __init__(self, make, model, battery_capacity):
        super().__init__(make, model)
        self.battery_capacity = battery_capacity
    
    def charge(self):
        print(f"Charging the {self.make} {self.model} with {self.battery_capacity} kWh")


my_electric_car = ElectricCar("Tesla", "Model S", 75)
my_electric_car.drive()
my_electric_car.charge()
```

### Результат.
![Меню](https://github.com/Imel1337/studing-Git/blob/Tema_8/pic/Lab8_3.png)

## Лабораторная работа №4. 
### Реализуйте инкапсуляцию для класса, созданного в первом задании. Создайте защищенный атрибут производителя и приватный атрибут модели. Вызовите защищенный атрибут и заставьте машину поехать. Напишите комментарии для кода, объясняющие его работу. Результатом выполнения задания будет листинг кода с комментариями и получившийся вывод в консоль.

```python
class Car:
    def __init__(self, make, model):
        self._make = make
        self.__model = model

    def drive(self):
        print(f"Driving the {self._make} {self.__model}")


my_car = Car ("Toyota", "Corolla")
print(my_car._make)
my_car.drive()
```

### Результат.
![Меню](https://github.com/Imel1337/studing-Git/blob/Tema_8/pic/Lab8_4.png)

## Лабораторная работа №5. 
### Реализуйте полиморфизм создав основной (общий) класс “Shape”, а также еще два класса “Rectangle” и “Circle”. Внутри последних двух классов реализуйте методы для подсчета площади фигуры. После этого создайте массив с фигурами, поместите туда круг и прямоугольник, затем при помощи цикла выведите их площади. Напишите комментарии для кода, объясняющие его работу. Результатом выполнения задания будет листинг кода с комментариями и получившийся вывод в консоль.

```python
class Robot:
    def __init__(self, name):
        self.name = name
    
    def action(self):
        return f"🤖 {self.name} выполняет базовые действия"

class CleanerRobot(Robot):
    def action(self):
        return f"🧹 {self.name} пылесосит пол"

class GuardRobot(Robot):
    def action(self):
        return f"🚨 {self.name} охраняет объект"


robots = [
    Robot("Базовый"),
    CleanerRobot("Чистильщик"),
    GuardRobot("Охранник")
]

for robot in robots:
    print(robot.action())
```

### Результат.
![Меню](https://github.com/Imel1337/studing-Git/blob/Tema_8/pic/Lab8_5.png)


## Самостоятельная работа №1. 
### Самостоятельно создайте класс и его объект. Они должны отличаться, от тех, что указаны в теоретическом материале (методичке) и лабораторных заданиях. Результатом выполнения задания будет листинг кода и получившийся вывод консоли.

```python
class Robot:
    def __init__(self, name, version):
        self.name = name
        self.version = version
        self.is_active = False
    
    def activate(self):
        self.is_active = True
        print(f"{self.name} v{self.version} активирован")
    
    def speak(self, message):
        if self.is_active:
            print(f"{self.name}: {message}")
        else:
            print("❌ Робот не активирован")


bot = Robot("Ассистент", "2.1")
bot.activate()
bot.speak("Привет! Я готов к работе")
```

### Результат.
![Меню](https://github.com/Imel1337/studing-Git/blob/Tema_8/pic/Sam8_1.png)

Вывод: узнал как создавать класс и его объекты.

## Самостоятельная работа №2. 
### Самостоятельно создайте атрибуты и методы для ранее созданного класса. Они должны отличаться, от тех, что указаны в теоретическом материале (методичке) и лабораторных заданиях. Результатом выполнения задания будет листинг кода и получившийся вывод консоли.

```python
class Robot:
    def __init__(self, name, energy=100):
        self.name = name
        self.energy = energy
    
    def work(self):
        self.energy -= 10
        print(f"⚡ {self.name} поработал. Энергия: {self.energy}%")
    
    def recharge(self):
        self.energy = 100
        print(f"🔋 {self.name} заряжен!")
    
    def status(self):
        print(f"🤖 {self.name} | Энергия: {self.energy}%")


bot = Robot("Валли")
bot.status()
bot.work()
bot.recharge()
bot.status()
```

### Результат.
![Меню](https://github.com/Imel1337/studing-Git/blob/Tema_8/pic/Sam8_2.png)

Вывод: научился создавать атрибуты и методы.

## Самостоятельная работа №3. 
### Самостоятельно реализуйте наследование, продолжая работать с ранее созданным классом. Оно должно отличаться, от того, что указано в теоретическом материале (методичке) и лабораторных заданиях. Результатом выполнения задания будет листинг кода и получившийся вывод консоли.

```python
class Robot:
    def __init__(self, name, energy=100):
        self.name = name
        self.energy = energy
    
    def work(self):
        self.energy -= 10
        print(f"⚡ {self.name} поработал. Энергия: {self.energy}%")
    
    def recharge(self):
        self.energy = 100
        print(f"🔋 {self.name} заряжен!")
    
    def status(self):
        print(f"🤖 {self.name} | Энергия: {self.energy}%")


class BattleRobot(Robot):
    def __init__(self, name, weapon, energy=100):
        super().__init__(name, energy)
        self.weapon = weapon
        self.ammo = 50
    
    def attack(self):
        if self.ammo > 0:
            self.ammo -= 5
            self.energy -= 15
            print(f"💥 {self.name} атаковал из {self.weapon}! Патроны: {self.ammo}")
        else:
            print(f"❌ У {self.name} закончились патроны!")
    
    def reload(self):
        self.ammo = 50
        print(f"🎯 {self.name} перезаряжен! Патроны: {self.ammo}")


soldier = BattleRobot("Терминатор", "лазерная пушка")

soldier.status()
soldier.attack()
soldier.work()
soldier.attack()
soldier.reload()
soldier.status()
```

### Результат.
![Меню](https://github.com/Imel1337/studing-Git/blob/Tema_8/pic/Sam8_3.png)

Вывод: понял как реализовывать наследование.

## Самостоятельная работа №4. 
### Самостоятельно реализуйте инкапсуляцию, продолжая работать с ранее созданным классом. Она должна отличаться, от того, что указана в теоретическом материале (методичке) и лабораторных заданиях. Результатом выполнения задания будет листинг кода и получившийся вывод консоли.

```python
class Robot:
    def __init__(self, name):
        self.name = name
        self.__battery = 100
    
    def work(self):
        self.__battery -= 20
        print(f"⚡ {self.name} | Батарея: {self.__battery}%")
    
    def get_battery(self):
        return self.__battery


bot = Robot("R2-D2")
bot.work()
print(f"🔋 Уровень: {bot.get_battery()}%")
```

### Результат.
![Меню](https://github.com/Imel1337/studing-Git/blob/Tema_8/pic/Sam8_4.png)

Вывод: узнал как реализовывать инкапсуляцию.

## Самостоятельная работа №5. 
### Самостоятельно реализуйте полиморфизм. Он должен отличаться, от того, что указан в теоретическом материале (методичке) и лабораторных заданиях. Результатом выполнения задания будет листинг кода и получившийся вывод консоли.

```python
class Robot:
    def __init__(self, name):
        self.name = name
    
    def action(self):
        return f"🤖 {self.name} выполняет базовые действия"

class CleanerRobot(Robot):
    def action(self):
        return f"🧹 {self.name} пылесосит пол"

class GuardRobot(Robot):
    def action(self):
        return f"🚨 {self.name} охраняет объект"


robots = [
    Robot("Базовый"),
    CleanerRobot("Чистильщик"),
    GuardRobot("Охранник")
]

for robot in robots:
    print(robot.action())
```

### Результат.
![Меню](https://github.com/Imel1337/studing-Git/blob/Tema_8/pic/Sam8_5.png)

Вывод: закрепил базовые знания о работе с полиморфизмом.

# Вывод 
Освоил основные работы с классами, наследованиями и базовыми операциями с ними, решил задачи где они используются.