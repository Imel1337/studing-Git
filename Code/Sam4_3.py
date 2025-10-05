import datetime
import time

def print_time_5_seconds():
    """Вывод текущего времени в течение 5 секунд"""
    for i in range(5):
        current_time = datetime.datetime.now()
        formatted_time = current_time.strftime("%H:%M:%S")
        print(f"Текущее время: {formatted_time}")
        time.sleep(1)

if __name__ == '__main__':
    print_time_5_seconds()