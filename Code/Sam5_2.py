results = [10.2, 14.8, 19.3, 22.7, 12.5, 33.1, 38.9, 21.6, 26.4, 17.1, 
           30.2, 35.7, 16.9, 27.8, 24.5, 16.3, 18.7, 31.9, 12.9, 37.4]

best_results = sorted(results)[:3]
print(f"1. Три лучших результата: {best_results}")

worst_results = sorted(results, reverse=True)[:3]
print(f"2. Три худших результата: {worst_results}")

first_ten_results = results[:10]
print(f"3. Первые 10 результатов: {first_ten_results}")