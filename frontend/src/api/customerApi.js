import {apiFetch} from "@/api/http.js";


export async function getAllCustomers(){
  return await apiFetch("/customers")
}

export async function getCustomerById(id){
  return await apiFetch(`/customers/${id}`)
}
