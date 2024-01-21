
Une contribution de Desired
Approuvé par CodinGamer anonyme,AndriamanitraetDoraa
Objectif
Gareth was once a brilliant and respected blacksmith, his swords were famous all around the world.
But Gareth grew distracted over time and his work is not as perfect as it used to be.

Identify the number of defective swords forged by Gareth.

A sword has a width of 5 characters with a variable height, and is composed of a blade, a guard and a grip. All swords of a same testcase have the same size.
The ascii representation of the guard and the grip is always the same.
Only the blade can be defective, a default in the blade can be identified by < or > in the middle of the blade.
Entrée
Line 1 : An integer N for the size of the swords.
N next lines : a line row representing the swords slices.
Each swords are separated by 2 spaces
Sortie
The number of defective swords.
Contraintes
N > 4
Exemple
Entrée
9
^      ^      ^  
| |    | |    | |
| |    | |    | |
| |    |<     | |
| |    | |    | |
| |    | |    | |
=====  =====  =====
|      |      |  
|      |      |  
Sortie
1