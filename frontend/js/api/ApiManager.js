class ApiManager {
    #apiprefix = "http://localhost:8080/api/";

    constructor() {}

    async login (formdata = {}) {
        const response = await fetch(this.#apiprefix.concat("auth/login"), {
            method: "POST",
            mode: "cors",
            headers: {
                "Content-type": "application/json"
            },
            body: JSON.stringify(formdata)
        });

        return response.json();
    }

    async register(formdata = {}) {
        const response = await fetch(this.#apiprefix.concat("auth/register"), {
            method: "POST",
            mode: "cors",
            headers: {
                "Content-type": "application/json"
            },
            body: JSON.stringify(formdata)
        });

        return response.json();
    }

    async insertPet(auth, formdata = {}) {
        const response = await fetch(this.#apiprefix.concat("pets/insert"), {
            method: "POST",
            mode: "cors",
            headers: {
                "Content-type": "application/json",
                "Authorization": auth
            },
            body: JSON.stringify(formdata)
        });

        return response.json();
    }

    async listRegisteredPets(auth, limit = 10) {
        const response = await fetch(this.#apiprefix.concat("pets/list?limit=".concat(limit)), {
            method: "GET",
            mode: "cors",
            headers: {
                "Authorization": auth
            },
            body: JSON.stringify(formdata)
        });

        return response.json();
    }

    async deletePetById(auth, petid) {
        const response = await fetch(this.#apiprefix.concat("pets/list?id=".concat(petid)), {
            method: "DELETE",
            mode: "cors",
            headers: {
                "Authorization": auth
            },
            body: JSON.stringify(formdata)
        });

        return response.json();
    }

    async insertVacina(auth, formdata = {}) {
        const response = await fetch(this.#apiprefix.concat("vacinas/insert"), {
            method: "POST",
            mode: "cors",
            headers: {
                "Content-type": "application/json",
                "Authorization": auth
            },
            body: JSON.stringify(formdata)
        });

        return response.json();
    }

    async returnVacinasByPetId(auth, petId, limit) {
        const response = await fetch(this.#apiprefix.concat("vacinas/listByPetsId?limit=".concat(limit).concat("&petsId=").concat(petId)), {
            method: "GET",
            mode: "cors",
            headers: {
                "Authorization": auth
            },
            body: JSON.stringify(formdata)
        });

        return response.json();
    }

    async insertEvent(auth, formdata) {
        const response = await fetch(this.#apiprefix.concat("calendario/insert"), {
            method: "POST",
            mode: "cors",
            headers: {
                "Content-type": "application/json",
                "Authorization": auth
            },
            body: JSON.stringify(formdata)
        });

        return response.json();
    }
    
    async listEvents(auth, limit) {
        const response = await fetch(this.#apiprefix.concat("calendario/list?limit=".concat(limit)), {
            method: "GET",
            mode: "cors",
            headers: {
                "Authorization": auth
            },
            body: JSON.stringify(formdata)
        });

        return response.json();
    }
}