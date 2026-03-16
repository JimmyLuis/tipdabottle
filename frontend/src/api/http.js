// services/api.js
import {useErrorStore} from "@/stores/app.js";

const apiUrl = import.meta.env.VITE_API_URL
const errorStore = useErrorStore();
export async function apiFetch(url, options = {}) {

  try {
    const response = await fetch(`${apiUrl}${url}`, options)

    const data = await response.json();
    if (!response.ok) {
      errorStore.set(data.message || "Server error")
      new Error(data.message)
    }

    return data

  } catch (error) {
    errorStore.set(error)
  }
}
