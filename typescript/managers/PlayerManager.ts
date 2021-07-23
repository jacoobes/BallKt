import Manager from "./Manager";
import Player, { APIPlayer } from "../classes/Player";

/**
 * @class 
 */
export default class extends Manager<Player["id"], Player> {

  /**
   * 
   * @param id player id
   * @param force force a request
   * @returns Player
   */  
  
  async fetch(id?: number, options?: options): Promise<Player> {

    const force = options?.force

    if (!id) {
      const { data } = await this.request("players");

      console.log(data.length)

      for (const obj of data) {
          if (!obj || !("id" in obj)) continue;
          this.cache.set(obj.id, new Player(obj))
      }

      return data.map((data: APIPlayer) => new Player(data));
    }


    const playerInCache = this.cache.get(id)
    if (!force && playerInCache) {
        return playerInCache;
    }

    const data: APIPlayer = await this.request("players/" + id.toString())

    this.cache.set(data.id, new Player(data));

    return new Player(data);
  }
}


type options = {
    force?: boolean
}