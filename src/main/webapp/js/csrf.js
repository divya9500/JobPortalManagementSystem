export async function loadCsrfToken() {
  const res = await fetch("/JobPortalManagementSystem/csrf-token");
  const data = await res.json();
  sessionStorage.setItem("CSRF_TOKEN", data.csrfToken);
}

export function csrfFetch(url, options = {}) {
  options.headers = options.headers || {};

  const token = sessionStorage.getItem("CSRF_TOKEN");
  if (token) {
    options.headers["X-CSRF-TOKEN"] = token;
  }

  return fetch(url, options);
}
