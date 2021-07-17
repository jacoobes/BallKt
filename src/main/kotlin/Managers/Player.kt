package dev.seren.Managers

import dev.seren.BallCache

import dev.seren.serializables.player.PlayerData



/**
 * Interacting with Player endpoint
 * */
class Player  : BallManager() {

    override val baseUrl: String = "${super.baseUrl}players"
    internal val cache = BallCache<String, PlayerData>()






}