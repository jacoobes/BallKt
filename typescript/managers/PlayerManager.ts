import Manager from "./Manager"

export default class extends Manager {
    async fetch(id: string, force?: boolean) {
        if (force || !this.cache.get(id)) return 
    }
}