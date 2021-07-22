import Manager from "./Manager";

export default class extends Manager {
  async fetch(id?: number, force?: boolean) {
    if (!id) {
      const { data } = await this.request("players");

      for (const obj of data) {
          if (!obj || !("id" in obj)) continue;
          this.cache.set(obj.id, obj)
      }

      return data;
    }
    if (!force && this.cache.get(id)) return this.cache.get(id);

    const data = await this.request("players/" + id.toString())

    this.cache.set(data.id, data);

    return data;
  }
}
