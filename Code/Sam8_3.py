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