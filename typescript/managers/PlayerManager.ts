import Manager from "./Manager";
import Player, { APIPlayer } from "../classes/Player";
import { numbers as range1_100 } from "../utils";

/**
 * @class
 */
export default class m extends Manager<Player["id"], Player> {
  async fetch<T extends all | name | id>(
    type: T,
    options: T extends all
      ? Partial<{ force: boolean; page: number; per_page: range1_100 }>
      : T extends name
      ? { force?: boolean; page?: number; per_page?: range1_100; name: string }
      : T extends id
      ? { force?: boolean; id: number }
      : never
  ): Promise<(T extends id ? Player : Player[]) | never> {
    const force = options?.force;

    if (["all", 0].includes(type)) {
      const { data } = await this.request("players");

      console.log(data.length);

      for (const obj of data) {
        if (!obj || !("id" in obj)) continue;
        this.cache.set(obj.id, new Player(obj));
      }

      return data.map((data: APIPlayer) => new Player(data));
    }
    if (["id", 2].includes(type) && "id" in options) {
      const id = options.id;

      const playerInCache = this.cache.get(id);
      if (!force && playerInCache) {
        return playerInCache as T extends id ? Player : Player[];
      }

      const data: APIPlayer = await this.request("players/" + id.toString());

      this.cache.set(data.id, new Player(data));

      return new Player(data) as T extends id ? Player : Player[];
    }

    if (["name", 2].includes(type)) {
      // todo
      return new Player({} as APIPlayer) as T extends id ? Player : Player[]
    }

    throw new Error("Wrong type parameters")
  }
}

type all = "all" | 0;
type name = "name" | 1;
type id = "id" | 2;
