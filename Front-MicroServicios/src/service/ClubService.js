import axios from 'axios';


export default class ClubService {

    url = "http://localhost:8001/club/";

    getAll() {
        return axios.get(this.url + "listar");
    }

    save(club) {
        return axios.post(this.url + "guardar", club)
    }
}