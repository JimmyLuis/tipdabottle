import {apiFetch} from "@/api/http.js";


export async function getAllCustomers(){
  return await apiFetch("/customers")
}
export async function getAllCustomersWithInactive(){
  return await apiFetch("/customers?listInactiveProfiles=true")
}

export async function getCustomerById(id){
  return await apiFetch(`/customers/${id}`)
}

export async function updateCustomer(customer){
  let mappedCustomer = {
    firstName: customer.firstName,
    lastName: customer.lastName,
    email: customer.email,
    community: customer.community,
    activeProfile: customer.activeProfile
  }
  let options = {
    method: "PUT",
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(mappedCustomer)
  }
  return await apiFetch(`/customers/${customer.id}`, options)
}

export async function updateCustomerBalance(id, addedBalance){
  let balance = {
    addedBalance: addedBalance
  }
  let options = {
    method: "POST",
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(balance)
  }
  return await apiFetch(`/customers/${id}/balance`, options)
}
