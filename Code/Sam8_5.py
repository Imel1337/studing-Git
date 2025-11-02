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