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