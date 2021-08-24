package dev.seren.Managers

import dev.seren.BallCache
import dev.seren.serializables.game.GameData
import dev.seren.serializables.team.TeamData

/**
 * Interacting with Game endpoint
 * */
class Game : BallManager() {

    internal val cache = BallCache<Int, GameData>(60)

}