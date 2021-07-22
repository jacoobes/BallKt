import Manager from "./Manager";
import fetch from "node-fetch";

export default class extends Manager {
  async fetch(id?: number, force?: boolean) {
    if (!id) {
      const { data } = await (await fetch(this.endpoint + "players")).json();

      for (const obj of data) {
          if (!obj || !("id" in obj)) continue;
          this.cache.set(obj.id, obj)
      }

      return data;
    }
    if (!force && this.cache.get(id)) return this.cache.get(id);

    const data = await (await fetch(this.endpoint + "players/" + id.toString())).json();

    this.cache.set(data.id, data);

    return data;
  }
}
