import {apiFetch} from "@/api/http.js";

export async function getAllProducts(){
  return await apiFetch("/products");
}

export async function updateProduct(product){
  const mappedProduct = {
    name: product.name,
    description: product.description,
    price: product.price,
    category: product.category,
    stock: product.stock
  }
  let options = {
    method: "PUT",
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(mappedProduct)
  }
  return await apiFetch(`/products/${product.id}`, options)
}

export async function createProduct(product){
  const mappedProduct = {
    name: product.name,
    description: product.description,
    price: product.price,
    category: product.category,
    stock: product.stock,
    container: product.container
  }
  let options = {
    method: "POST",
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(mappedProduct)
  }
  return await apiFetch(`/products`, options)
}
