package dev.seren.Managers

import dev.seren.BallCache
import dev.seren.serializables.game.GameData

/**
 * Interacting with Game endpoint
 * */
class Game : BallManager() {

    internal val cache = BallCache<Int, GameData>(60)
}