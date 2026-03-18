import {apiFetch} from "@/api/http.js";

export async function getAllProducts(){
  return await apiFetch("/products");
}
