# The Bots

## Frankenplayer
A combination of the other three bots code. It uses the BuyMonsterMove from New_player, the steal monster move from PointsPlayer, and the castle placement strategy from Tony

## New_Player

## PointsPlayer
AI which looks for the highest scored monsters it can buy and then places the monsters evenly across the three castles. The castle-placing strategy is a little broken and is not the smartest, but the buy and steal strategies are decent.

## TonyMakesBadChoices
AI that tries to end the game as quickly as possible without giving the other player time to build up defenses. It places 4 monsters at a random castle where it doesn't have a hidden dragon. After that battle, it starts filling up its hidden dragon castle. It buys random monsters and only steals if the opponent offers a monster at a price less than or equal to its value.
