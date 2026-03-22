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

export async function revertPurchase(purchases){
  const mappedPurchases = []
  purchases.forEach((purchase, id) => {
    console.log(id)
    mappedPurchases.push({
      id: purchase.id,
      product_id: purchase.product.id,
      reverted: true
    })
  })
  const customerId = purchases.values().next().value.customerId;
  let options = {
    method: "DELETE",
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(mappedPurchases)
  }
  console.log(customerId)
  console.log(mappedPurchases)
  return await apiFetch(`/purchases/customer/${customerId}`, options)
}
