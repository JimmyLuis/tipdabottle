import {apiFetch} from "@/api/http.js";

export async function login(email, password){
  let loginBody = {
    email: email,
    password: password
  }

  let options = {
    method: "POST",
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(loginBody)
  }
  return await apiFetch('/accounts/login', options)
}

export async function logout(){
  let options = {
    method: "POST",
    headers: {
      'Content-Type': 'application/json'
    }
  }
  return await apiFetch('/accounts/logout', options)
}

export async function getSession(){
  let options = {
    method: "GET",
    headers: {
      'Content-Type': 'application/json'
    }
  }
  return await apiFetch('/accounts/me', options, true)
}
