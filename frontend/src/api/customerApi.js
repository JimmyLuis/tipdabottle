import {apiFetch} from "@/api/http.js";


export async function getAllCustomers(){
  return await apiFetch("/customers")
}
