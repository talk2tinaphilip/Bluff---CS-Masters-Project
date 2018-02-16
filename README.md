# Bluff---CS-Masters-Project
Bluff with AI:
The goal of this project is to build multiple agents for the game Bluff and to conduct experiments
as to which performs better. Bluff is a multi-player, non-deterministic card game where players
try to get rid of all the cards in their hand. The process of bluffing involves making a move such
that it misleads the opponent and thus prove to be of advantage to the player. The strategic
complexity in the game arises due to the imperfect or hidden information which means that
certain relevant details about the game are unknown to the players. Multiple agents followed
different strategies to compete against each other. Two of the agents tried to play the game in
offense mode where they tried to win by removing the cards from the hand efficiently and two
other agents in defense mode where they try to prevent or delay other players from winning by
calling Bluff on them when they have few cards left.
In the experiments that we conducted with all four agents competing against each other,
we found that the best strategy was to not Bluff and play truthfully. Playing the right cards, gave
the most wins to any player. Also we found out that calling Bluff on a player even if we have
more than one card of the same rank would prove risky, since there is a chance that the player
was actually playing the correct cards and we could lose the bet as shown by the Anxious AI.
We conducted an interesting experiment to find out the best defense strategy and which agent
would catch the most number of bluffs correctly. The Anxious AI was the winner. We also try to
“teach” an agent how to play the game effectively and experiments show that the agent did learn
the strategy very well. We also found that the Smart AI was the evolutionary stable strategy
among the four agents.
