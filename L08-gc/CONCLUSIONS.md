###### Введение

Была разработана программа для демонстарции работы разных GC: G1, Serial Collector, Parallel Collector, ZGC.
Программа выполняется до получения ошибки java.lang.OutOfMemoryError, которая достигается путем заполения списка.
Данные работы программы снимались для heap 512m и 1024m.

###### Результаты запусков

GC: G1

|                    |-Xmx512m   |-Xmx1024m|
|--------------------|-----------|---------|
| Time to OOM (min)  | 2.27      | 4.37    |
| Young GC (ms)      | 506       | 848     |
| Old GC (ms)        | 3475      | 3503    |
| Total GC Time (ms):| 3981      | 4351    |
| GC iterations      | 42        | 36      |
| Avg GC time (ms)   | 95        | 120     |


GC: Serial Collector

|                    |-Xmx512m   |-Xmx1024m|
|--------------------|-----------|---------|
| Time to OOM (min)  | 2.31      | 4.58    |
| Young GC (ms)      | 473       | 852     |
| Old GC (ms)        | 6203      | 9206    |
| Total GC Time (ms):| 6676      | 10058   |
| GC iterations      | 13        | 14      |
| Avg GC time (ms)   | 513       | 718     |


GC: Parallel Collector

|                    |-Xmx512m   |-Xmx1024m|
|--------------------|-----------|---------|
| Time to OOM (min)  | 2.19      | 4.36    |
| Young GC (ms)      | 191       | 323     |
| Old GC (ms)        | 2237      | 5789    |
| Total GC Time (ms):| 2428      | 6112    |
| GC iterations      | 12        | 15      |
| Avg GC time (ms)   | 202       | 407     |


GC: ZGC

|                    |-Xmx512m   |-Xmx1024m|
|--------------------|-----------|---------|
| Time to OOM (min)  | 1.55      | 3.51    |
| Young GC (ms)      | 0         | 0       |
| Total GC Time (ms):| 12142     | 25816   |
| GC iterations      | 24        | 26      |
| Avg GC time (ms)   | 505       | 992     |


###### Выводы

1 Если в качестве эффективности работы GC выбрать эффективную продолжительность работы программы (Time to OOM - Total GC Time), 
то сданной задачей лучше всего справились G1 и Serial Collector. При этом у G1 STW остановки значительно короче, 
и в целом на сборку мусора уходит меньше времени при схожем с Serial Collector результате.

2 Было показано, что изменение размера heap линейно влияет на продолжительность работы программы до OOM. 
При этом для G1 общее время сборки мусора увеличилось незначительно.

3 Для всех GC (кроме ZGC) продолжительность сборки Young Generation объектов значительно быстрее сборки Old Generation, которые начинаются, 
когда ресурсы heap заканчиваются.










