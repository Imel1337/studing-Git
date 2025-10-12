def useless(lst):
    return max(lst) / len(lst)

print(useless([1,2,3,4,5]))
print(useless([-12.5, 12, -45, 23, 123, -333]))
print(useless([-12222.2, 313131, 3333, -23.45]))