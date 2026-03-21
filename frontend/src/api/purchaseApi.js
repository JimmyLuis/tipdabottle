import {apiFetch} from "@/api/http.js";

export async function purchaseProducts(customerId, products){
  const mappedProducts = products.map(({ id, ...rest }) => ({
    ...rest,
    product_id: id
  }));
  let options = {
    method: "POST",
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(mappedProducts)
  }
  return await apiFetch(`/purchases/customer/${customerId}`, options)
}

export async function getAllPurchases(page, size){
  return await apiFetch(`/purchases?page=${page}&size=${size}`)
}
