from pprint import pprint

my_dict = {'first': 'so easy'}


def dict_marker(**kwargs):
    my_dict.update(**kwargs)
    

dict_marker(a1=1, a2=20, a3=54, a4=13)
dict_marker(name="Михаил", age=31, weight=70, eyes_color="blue")
pprint(my_dict)