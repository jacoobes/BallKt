import Manager from "./Manager";
import Player, { APIPlayer } from "../classes/Player";
import { PlayerManagerTypes, numbers as range1_100, NoTypes } from "../utils";

/**
 * @class
 */
export default class m extends Manager<Player["id"], Player> {
  async fetch<T extends all | name | id>(
    type: T,
    options: T extends all
      ? { page?: number; amount?: range1_100 }
      : T extends name
      ? { page?: number; amount?: range1_100; name: string }
      : T extends id
      ? { force?: boolean; id: number }
      : never
  ): Promise<(T extends id ? Player : Player[]) | never> {


    if (["all", 0, "every"].includes(type)) {
      
      let allArr: { name: string, value: string }[] = []

      let newOptions = options as Partial<{ page: number; amount: range1_100 }>

      if (newOptions.page) allArr.push({
        name: "page",
        value: newOptions.page.toString()
      })

      if (newOptions.amount) allArr.push({
        name: "per_page",
        value: newOptions.amount.toString()
      })

      const { data } = await this.request(NoTypes.query("players", allArr));

      for (const obj of data) {
        if (!obj || !("id" in obj)) continue;
        this.cache.set(obj.id, new Player(obj));
      }

      return data.map((data: APIPlayer) => new Player(data));
    }


    if (["id", 2].includes(type) && "id" in options) {
      const id = options.id;

      const playerInCache = this.cache.get(id);

      const force = options.force
      if (!force && playerInCache) {
        return playerInCache as T extends id ? Player : Player[];
      }

      const data: APIPlayer = await this.request("players/" + id.toString());

      this.cache.set(data.id, new Player(data));

      return new Player(data) as T extends id ? Player : Player[];
    }

    if (["name", 1].includes(type) && "name" in options) {

      let allArr: { name: string, value: string }[] = []

      allArr.push({
        name: "search",
        value: options.name
      })
      
      if (options.page) allArr.push({
        name: "page",
        value: options.page.toString()
      })

      if (options.amount) allArr.push({
        name: "per_page",
        value: options.amount.toString()
      })

      const { data } = await this.request(NoTypes.query("players", allArr));

      for (const obj of data) {
        if (!obj || !("id" in obj)) continue;
        this.cache.set(obj.id, new Player(obj));
      }

      return data.map((data: APIPlayer) => new Player(data));

    }

    throw new Error("Wrong type parameters")
  }
}

type all = PlayerManagerTypes.every
type name = PlayerManagerTypes.name
type id = PlayerManagerTypes.id