string = 'hello'
counter = 0
values = [0, 2, 4, 6, 8, 10]
memory = 'world'

while counter != 10:
    if counter in values:
        memory = string
    if counter > 7:
        print(string + ' ' + memory)
    counter += 1
    if counter < 10:
        string = 'hello'