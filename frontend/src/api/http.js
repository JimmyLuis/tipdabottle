// services/api.js
import {useErrorStore} from "@/stores/app.js";

const apiUrl = window.location.protocol + "//" + window.location.hostname + ":8080"


export async function apiFetch(url, options = {}, suppressErrors= false) {
  options.credentials = 'include'
  try {
    const response = await fetch(`${apiUrl}${url}`, options)

    const data = await response.json();
    if (!response.ok) {
      if (!suppressErrors) useErrorStore().set(data || "Server error")
      new Error(data.message)
      return null
    }
    return data
  } catch (error) {
    if (!suppressErrors)  useErrorStore().set(error)
  }
}
