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