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