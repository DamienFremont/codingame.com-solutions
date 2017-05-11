# codingame-multiplayer-bot_programming-ghost_in_the_cell
1,532 CodinGamers in the Ghost in the Cell arena

https://www.codingame.com/multiplayer/bot-programming/ghost-in-the-cell

![alt text](screenshots/ScreenShot001.png)

## The Goal

Your objective is to produce a maximum amount of cyborgs in order to destroy those of your opponent. To this end, you must take ownership of factories that will enable you to increase the size of your cyborg army.

## Rules

The game is played with 2 players on a board on which a variable number of factories are placed (from 7 to 15 factories). Initially, each player holds a single factory in which there is a stock of 15 to 30 cyborgs. The other factories are neutral but also have cyborgs defending them.

On each turn, a player can send any number of cyborgs from one factory to another. The cyborgs in transit form a troop. This troop will take between 1 and 20 turns to reach its destination. When the troop arrives, the cyborgs will fight with any opponent cyborgs present at the factory.

### Factory placement

Factories are placed randomly across the map at the start of each game. The player is given the distance between each factory, expressed as the number of turns it takes to reach a factory starting from another.

### Game Turn

One game turn is computed as follows:
- Move existing troops
- Execute user orders
- Produce new cyborgs in all factories
- Solve battles
- Check end conditions
 

### Cyborg Production

Each turn, every non-neutral factory produces between 0 and 3 cyborgs.

![alt text](screenshots/ScreenShot002.png)
 

### Battles

To conquer a factory, you must send cyborgs to the coveted factory. Battles are played in this order:

- Cyborgs that reach the same destination on the same turn fight between themselves.
- Remaining cyborgs fight against the ones already present in the factory (beware that the cyborgs currently leaving do not fight).

If the number of attacking cyborgs is greater than the number of cyborgs in defense, the factory will then belong to the attacking player and it will start producing new cyborgs for this player on the next turn.

![alt text](screenshots/ScreenShot003.png)

 
## Victory Conditions
- Your opponent has no cyborgs left, nor any factories capable of producing new cyborgs.
- You have more cyborgs than your opponent after 200 turns.

## Game Input

### Initialization input
- Line 1:factoryCount, the number of factories.
- Line 2:linkCount, the number of links between factories.
- Next linkCount lines: 3 space-separated integers factory1, factory2 and distance, where distance is the number of turns needed for a troop to travel between factory1 and factory2.

### Input for one game turn
- Line 1: an integer entityCount, the number of entities.
- Next entityCount lines: an integer entityId, a string entityType and 5 integers arg1, arg2, arg3, arg4 and arg5.

If entityType equals FACTORY then the arguments are:
- arg1: player that owns the factory: 1 for you, -1 for your opponent and 0 if neutral
- arg2: number of cyborgs in the factory
- arg3: factory production (between 0 and 3)
- arg4: unused
- arg5: unused

If entityType equals TROOP then the arguments are:
- arg1: player that owns the troop: 1 for you or -1 for your opponent
- arg2: identifier of the factory from where the troop leaves
- arg3: identifier of the factory targeted by the troop
- arg4: number of cyborgs in the troop (positive integer)
- arg5: remaining number of turns before the troop arrives (positive integer)

### Output for one game turn
The available actions are:
- MOVE source destination cyborgCount: creates a troop of cyborgCount cyborgs at the factory source and sends that troop towards destination. Example: MOVE 2 4 12 will send 12 cyborgs from factory 2 to factory 4.
- WAIT: does nothing.

If you try to move more cyborgs that there are in the source factory, then all the available units will be sent.

### Constraints
- 7 ≤ factoryCount ≤ 15
- 21 ≤ linkCount ≤ 105
- 1 ≤ distance ≤ 20
- Response time for first turn ≤ 1000ms
- Response time for one turn ≤ 50ms
