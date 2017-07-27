val a = List(1,2,3,4,5)
val b = List(9,8,7,6,5,4)


a.flatMap(v => b)
b.flatMap(v => a)

